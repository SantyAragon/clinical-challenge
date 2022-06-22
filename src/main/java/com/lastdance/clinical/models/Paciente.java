package com.lastdance.clinical.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private Long identificacion;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.EAGER)
    private Set<PacienteServicio> servicios = new HashSet<>();

//    private Set<Servicio> servicios = new HashSet<>();

    public Paciente() {
    }

    public Paciente(String nombre, String apellido, String email, String contraseña, Long identificacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.identificacion = identificacion;
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

    public Long getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Long identificacion) {
        this.identificacion = identificacion;
    }

    public Set<PacienteServicio> getServicios() {
        return servicios;
    }

    public void setServicios(Set<PacienteServicio> servicios) {
        this.servicios = servicios;
    }


    public String getFullName() {
        return nombre + " " + apellido;
    }
}
