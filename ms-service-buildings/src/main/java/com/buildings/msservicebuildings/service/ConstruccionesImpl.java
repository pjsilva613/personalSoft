package com.buildings.msservicebuildings.service;

import com.buildings.msservicebuildings.entity.Construccion;
import com.buildings.msservicebuildings.entity.Material;
import com.buildings.msservicebuildings.respository.ConstruccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstruccionesImpl implements IConstruccionesService{

    @Autowired
    private ConstruccionRepository construccionRepository;

    @Override
    public List<Construccion> listAllConstruccion() {
        return construccionRepository.findAll();
    }

    @Override
    public Construccion getConstruccion(Long id) {
        return construccionRepository.findById(id).orElse(null);
    }

    @Override
    public Construccion createConstruccion(Construccion construccion) {
        construccion.setEstado("CREADO");
        return construccionRepository.save(construccion);
    }

    @Override
    public Construccion updateConstruccion(Construccion construccion) {
        Construccion ConstruccionDb = getConstruccion(construccion.getId());
        if (ConstruccionDb == null) {
            return null;
        }
        ConstruccionDb.setNombre(construccion.getNombre());
        ConstruccionDb.setDescripcion(construccion.getDescripcion());
        ConstruccionDb.setCantidadDias(construccion.getCantidadDias());
        return construccionRepository.save(ConstruccionDb);
    }

    @Override
    public Construccion deleteConstruccion(Long id) {
        Construccion construccionDb = getConstruccion(id);
        if (construccionDb == null) {
            return null;
        }
        construccionDb.setEstado("ELIMINADO");
        return construccionRepository.save(construccionDb);
    }

    @Override
    public Construccion updateCantidadDiasConstruccion(Long id, Double cantidadDias) {
        Construccion construccionDb = getConstruccion(id);
        if (construccionDb == null) {
            return null;
        }
        Double newStock= construccionDb.getCantidadDias()+cantidadDias;
        construccionDb.setCantidadDias(newStock);
        return construccionRepository.save(construccionDb);
    }
}
