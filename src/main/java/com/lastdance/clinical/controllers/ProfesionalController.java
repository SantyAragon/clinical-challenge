package com.lastdance.clinical.controllers;

import com.lastdance.clinical.DTOS.ProfesionalDTO;
import com.lastdance.clinical.models.*;
import com.lastdance.clinical.services.PacienteService;
import com.lastdance.clinical.services.ProfesionalService;
import com.lastdance.clinical.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProfesionalController {
    @Autowired
    ProfesionalService profesionalService;

    @Autowired
    ServicioService servicioService;

    @GetMapping("/profesionales")
    public Set<ProfesionalDTO> traerProfesionales() {
        return profesionalService.traerProfesionales().stream().filter(profesional -> profesional.isActivo()).map(profesional -> new ProfesionalDTO(profesional)).collect(Collectors.toSet());
    }

    @GetMapping("/profesionales/{id}")
    public ProfesionalDTO traerProfesional(@PathVariable Long id) {
        return profesionalService.traerProfesionalDTO(id);
    }


    @PostMapping("/profesional")
    public ResponseEntity<Object> nuevoProfesional (@RequestParam String nombre, @RequestParam String apellido, @RequestParam TipoEspecialidad especialidad, @RequestParam Long servicioId, @RequestParam String email, @RequestParam String contraseña) {

        Servicio servicio = servicioService.traerServicio(servicioId);

        Profesional profesional = new Profesional(nombre, apellido, especialidad, servicio, email, contraseña);
        profesionalService.guardarProfesinal(profesional);

        servicio.addProfesional(profesional);
        servicioService.guardarServicio(servicio);

        return new ResponseEntity<>("Profesional creado", HttpStatus.CREATED);
    }

    @PatchMapping("/profesional/{id}/nombre")
    public ResponseEntity<Object> editarNombreProfesional (@PathVariable Long id, @RequestParam String nombre) {
        Profesional profesional = profesionalService.traerProfesional(id);
        profesional.setNombre(nombre);
        profesionalService.guardarProfesinal(profesional);
        return new ResponseEntity<>("Nombre profesional editado", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/profesional/{id}/apellido")
    public ResponseEntity<Object> editarApellidoProfesional (@PathVariable Long id, @RequestParam String apellido) {
        Profesional profesional = profesionalService.traerProfesional(id);
        profesional.setApellido(apellido);
        profesionalService.guardarProfesinal(profesional);
        return new ResponseEntity<>("Apellido profesional editado", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/profesional/{id}/especialidad")
    public ResponseEntity<Object> editarEspecialidadProfesional (@PathVariable Long id, @RequestParam TipoEspecialidad especialidad) {
        Profesional profesional = profesionalService.traerProfesional(id);
        profesional.setEspecialidad(especialidad);
        profesionalService.guardarProfesinal(profesional);
        return new ResponseEntity<>("Especialidad profesional editado", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/profesional/{id}/servicio")
    public ResponseEntity<Object> editarServicioProfesional (@PathVariable Long id, @RequestParam Long servicioId) {
        Profesional profesional = profesionalService.traerProfesional(id);
        Servicio servicio = servicioService.traerServicio(servicioId);

        profesional.setServicio(servicio);
        servicio.addProfesional(profesional);

        profesionalService.guardarProfesinal(profesional);
        servicioService.guardarServicio(servicio);
        return new ResponseEntity<>("Servicio profesional editado", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/profesional/{id}")
    public ResponseEntity<Object> borrarProfesional (@PathVariable Long id) {

        Profesional profesional = profesionalService.traerProfesional(id);
        Set<PacienteServicio> turnos = profesional.getServicio().getPacienteServicios();

        if(turnos.size() > 0){
            return new ResponseEntity<>("El profesional tiene turnos dados", HttpStatus.FORBIDDEN);
        }

        profesional.setActivo(false);

        profesionalService.guardarProfesinal(profesional);

        return new ResponseEntity<>("Profesional borrado", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/profesional/{id}/email")
    public ResponseEntity<Object> editarEmailProfesional (@PathVariable Long id, @RequestParam String email) {
        Set<String> emails = profesionalService.traerProfesionales().stream().map(Profesional::getEmail).collect(Collectors.toSet());

        if (emails.contains(email))
            return new ResponseEntity<>("Email ya en uso", HttpStatus.FORBIDDEN);
        if (!email.contains("@") || !email.endsWith(".com") || !email.endsWith(".net") || !email.endsWith(".es") || !email.endsWith(".com.ar"))
            return new ResponseEntity<>("Email ya en uso", HttpStatus.FORBIDDEN);

        Profesional profesional = profesionalService.traerProfesional(id);
        profesional.setEmail(email);
        profesionalService.guardarProfesinal(profesional);
        return new ResponseEntity<>("Email profesional editado", HttpStatus.ACCEPTED);
    }
}
