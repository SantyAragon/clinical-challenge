package com.lastdance.clinical.DTOS;
import com.lastdance.clinical.models.PacienteServicio;
import java.time.LocalDateTime;

public class PacienteServicioDTO {
    private Long id;
    private Double monto;
    private LocalDateTime fecha;


    public PacienteServicioDTO() {
    }

    public PacienteServicioDTO(PacienteServicio pacienteServicio) {
        this.id = pacienteServicio.getId();
        this.monto = pacienteServicio.getMonto();
        this.fecha = pacienteServicio.getFecha();

    }

    public Long getId() {
        return id;
    }

    public Double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

}
