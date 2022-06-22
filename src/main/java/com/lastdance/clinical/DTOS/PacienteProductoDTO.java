package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.PacienteProducto;

import java.time.LocalDateTime;

public class PacienteProductoDTO {

    private Long id;
    private String nombre;
    private Double monto;
    private LocalDateTime fecha;

    public PacienteProductoDTO() {  }

    public PacienteProductoDTO(PacienteProducto pacienteProducto) {
        this.id = pacienteProducto.getId();
        this.nombre = pacienteProducto.getNombre();
        this.monto = pacienteProducto.getMonto();
        this.fecha = pacienteProducto.getFecha();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

}
