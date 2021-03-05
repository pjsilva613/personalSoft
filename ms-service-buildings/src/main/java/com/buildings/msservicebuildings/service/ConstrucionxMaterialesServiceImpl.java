package com.buildings.msservicebuildings.service;

import com.buildings.msservicebuildings.entity.Construccion;
import com.buildings.msservicebuildings.entity.ConstruccionMaterial;
import com.buildings.msservicebuildings.respository.ConstruccionMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConstrucionxMaterialesServiceImpl implements IConstruccionesxMaterialesService {

    @Autowired
    private ConstruccionMaterialRepository construccionMaterialRepository;

    @Override
    public List<ConstruccionMaterial> createConstruccionMaterial(List<ConstruccionMaterial> construccionMaterialList) {
        if (!construccionMaterialList.isEmpty()) {
            construccionMaterialList.stream()
                    .map(x->this.createConstruccionMaterial(x))
                    .collect(Collectors.toList());
        }
        construccionMaterialList.forEach(System.out::println);
        return construccionMaterialList;
    }

    @Override
    public List<ConstruccionMaterial> updateConstruccionMaterial(List<ConstruccionMaterial> construccionMaterialList) {
        construccionMaterialList.forEach(System.out::println);
        if (!construccionMaterialList.isEmpty()) {
            construccionMaterialList.stream()
                    .map(x->this.updateConstruccionMaterial(x))
                    .collect(Collectors.toList());
        }
        construccionMaterialList.forEach(System.out::println);
        return construccionMaterialList;
    }

    @Override
    public List<ConstruccionMaterial> deleteConstruccionMaterial(List<ConstruccionMaterial> construccionMaterialList) {
        construccionMaterialList.forEach(System.out::println);
        if (!construccionMaterialList.isEmpty()) {
            construccionMaterialList.stream()
                    .map(x->this.deleteConstruccionMaterial(x.getId()))
                    .collect(Collectors.toList());
        }
        construccionMaterialList.forEach(System.out::println);
        return construccionMaterialList;
    }

    @Override
    public List<ConstruccionMaterial> findByIdConstruccion(Long id) {
        return construccionMaterialRepository.findByIdConstruccion(id);
    }

    @Override
    public List<ConstruccionMaterial> listAllConstruccionMaterial() {
        return construccionMaterialRepository.findAll();
    }

    @Override
    public ConstruccionMaterial getConstruccionMaterial(Long id) {
        return construccionMaterialRepository.findById(id).orElse(null);
    }

    @Override
    public ConstruccionMaterial createConstruccionMaterial(ConstruccionMaterial construccionMaterial) {
        construccionMaterial.setEstado("CREADO");
        return construccionMaterialRepository.save(construccionMaterial);
    }

    @Override
    public ConstruccionMaterial updateConstruccionMaterial(ConstruccionMaterial construccionMaterial) {
        ConstruccionMaterial construccionMaterialDb = this.getConstruccionMaterial(construccionMaterial.getId());
        if (construccionMaterialDb == null) {
            return null;
        }
        construccionMaterialDb.setIdConstruccion(construccionMaterial.getIdConstruccion());
        construccionMaterialDb.setIdMaterial(construccionMaterial.getIdMaterial());
        construccionMaterialDb.setCantidadMaterialRequerido(construccionMaterial.getCantidadMaterialRequerido());
        return construccionMaterialRepository.save(construccionMaterialDb);
    }

    @Override
    public ConstruccionMaterial deleteConstruccionMaterial(Long id) {
        ConstruccionMaterial ConstruccionMaterialDb = getConstruccionMaterial(id);
        if (ConstruccionMaterialDb == null) {
            return null;
        }
        ConstruccionMaterialDb.setEstado("ELIMINADO");
        return construccionMaterialRepository.save(ConstruccionMaterialDb);
    }
}
