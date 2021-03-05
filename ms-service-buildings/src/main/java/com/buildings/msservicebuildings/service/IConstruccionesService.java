package com.buildings.msservicebuildings.service;

import com.buildings.msservicebuildings.entity.Construccion;
import com.buildings.msservicebuildings.entity.Material;

import java.util.List;

public interface IConstruccionesService {
    public List<Construccion> listAllConstruccion();
    public Construccion getConstruccion(Long id);
    public Construccion createConstruccion(Construccion construccion);
    public Construccion updateConstruccion(Construccion construccion);
    public Construccion deleteConstruccion(Long id);
    public Construccion updateCantidadDiasConstruccion(Long id, Double cantidadDias);
}
