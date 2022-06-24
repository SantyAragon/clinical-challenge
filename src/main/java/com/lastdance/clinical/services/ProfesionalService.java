package com.lastdance.clinical.services;

import com.lastdance.clinical.DTOS.ProfesionalDTO;
import com.lastdance.clinical.models.Profesional;

import java.util.List;

public interface ProfesionalService {

    List<Profesional> traerProfesionales();

    ProfesionalDTO traerProfesionalDTO(Long id);

    Profesional traerProfesional(Long id);

    void guardarProfesinal(Profesional profesional);

    void borrarProfesional(Profesional profesional);
}
