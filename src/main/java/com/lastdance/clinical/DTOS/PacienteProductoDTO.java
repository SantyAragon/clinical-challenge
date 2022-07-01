package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.PacienteProducto;

import java.time.LocalDateTime;
import java.util.Set;

public class PacienteProductoDTO {

    private Long id;
    private Long idProducto;
    private String nombre;
    private int cantidad;
    private Double monto;
    private LocalDateTime fecha;
    private String imagen;

    public PacienteProductoDTO() {
    }

    public PacienteProductoDTO(PacienteProducto pacienteProducto) {
        this.id = pacienteProducto.getId();
        this.idProducto = pacienteProducto.getProducto().getId();
        this.cantidad = pacienteProducto.getCantidad();
        this.monto = pacienteProducto.getMonto();
        this.fecha = pacienteProducto.getFecha();
        this.nombre = pacienteProducto.getProducto().getNombre();
        this.imagen = pacienteProducto.getProducto().getImagen();
    }


    public Long getId() {
        return id;
    }

    public Long getIdProducto() {
        return idProducto;
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

    public String getImagen() {
        return imagen;
    }
}
