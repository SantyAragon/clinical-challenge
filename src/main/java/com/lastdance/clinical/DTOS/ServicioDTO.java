package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.Profesional;
import com.lastdance.clinical.models.Servicio;
import com.lastdance.clinical.models.TipoServicio;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ServicioDTO {
    private Long id;
    private TipoServicio tipoServicio;
//    private Set<String> profesionales = new HashSet<>();
    private Set<ProfesionalDTO> profesionales = new HashSet<>();

    private boolean activo;

    public ServicioDTO() {
    }

    public ServicioDTO(Servicio servicio) {
        this.id = servicio.getId();
        this.tipoServicio = servicio.getTipoServicio();
        this.profesionales = servicio.getProfesionals().stream().filter(profesional -> profesional.isActivo()).map(ProfesionalDTO::new).collect(Collectors.toSet());
        this.activo = servicio.isActivo();
//        this.profesionales = servicio.getProfesionals().stream().map(Profesional::getFullName).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public Set<ProfesionalDTO> getProfesionales() {
        return profesionales;
    }

    public boolean isActivo() {
        return activo;
    }

    //    public Set<String> getProfesionales() {
//        return profesionales;
//    }
}
