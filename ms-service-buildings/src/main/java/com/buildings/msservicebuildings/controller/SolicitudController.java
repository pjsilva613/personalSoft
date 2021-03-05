package com.buildings.msservicebuildings.controller;

import com.buildings.msservicebuildings.entity.Material;
import com.buildings.msservicebuildings.entity.Solicitud;
import com.buildings.msservicebuildings.service.ISolicitudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/solicitudes")
public class SolicitudController {

    @Autowired
    private ISolicitudService iSolicitudService;
    Logger log= LoggerFactory.getLogger(MaterialController.class);

    @GetMapping
    public ResponseEntity<List<Solicitud>> getAllSolicitudes(){
        List<Solicitud> mSolicitudesList = iSolicitudService.listAllSolicitudes();
        if (mSolicitudesList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mSolicitudesList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Solicitud> getSolicitud(@PathVariable("id") Long id){
        Solicitud solicitud= iSolicitudService.getSolicitud(id);
        if ( null == solicitud ) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(solicitud);
    }

    @PostMapping
    public ResponseEntity<Solicitud> createSolicitud(@Valid @RequestBody Solicitud solicitud, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
        }
        Solicitud solicitudDb = iSolicitudService.createSolicitud(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudDb);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Solicitud> updateSolicitud(@PathVariable("id") Long id, @RequestBody Solicitud solicitud){
        solicitud.setId(id);
        Solicitud solicitudDb = iSolicitudService.updateSolicitud(solicitud);
        if (solicitudDb == null){
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(solicitudDb);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Solicitud> deleteSolicitud(@PathVariable("id") Long id){
        Solicitud solicitudDb = iSolicitudService.deleteSolicitud(id);
        if (solicitudDb == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(solicitudDb);
    }

    private String formatMessage(BindingResult bindingResult){

        List<Map<String, String>> errors= bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> {Map<String, String> error = new HashMap<>();
                    log.info("Imprime error:    "+fieldError.getField()+" "+fieldError.getDefaultMessage());
                    error.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return error;})
                .collect(Collectors.toList());

        ErrorMessage errorMessage= ErrorMessage.builder().code("01").Message(errors).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString="";

        try {
            jsonString= objectMapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info(jsonString);
        return jsonString;
    }

    @GetMapping(value = "/exportar/reporteSolicitudes.xls")
    public ResponseEntity<InputStreamResource> generarReporte() throws IOException {
        ByteArrayInputStream byteArrayInputStream= iSolicitudService.exportAll();
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename:solicitudes.xls");
        return ResponseEntity.ok().headers(httpHeaders).body(new InputStreamResource(byteArrayInputStream));
    }
}
