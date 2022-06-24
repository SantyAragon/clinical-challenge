package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.Profesional;
import com.lastdance.clinical.models.Servicio;
import com.lastdance.clinical.models.TipoEspecialidad;

public class ProfesionalDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private TipoEspecialidad especialidad;

    private boolean activo;
//    private Servicio servicio;

    public ProfesionalDTO() {
    }

    public ProfesionalDTO(Profesional profesional) {
        this.id = profesional.getId();
        this.nombre = profesional.getNombre();
        this.apellido = profesional.getApellido();
        this.especialidad = profesional.getEspecialidad();
        this.activo = profesional.isActivo();
//        this.servicio = profesional.getServicio();
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

    public TipoEspecialidad getEspecialidad() {
        return especialidad;
    }

    public boolean isActivo() {
        return activo;
    }

    //    public Servicio getServicio() {
//        return servicio;
//    }
}
