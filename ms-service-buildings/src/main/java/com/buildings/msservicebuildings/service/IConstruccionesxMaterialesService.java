package com.buildings.msservicebuildings.service;


import com.buildings.msservicebuildings.entity.ConstruccionMaterial;
import java.util.List;

public interface IConstruccionesxMaterialesService {

    public List<ConstruccionMaterial> createConstruccionMaterial(List<ConstruccionMaterial> construccionMaterialList);
    public List<ConstruccionMaterial> updateConstruccionMaterial(List<ConstruccionMaterial> construccionMaterialList);
    public List<ConstruccionMaterial> deleteConstruccionMaterial(List<ConstruccionMaterial> construccionMaterialList);
    public List<ConstruccionMaterial> findByIdConstruccion(Long id);
    public List<ConstruccionMaterial> listAllConstruccionMaterial();
    public ConstruccionMaterial getConstruccionMaterial(Long id);
    public ConstruccionMaterial createConstruccionMaterial(ConstruccionMaterial construccionMaterial);
    public ConstruccionMaterial updateConstruccionMaterial(ConstruccionMaterial construccionMaterial);
    public ConstruccionMaterial deleteConstruccionMaterial(Long id);
    //public ConstruccionMaterial updateCantidadDiasConstruccion(Long id, Double cantidadDias);
}
