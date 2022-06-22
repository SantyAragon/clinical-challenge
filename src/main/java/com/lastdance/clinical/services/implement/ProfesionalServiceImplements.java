package com.lastdance.clinical.services.implement;

import com.lastdance.clinical.DTOS.ProfesionalDTO;
import com.lastdance.clinical.models.Profesional;
import com.lastdance.clinical.repositories.ProfesionalRepository;
import com.lastdance.clinical.services.ProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesionalServiceImplements implements ProfesionalService {

    @Autowired
    private ProfesionalRepository profesionalRepository;
    @Override
    public List<Profesional> traerProfesionales() {
        return profesionalRepository.findAll();
    }

    @Override
    public ProfesionalDTO traerProfesionalDTO(Long id) {
        return profesionalRepository.findById(id).map(ProfesionalDTO::new).orElse(null);
    }

    @Override
    public Profesional traerProfesional(Long id) {
        return profesionalRepository.findById(id).orElse(null);
    }

    @Override
    public void guardarProfesinal(Profesional profesional) {
        profesionalRepository.save(profesional);

    }
}
