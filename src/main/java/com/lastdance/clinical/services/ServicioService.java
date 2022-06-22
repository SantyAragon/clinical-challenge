package com.lastdance.clinical.services;

import com.lastdance.clinical.DTOS.ServicioDTO;
import com.lastdance.clinical.models.Servicio;

import java.util.List;

public interface ServicioService {

    List<Servicio> traerServicios();
    ServicioDTO traerServicioDTO(Long id);
    Servicio traerServicio(Long id);
    void guardarServicio(Servicio servicio);
}
