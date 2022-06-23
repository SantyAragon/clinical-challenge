package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.PacienteProducto;

import java.time.LocalDateTime;

public class PacienteProductoDTO {

    private Long id;
    private String nombre;
    private int cantidad;
    private Double monto;
    private LocalDateTime fecha;

    public PacienteProductoDTO() {
    }

    public PacienteProductoDTO(PacienteProducto pacienteProducto) {
        this.id = pacienteProducto.getId();
        this.cantidad = pacienteProducto.getCantidad();
        this.monto = pacienteProducto.getMonto();
        this.fecha = pacienteProducto.getFecha();
        this.nombre = pacienteProducto.getProducto().getNombre();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

}
