package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.Factura;
import com.lastdance.clinical.models.PacienteProducto;
import com.lastdance.clinical.models.PacienteServicio;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FacturaDTO {

    private Long id;
    private double monto;
    private LocalDateTime fecha;
    private Set<PacienteServicioDTO> servicios = new HashSet<>();
    private Set<PacienteProductoDTO> productos = new HashSet<>();

    public FacturaDTO() {
    }

    public FacturaDTO(Factura factura) {
        this.id = factura.getId();
        this.monto = factura.getMonto();
        this.fecha = factura.getFecha();
        this.servicios = factura.getServicios().stream().map(PacienteServicioDTO::new).collect(Collectors.toSet());
        this.productos = factura.getProductos().stream().map(PacienteProductoDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Set<PacienteServicioDTO> getServicios() {
        return servicios;
    }

    public Set<PacienteProductoDTO> getProductos() {
        return productos;
    }
}
