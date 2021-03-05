package com.buildings.msservicebuildings.service;

import com.buildings.msservicebuildings.entity.Construccion;
import com.buildings.msservicebuildings.entity.Solicitud;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ISolicitudService {
    public List<Solicitud> listAllSolicitudes();
    public Solicitud getSolicitud(Long id);
    public Solicitud createSolicitud(Solicitud solicitud);
    public Solicitud updateSolicitud(Solicitud solicitud);
    public Solicitud deleteSolicitud(Long id);
    public ByteArrayInputStream exportAll() throws IOException;
}
