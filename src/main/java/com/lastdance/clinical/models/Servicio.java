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

    @OneToMany(mappedBy = "servicio", fetch = FetchType.EAGER)
    private Set<Profesional> profesionales = new HashSet<>();

    public Servicio() {
    }
    public Servicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
    public Servicio(TipoServicio tipoServicio,Profesional profesional) {
        this.tipoServicio = tipoServicio;
        this.profesionales.add(profesional);
    }
    public Servicio(TipoServicio tipoServicio, Set<Profesional> profesionales) {
        this.tipoServicio = tipoServicio;
        this.profesionales = profesionales;
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

    public Set<Profesional> getProfecionales() {
        return profesionales;
    }

    public void setProfesionales(Set<Profesional> profesionales) {
        this.profesionales = profesionales;
    }

    public void addProfesional(Profesional profesional) {
        profesional.setServicio(this);
        profesionales.add(profesional);

    }

}
