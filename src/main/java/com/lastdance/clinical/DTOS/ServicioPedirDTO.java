package com.lastdance.clinical.DTOS;

import java.time.LocalDateTime;

public class ServicioPedirDTO {
    private Long idServicio;
    private LocalDateTime fecha;

    public ServicioPedirDTO(Long idServicio, LocalDateTime fecha) {
        this.idServicio = idServicio;
        this.fecha = fecha;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}

