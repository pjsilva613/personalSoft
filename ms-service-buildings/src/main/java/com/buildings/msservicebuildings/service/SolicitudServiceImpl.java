package com.buildings.msservicebuildings.service;

import com.buildings.msservicebuildings.entity.Construccion;
import com.buildings.msservicebuildings.entity.ConstruccionMaterial;
import com.buildings.msservicebuildings.entity.Material;
import com.buildings.msservicebuildings.entity.Solicitud;
import com.buildings.msservicebuildings.respository.SolicitudRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.util.ByteArrayOutputStreamEx;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SolicitudServiceImpl implements ISolicitudService{

    private static final String MESSAGE_EXISTE_COORDENADAS="Ya existe una solicitud con las mismas coordenadas";
    private static final String MESSAGE_INVENTARIO="No se puede registrar Debido a la falta de material ";
    private static final String URI_CONSTRUCCION ="http://localhost:8080/construcciones/";
    private static final String URI_MATERIAL ="http://localhost:8080/materiales/";
    private static final String URI_MATERIALXCONSTRUCCION ="http://localhost:8080/construccionesxmateriales/";
    private static final String MESSAGE_ERROR_STRING= "Error al convertir el objeto en un json";
    @Autowired
    private SolicitudRepository solicitudRepository;

    @Override
    public List<Solicitud> listAllSolicitudes() {
        return solicitudRepository.findAll();
    }

    @Override
    public Solicitud getSolicitud(Long id) {
        return solicitudRepository.findById(id).orElse(null);
    }

    @Override
    public Solicitud createSolicitud(Solicitud solicitud) {
        Long dias = diasDeConstruccion(solicitud.getIdConstruccion());
        LocalDate fechaActual= LocalDate.now();
        Solicitud solicitudDB = solicitudRepository.findByCoordenadaXAndCoordenadaY(solicitud.getCoordenadaX(), solicitud.getCoordenadaY());
        if (null != solicitudDB) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_EXISTE_COORDENADAS);
        }
        solicitudDB = solicitudRepository.findById(solicitudRepository.count()).orElse(null) ;
        validarMaterial(solicitud.getIdConstruccion());
        if (solicitudDB == null || solicitudDB.getFechaEntregaCiudadela().isBefore(fechaActual)) {
            solicitud.setFechaInicioConstruccion(LocalDate.now().plusDays(1));
            solicitud.setFechaFinalConstruccion(solicitud.getFechaInicioConstruccion().plusDays(dias));
            solicitud.setFechaEntregaCiudadela(solicitud.getFechaFinalConstruccion().plusDays(1));
        }else{
            solicitud.setFechaInicioConstruccion(solicitudDB.getFechaEntregaCiudadela().plusDays(1));
            solicitud.setFechaFinalConstruccion(solicitud.getFechaInicioConstruccion().plusDays(dias));
            solicitud.setFechaEntregaCiudadela(solicitud.getFechaFinalConstruccion().plusDays(1));
        }
        solicitud.setEstado("PENDIENTE");
        return solicitudRepository.save(solicitud);
    }

    private boolean validarMaterial(Long idConstruccion) {
        List<ConstruccionMaterial> construccionMaterialList = obtenerMaterialRequerido(idConstruccion);
        List<Material> materialList = obtenerMaterial();

        construccionMaterialList.stream().forEach(x-> materialList.stream().forEach(y-> {
            if (y.getId() == x.getIdMaterial()) {
                if ((y.getCantidad()-x.getCantidadMaterialRequerido())<=0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_INVENTARIO+y.getNombre());
                }else{
                    y.setCantidad(y.getCantidad()-x.getCantidadMaterialRequerido());
                    actualizarCantidadMaterial(y);
                }
            }
        }));
        return true;
    }

    private void actualizarCantidadMaterial(Material material) {
        try {
            String jsonString="";
            ObjectMapper objectMapper = new ObjectMapper();
            jsonString= objectMapper.writeValueAsString(material);
            RestTemplate restTemplate= new RestTemplate();
            System.out.println("Stringjson:::::::::::::::: "+jsonString);
            System.out.println("URI:::::::del servicio"+URI_MATERIAL+material.getId()+"/"+jsonString);
            restTemplate.put(URI_MATERIAL+material.getId(), material);
        }catch (JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_ERROR_STRING+e);
        }
       }

    private List<Material> obtenerMaterial() {
        List<Material> materialList = new ArrayList<>();
        RestTemplate restTemplate= new RestTemplate();
        ResponseEntity<Material[]> responseEntity = restTemplate.getForEntity(URI_MATERIAL , Material[].class);
        Material[] objects = responseEntity.getBody();
        for (int i = 0; i < objects.length; i++) {
            Material material = objects[i];
            materialList.add(material);
        }
        return  materialList;
    }

    private List<ConstruccionMaterial> obtenerMaterialRequerido(Long idConstruccion){
        List<ConstruccionMaterial> construccionMaterialList = new ArrayList<>();
        RestTemplate restTemplate= new RestTemplate();
        ResponseEntity<ConstruccionMaterial[]> responseEntity = restTemplate.getForEntity(URI_MATERIALXCONSTRUCCION+idConstruccion , ConstruccionMaterial[].class);
        ConstruccionMaterial[] objects = responseEntity.getBody();
        for (int i = 0; i < objects.length; i++) {
            ConstruccionMaterial construccionMaterial = objects[i];
            System.out.println("construccionMaterial.toString()::::"+"i:::"+i+":::::"+construccionMaterial.toString());
            construccionMaterialList.add(construccionMaterial);
        }
        return construccionMaterialList;
    }

    private Long diasDeConstruccion(Long idConstruccion) {
        RestTemplate restTemplate= new RestTemplate();
        Construccion construccion = restTemplate.getForObject(URI_CONSTRUCCION+idConstruccion, Construccion.class);
        return Long.parseLong(String.valueOf(construccion.getCantidadDias().intValue()));
    }

    @Override
    public Solicitud updateSolicitud(Solicitud solicitud) {
        Solicitud solicitudDb = getSolicitud(solicitud.getId());
        if (solicitudDb == null) {
            return null;
        }
        solicitudDb.setIdConstruccion(solicitud.getIdConstruccion());
        solicitudDb.setCoordenadaX(solicitud.getCoordenadaX());
        solicitudDb.setCoordenadaY(solicitud.getCoordenadaY());
        return solicitudRepository.save(solicitudDb);
    }

    @Override
    public Solicitud deleteSolicitud(Long id) {
        Solicitud solicitudDb = getSolicitud(id);
        if (solicitudDb == null) {
            return null;
        }
        solicitudDb.setEstado("ELIMINADO");
        return solicitudRepository.save(solicitudDb);
    }

    @Override
    public ByteArrayInputStream exportAll() throws IOException {
        String[] columnas={"idConstruccion", "coordenada X", "coordenada Y", "Estado", "Fecha Inicio Construccion", "Fecha Final Construccion"};
        Workbook workbook= new HSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        Sheet sheet= workbook.createSheet("Solicitudes");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columnas.length; i++) {
            Cell cell= row.createCell(i);
            cell.setCellValue(columnas[i]);
        }

        List<Solicitud> solicitudList= this.listAllSolicitudes();
        int initRow =1;
        for (Solicitud solicitud: solicitudList) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(solicitud.getIdConstruccion());
            row.createCell(1).setCellValue(solicitud.getCoordenadaX());
            row.createCell(2).setCellValue(solicitud.getCoordenadaY());
            row.createCell(3).setCellValue(solicitud.getEstado());
            row.createCell(4).setCellValue(solicitud.getFechaInicioConstruccion());
            row.createCell(5).setCellValue(solicitud.getFechaFinalConstruccion());
            initRow++;
        }
        workbook.write(byteArrayOutputStream);
        workbook.close();
        return  new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

}
