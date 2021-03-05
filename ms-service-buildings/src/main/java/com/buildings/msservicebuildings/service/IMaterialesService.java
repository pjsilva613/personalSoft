package com.buildings.msservicebuildings.service;

import java.util.List;

import com.buildings.msservicebuildings.entity.Material;


public interface IMaterialesService {
	
	public List<Material> listAllMaterial();
    public Material getMaterial(Long id);
    public Material createMaterial(Material material);
    public Material updateMaterial(Material material);
    public Material deleteMaterial(Long id);
    public Material updateCantidadMaterial(Long id, Double cantidad);
	
}
