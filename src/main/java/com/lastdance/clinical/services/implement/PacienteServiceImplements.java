package com.lastdance.clinical.services.implement;

import com.lastdance.clinical.DTOS.PacienteDTO;
import com.lastdance.clinical.models.Paciente;
import com.lastdance.clinical.repositories.PacienteRepository;
import com.lastdance.clinical.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImplements implements PacienteService {

    @Autowired
    PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> traerPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public PacienteDTO traerPacienteDTO(Long id) {
        return pacienteRepository.findById(id).map(paciente -> new PacienteDTO(paciente)).orElse(null);
    }

    @Override
    public Paciente traerPaciente(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @Override
    public void guardarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }
}
