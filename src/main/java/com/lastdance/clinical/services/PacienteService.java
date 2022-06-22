package com.lastdance.clinical.services;

import com.lastdance.clinical.DTOS.PacienteDTO;
import com.lastdance.clinical.models.Paciente;

import java.util.List;

public interface PacienteService {
    List<Paciente> traerPacientes();

    PacienteDTO traerPacienteDTO(Long id);

    Paciente traerPaciente(Long id);

    void guardarPaciente(Paciente paciente);
}
