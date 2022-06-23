package com.lastdance.clinical.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Profesional {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nombre;
    private String apellido;
    private TipoEspecialidad especialidad;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="servicio_id")
    private Servicio servicio;

    public Profesional() { }

    public Profesional(String nombre, String apellido, TipoEspecialidad especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }
    public Profesional(String nombre, String apellido, TipoEspecialidad especialidad, Servicio servicio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.servicio = servicio;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoEspecialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(TipoEspecialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public String getFullName() {
        return nombre + " " + apellido;
    }
}
