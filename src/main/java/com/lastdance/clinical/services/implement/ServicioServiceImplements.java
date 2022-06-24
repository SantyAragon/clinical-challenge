package com.lastdance.clinical.services.implement;

import com.lastdance.clinical.DTOS.ServicioDTO;
import com.lastdance.clinical.models.Servicio;
import com.lastdance.clinical.models.TipoServicio;
import com.lastdance.clinical.repositories.ServicioRepository;
import com.lastdance.clinical.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServiceImplements implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public List<Servicio> traerServicios() {
        return servicioRepository.findAll();
    }

    @Override
    public ServicioDTO traerServicioDTO(Long id) {
        return servicioRepository.findById(id).map(ServicioDTO::new).orElse(null);
    }

    @Override
    public Servicio traerServicio(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public void guardarServicio(Servicio servicio) {
        servicioRepository.save(servicio);
    }
}
