package com.lastdance.clinical.controllers;

import com.lastdance.clinical.DTOS.ServicioDTO;
import com.lastdance.clinical.models.Profesional;
import com.lastdance.clinical.models.Servicio;
import com.lastdance.clinical.models.TipoServicio;
import com.lastdance.clinical.services.ProfesionalService;
import com.lastdance.clinical.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ServicioController {
    @Autowired
    ServicioService servicioService;
    @Autowired
    ProfesionalService profesionalService;

    @GetMapping("/servicios")
    public Set<ServicioDTO> traerServicios() {
        return servicioService.traerServicios().stream().filter(servicio -> servicio.isActivo()).map(servicio -> new ServicioDTO(servicio)).collect(Collectors.toSet());
    }

    @GetMapping("/servicios/{id}")
    public ServicioDTO traerServicio(@PathVariable Long id) {
        return servicioService.traerServicioDTO(id);
    }

    @PostMapping("/servicios")
    public ResponseEntity<Object> nuevoServicio (@RequestParam TipoServicio tipoServicio, @RequestParam List<Profesional> profesionales) {

        Set<Profesional> setProfesionales = new HashSet<>(profesionales);

        Servicio servicio = new Servicio(tipoServicio, setProfesionales);
        servicioService.guardarServicio(servicio);

        return new ResponseEntity<>("Servicio creado", HttpStatus.CREATED);
    }

    @PatchMapping("servicios/profesional/{id}")
    public ResponseEntity<Object> removerProfesional (@PathVariable Long id, @RequestParam Long profesionalId) {

        Servicio servicio = servicioService.traerServicio(id);
        Profesional profesional = profesionalService.traerProfesional(profesionalId);

        Set<Profesional> profesionalesAct = servicio.getProfesionales();
        profesionalesAct.remove(profesional);
        servicio.setProfesionales(profesionalesAct);

        profesional.setServicio(null);
        servicioService.guardarServicio(servicio);

        return new ResponseEntity<>("Profesional removido", HttpStatus.ACCEPTED);
    }

    @PatchMapping("servicios/profesionales/{id}")
    public ResponseEntity<Object> agregarProfesional (@PathVariable Long id, @RequestParam Long profesionaId) {

        Servicio servicio = servicioService.traerServicio(id);
        Profesional profesional = profesionalService.traerProfesional(profesionaId);

        if (!profesional.isActivo()) {
            profesional.setActivo(true);
        }

        servicio.addProfesional(profesional);

        profesionalService.guardarProfesinal(profesional);
        servicioService.guardarServicio(servicio);

        return new ResponseEntity<>("Profesional agregado", HttpStatus.ACCEPTED);

    }

    @PatchMapping("servicios/{id}")
    public ResponseEntity<Object> eliminarServicio (@PathVariable Long id) {
        Servicio servicio = servicioService.traerServicio(id);

        Set<Profesional> profesionales = servicio.getProfesionales();
        profesionales.forEach(profesional -> profesional.setServicio(null));

        servicio.getProfesionales().removeAll(profesionales);

        servicio.setActivo(false);

        profesionales.forEach(profesional -> profesionalService.guardarProfesinal(profesional));
        servicioService.guardarServicio(servicio);

        return new ResponseEntity<>("Servicio eliminado", HttpStatus.ACCEPTED);
    }



}


