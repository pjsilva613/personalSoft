package com.buildings.msservicebuildings.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "construccion_X_material")
@Data
@Builder
public class ConstruccionMaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idConstruccion;
    private Long idMaterial;
    private Double cantidadMaterialRequerido;
    private String estado;

    public ConstruccionMaterial() {
    }

    public ConstruccionMaterial(Long id, Long idConstruccion, Long idMaterial, Double cantidadMaterialRequerido, String estado) {
        this.id = id;
        this.idConstruccion = idConstruccion;
        this.idMaterial = idMaterial;
        this.cantidadMaterialRequerido = cantidadMaterialRequerido;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdConstruccion() {
        return idConstruccion;
    }

    public void setIdConstruccion(Long idConstruccion) {
        this.idConstruccion = idConstruccion;
    }

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Double getCantidadMaterialRequerido() {
        return cantidadMaterialRequerido;
    }

    public void setCantidadMaterialRequerido(Double cantidadMaterialRequerido) {
        this.cantidadMaterialRequerido = cantidadMaterialRequerido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ConstruccionMaterial{" +
                "id=" + id +
                ", idConstruccion=" + idConstruccion +
                ", idMaterial=" + idMaterial +
                ", cantidadMaterialRequerido=" + cantidadMaterialRequerido +
                ", estado='" + estado + '\'' +
                '}';
    }
}
