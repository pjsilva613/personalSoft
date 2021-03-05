package com.buildings.msservicebuildings.entity;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "solicitud")
@Data
@Builder
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idConstruccion;
    private Double coordenadaX;
    private Double coordenadaY;
    private String estado;
    private LocalDate fechaEntregaCiudadela;
    private LocalDate fechaInicioConstruccion;
    private LocalDate fechaFinalConstruccion;

    public Solicitud() {
    }

    public Solicitud(Long id, Long idConstruccion, Double coordenadaX, Double coordenadaY, String estado, LocalDate fechaEntregaCiudadela, LocalDate fechaInicioConstruccion, LocalDate fechaFinalConstruccion) {
        this.id = id;
        this.idConstruccion = idConstruccion;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.estado = estado;
        this.fechaEntregaCiudadela = fechaEntregaCiudadela;
        this.fechaInicioConstruccion = fechaInicioConstruccion;
        this.fechaFinalConstruccion = fechaFinalConstruccion;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Double getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(Double coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public Double getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(Double coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaEntregaCiudadela(LocalDate fechaEntregaCiudadela) {
        this.fechaEntregaCiudadela = fechaEntregaCiudadela;
    }

    public LocalDate getFechaInicioConstruccion() {
        return fechaInicioConstruccion;
    }

    public void setFechaInicioConstruccion(LocalDate fechaInicioConstruccion) {
        this.fechaInicioConstruccion = fechaInicioConstruccion;
    }

    public LocalDate getFechaFinalConstruccion() {
        return fechaFinalConstruccion;
    }

    public void setFechaFinalConstruccion(LocalDate fechaFinalConstruccion) {
        this.fechaFinalConstruccion = fechaFinalConstruccion;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", idConstruccion=" + idConstruccion +
                ", coordenadaX=" + coordenadaX +
                ", coordenadaY=" + coordenadaY +
                ", estado='" + estado + '\'' +
                ", fechaEntregaCiudadela=" + fechaEntregaCiudadela +
                ", fechaInicioConstruccion=" + fechaInicioConstruccion +
                ", fechaFinalConstruccion=" + fechaFinalConstruccion +
                '}';
    }
}
