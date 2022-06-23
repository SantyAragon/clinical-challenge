package com.lastdance.clinical.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PacienteServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private Double monto;
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "factura_id")
    private Factura factura;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;


    public PacienteServicio() {
    }

    public PacienteServicio(Double monto, LocalDateTime fecha, Factura factura, Servicio servicio) {
        this.monto = monto;
        this.fecha = fecha;
        this.factura = factura;
        this.servicio = servicio;
    }

    public Long getId() {
        return id;
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

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
}
