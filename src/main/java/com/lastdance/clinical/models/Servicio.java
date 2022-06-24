package com.lastdance.clinical.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private TipoServicio tipoServicio;
    private Double monto;

    @OneToMany(mappedBy = "servicio", fetch = FetchType.EAGER)
    private Set<Profesional> profesionales = new HashSet<>();

    @OneToMany(mappedBy = "servicio", fetch = FetchType.EAGER)
    private Set<PacienteServicio> pacienteServicios = new HashSet<>();

    private boolean activo;


    public Servicio() {
    }

    public Servicio(TipoServicio tipoServicio, Double monto) {
        this.tipoServicio = tipoServicio;
        this.monto = monto;
        this.activo = true;
    }

    public Servicio(TipoServicio tipoServicio, Double monto, Profesional profesional) {
        this.tipoServicio = tipoServicio;
        this.monto = monto;
        this.profesionales.add(profesional);
        this.activo = true;
    }

    public Servicio(TipoServicio tipoServicio, Double monto, Set<Profesional> profesionales) {
        this.tipoServicio = tipoServicio;
        this.monto = monto;
        this.profesionales = profesionales;
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Set<Profesional> getProfesionales() {
        return profesionales;
    }

    public void setProfesionales(Set<Profesional> profesionales) {
        this.profesionales = profesionales;
    }

    public void addProfesional(Profesional profesional) {
        profesional.setServicio(this);
        profesionales.add(profesional);

    }

    public Set<PacienteServicio> getPacienteServicios() {
        return pacienteServicios;
    }

    public void setPacienteServicios(Set<PacienteServicio> pacienteServicios) {
        this.pacienteServicios = pacienteServicios;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
