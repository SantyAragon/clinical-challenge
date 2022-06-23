package com.lastdance.clinical.controllers;

import com.lastdance.clinical.DTOS.ProfesionalDTO;
import com.lastdance.clinical.models.Profesional;
import com.lastdance.clinical.services.ProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProfesionalController {
    @Autowired
    ProfesionalService profesionalService;

    @GetMapping("/profesionales")
    public Set<ProfesionalDTO> traerProfesionales() {
        return profesionalService.traerProfesionales().stream().map(profesional -> new ProfesionalDTO(profesional)).collect(Collectors.toSet());
    }

    @GetMapping("/profesionales/{id}")
    public ProfesionalDTO traerProfesional(@PathVariable Long id) {
        return profesionalService.traerProfesionalDTO(id);
    }
}
