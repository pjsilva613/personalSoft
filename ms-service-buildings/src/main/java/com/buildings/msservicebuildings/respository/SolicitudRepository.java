package com.buildings.msservicebuildings.respository;

import com.buildings.msservicebuildings.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.ByteArrayInputStream;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    public Solicitud findByCoordenadaXAndCoordenadaY(Double coordenadaX, Double coordenaday);
}
