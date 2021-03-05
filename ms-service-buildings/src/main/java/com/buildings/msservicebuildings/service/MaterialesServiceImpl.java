package com.buildings.msservicebuildings.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buildings.msservicebuildings.entity.Material;
import com.buildings.msservicebuildings.respository.MaterialesRepository;

@Service
public class MaterialesServiceImpl implements IMaterialesService{

	@Autowired
	private MaterialesRepository materialesRepository;

	@Override
	public List<Material> listAllMaterial() {
		return materialesRepository.findAll();
	}

	@Override
	public Material getMaterial(Long id) {
		return materialesRepository.findById(id).orElse(null);
	}

	@Override
	public Material createMaterial(Material material) {
		material.setEstado("CREATE");
		return materialesRepository.save(material);
	}

	@Override
	public Material updateMaterial(Material material) {
		Material MaterialDb = getMaterial(material.getId());
		if (MaterialDb == null) {
			return null;
		}
		MaterialDb.setNombre(material.getNombre());
		MaterialDb.setDescripcion(material.getDescripcion());
		MaterialDb.setCantidad(material.getCantidad());
		return materialesRepository.save(MaterialDb);
	}

	@Override
	public Material deleteMaterial(Long id) {
		Material materialDb = getMaterial(id);
		if (materialDb == null) {
			return null;
		}
		materialDb.setEstado("ELIMINADO");
		return materialesRepository.save(materialDb);
	}

	@Override
	public Material updateCantidadMaterial(Long id, Double cantidad) {
		Material materialDb = getMaterial(id);
		if (materialDb == null) {
			return null;
		}
		Double newStock= materialDb.getCantidad()+cantidad;
		materialDb.setCantidad(newStock);
		return materialesRepository.save(materialDb);
	}

}
