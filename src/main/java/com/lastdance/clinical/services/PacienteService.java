package com.lastdance.clinical.services;

import com.lastdance.clinical.DTOS.PacienteDTO;
import com.lastdance.clinical.models.Paciente;

import java.util.List;

public interface PacienteService {
    List<Paciente> traerPacientes();

    PacienteDTO traerPacienteDTO(Long id);

    Paciente traerPaciente(Long id);

    Paciente traerPacientePorEmail(String email);

    PacienteDTO traerPacienteDTOPorEmail(String email);

    Paciente traerPacientePorIdentificacion(Long identificacion);

    Paciente traerPacientePorToken(String token);

    void guardarPaciente(Paciente paciente);
}
