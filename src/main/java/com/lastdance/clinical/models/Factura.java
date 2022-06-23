package com.lastdance.clinical.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private double monto;
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.EAGER)
    private Set<PacienteServicio> servicios = new HashSet<>();

    @OneToMany(mappedBy = "paciente", fetch = FetchType.EAGER)
    private Set<PacienteProducto> productos = new HashSet<>();


    //    ======METODOS CONSTRUCTORES======

    public Factura() {
    }

    public Factura(double monto) {
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

    public Factura(double monto, Set<PacienteServicio> servicios, Set<PacienteProducto> productos) {
        this.monto = monto;
        this.fecha = LocalDateTime.now();
        this.servicios = servicios;
        this.productos = productos;
    }

    //    ======METODOS ACCESORES======

    public Long getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Set<PacienteServicio> getServicios() {
        return servicios;
    }

    public void setServicios(Set<PacienteServicio> servicios) {
        this.servicios = servicios;
    }

    public Set<PacienteProducto> getProductos() {
        return productos;
    }

    public void setProductos(Set<PacienteProducto> productos) {
        this.productos = productos;
    }


    //    ======METODOS======

    public void addPacienteProducto(PacienteProducto pacienteProducto) {
        pacienteProducto.setFactura(this);
        productos.add(pacienteProducto);
    }

    public void addPacienteServicio(PacienteServicio pacienteServicio) {
        pacienteServicio.setFactura(this);
        servicios.add(pacienteServicio);
    }

    public void addPacienteProductoServicio(PacienteServicio pacienteServicio, PacienteProducto pacienteProducto) {
        pacienteServicio.setFactura(this);
        pacienteProducto.setFactura(this);
        servicios.add(pacienteServicio);
        productos.add(pacienteProducto);
    }


}
