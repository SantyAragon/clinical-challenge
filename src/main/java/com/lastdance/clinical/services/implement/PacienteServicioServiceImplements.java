package com.lastdance.clinical.services.implement;

import com.lastdance.clinical.models.PacienteServicio;
import com.lastdance.clinical.repositories.PacienteServicioRepository;
import com.lastdance.clinical.services.PacienteServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteServicioServiceImplements implements PacienteServicioService {
    @Autowired
    PacienteServicioRepository pacienteServicioRepository;


    @Override
    public void guardarPacienteServicio(PacienteServicio pacienteServicio) {
        pacienteServicioRepository.save(pacienteServicio);
    }
}
