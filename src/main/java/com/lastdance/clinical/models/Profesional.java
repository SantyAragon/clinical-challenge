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
    private boolean activo;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private TipoEspecialidad especialidad;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_servicio_id")
    private PacienteServicio pacienteServicio;


    public Profesional() {
    }

    public Profesional(String nombre, String apellido, TipoEspecialidad especialidad, String email, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.email = email;
        this.contraseña = contraseña;
        this.activo = true;
    }

    public Profesional(String nombre, String apellido, TipoEspecialidad especialidad, Servicio servicio, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.servicio = servicio;
        this.email = email;
        this.activo = true;
    }
    public Profesional(String nombre, String apellido, TipoEspecialidad especialidad, Servicio servicio, String email, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.servicio = servicio;
        this.email = email;
        this.contraseña = contraseña;
        this.activo = true;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean visible) {
        this.activo = visible;
    }

    public String getFullName() {
        return nombre + " " + apellido;
    }

    public PacienteServicio getPacienteServicio() {
        return pacienteServicio;
    }

    public void setPacienteServicio(PacienteServicio pacienteServicio) {
        this.pacienteServicio = pacienteServicio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
