package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.Servicio;
import com.lastdance.clinical.models.TipoServicio;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ServicioDTO {
    private Long id;
    private boolean activo;
    private TipoServicio tipoServicio;
    private Double monto;
    private Set<ProfesionalDTO> profesionales = new HashSet<>();


    public ServicioDTO() {
    }

    public ServicioDTO(Servicio servicio) {
        this.id = servicio.getId();
        this.activo = servicio.isActivo();
        this.tipoServicio = servicio.getTipoServicio();
        this.monto = servicio.getMonto();
        this.profesionales = servicio.getProfesionales().stream().filter(profesional -> profesional.isActivo()).map(ProfesionalDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public boolean isActivo() {
        return activo;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public Double getMonto() {
        return monto;
    }

    public Set<ProfesionalDTO> getProfesionales() {
        return profesionales;
    }


}
