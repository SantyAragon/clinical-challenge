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
    private Set<Profesional> profesionals = new HashSet<>();

    public Servicio() {
    }

    public Servicio(TipoServicio tipoServicio, Double monto) {
        this.tipoServicio = tipoServicio;
        this.monto = monto;
    }

    public Servicio(TipoServicio tipoServicio, Double monto, Profesional profesional) {
        this.tipoServicio = tipoServicio;
        this.monto = monto;
        this.profesionals.add(profesional);
    }

    public Servicio(TipoServicio tipoServicio, Double monto, Set<Profesional> profesionales) {
        this.tipoServicio = tipoServicio;
        this.monto = monto;
        this.profesionals = profesionales;
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

    public Set<Profesional> getProfesionals() {
        return profesionals;
    }

    public void setProfesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
    }

    public void addProfesional(Profesional profesional) {
        profesional.setServicio(this);
        profesionals.add(profesional);

    }

}
