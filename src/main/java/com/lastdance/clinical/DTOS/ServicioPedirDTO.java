package com.lastdance.clinical.DTOS;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

public class ServicioPedirDTO {
    private Long idServicio;
    private Long idProfesional;
    private LocalDateTime fecha;

    public ServicioPedirDTO(Long idServicio, Long idProfesional, LocalDateTime fecha) {
        this.idServicio = idServicio;
        this.idProfesional = idProfesional;
        this.fecha = fecha;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Long getIdProfesional() {
        return idProfesional;
    }
}

