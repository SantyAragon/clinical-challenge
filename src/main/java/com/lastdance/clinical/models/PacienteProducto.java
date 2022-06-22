package com.lastdance.clinical.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PacienteProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nombre;
    private Double monto;
    private LocalDateTime fecha;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="paciente_id")
    private Paciente paciente;
    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="producto_id")
    private Producto producto;*/

    public PacienteProducto() { }

    public PacienteProducto(Long id, String nombre, Double monto, LocalDateTime fecha, Paciente paciente) {
        this.id = id;
        this.nombre = nombre;
        this.monto = monto;
        this.fecha = fecha;
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /*public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }*/
}
