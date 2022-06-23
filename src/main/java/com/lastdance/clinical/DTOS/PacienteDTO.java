package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.Factura;
import com.lastdance.clinical.models.Paciente;
import com.lastdance.clinical.models.PacienteProducto;
import com.lastdance.clinical.models.PacienteServicio;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PacienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Long identificacion;
    private Set<PacienteServicioDTO> servicios = new HashSet<>();
    private Set<PacienteProductoDTO> productos = new HashSet<>();

    public PacienteDTO() {
    }

    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nombre = paciente.getNombre();
        this.apellido = paciente.getApellido();
        this.email = paciente.getEmail();
        this.identificacion = paciente.getIdentificacion();

        Set<PacienteServicio> serviciosTomados = new HashSet<>();
        paciente.getFacturas().forEach(factura -> serviciosTomados.addAll(factura.getServicios()));
        this.servicios = serviciosTomados.stream().map(PacienteServicioDTO::new).collect(Collectors.toSet());


        Set<PacienteProducto> productosTomados = new HashSet<>();
        paciente.getFacturas().forEach(factura -> productosTomados.addAll(factura.getProductos()));

        this.productos = productosTomados.stream().map(PacienteProductoDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public Long getIdentificacion() {
        return identificacion;
    }

    public Set<PacienteServicioDTO> getServicios() {
        return servicios;
    }

    public Set<PacienteProductoDTO> getProductos() {
        return productos;
    }
}