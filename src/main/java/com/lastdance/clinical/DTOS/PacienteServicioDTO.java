package com.lastdance.clinical.DTOS;
import com.lastdance.clinical.models.PacienteServicio;
import com.lastdance.clinical.models.Profesional;

import java.time.LocalDateTime;

public class PacienteServicioDTO {
    private Long id;
    private Long idServicio;
    private Double monto;
    private LocalDateTime fecha;
    private String profesional;


    public PacienteServicioDTO() {
    }

    public PacienteServicioDTO(PacienteServicio pacienteServicio) {
        this.id = pacienteServicio.getId();
        this.idServicio= pacienteServicio.getServicio().getId();
        this.monto = pacienteServicio.getMonto();
        this.fecha = pacienteServicio.getFecha();
        this.profesional = pacienteServicio.getServicio().getProfesionales().stream().map(Profesional::getFullName).findFirst().orElse(null);

    }

    public Long getId() {
        return id;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public Double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getProfesional() {
        return profesional;
    }
}
