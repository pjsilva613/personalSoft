package com.buildings.msservicebuildings.respository;


import com.buildings.msservicebuildings.entity.ConstruccionMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ConstruccionMaterialRepository extends JpaRepository<ConstruccionMaterial, Long> {

    public List<ConstruccionMaterial> findByIdConstruccion(Long idConstruccion);
}
