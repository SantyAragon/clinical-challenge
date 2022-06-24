package com.lastdance.clinical.controllers;

import com.lastdance.clinical.DTOS.PacienteDTO;
import com.lastdance.clinical.models.Paciente;
import com.lastdance.clinical.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @GetMapping("/pacientes")
    public Set<PacienteDTO> traerPacientesActivos() {
        return pacienteService.traerPacientes().stream().filter(paciente -> paciente.isActivo()).map(paciente -> new PacienteDTO(paciente)).collect(Collectors.toSet());
    }
    @GetMapping("/pacientes/all")
    public Set<PacienteDTO> traerTodosLosPacientes() {
        return pacienteService.traerPacientes().stream().map(paciente -> new PacienteDTO(paciente)).collect(Collectors.toSet());
    }
    @GetMapping("/pacientes/{id}")
    public PacienteDTO traerPaciente(@PathVariable Long id) {
        return pacienteService.traerPacienteDTO(id);
    }

    @PostMapping("/pacientes")
    public ResponseEntity<Object> registrarPaciente(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String contraseña, @RequestParam Long identificacion) {

        Paciente paciente = new Paciente(nombre, apellido, email, contraseña, identificacion);
        pacienteService.guardarPaciente(paciente);

        return new ResponseEntity<>("Registrado exitosamente", HttpStatus.ACCEPTED);

    }

    @PatchMapping("/pacientes/{id}/nombre")
    public ResponseEntity<Object> actualizarNombrePaciente(@PathVariable Long id, @RequestParam String nombre) {
        if (nombre.length() < 4) {
            return new ResponseEntity<>("Nombre demasiado corto", HttpStatus.FORBIDDEN);
        }
        Paciente paciente = pacienteService.traerPaciente(id);
        paciente.setNombre(nombre);
        pacienteService.guardarPaciente(paciente);
        return new ResponseEntity<>("Modificacion de nombre exitosa", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/pacientes/{id}/apellido")
    public ResponseEntity<Object> actualizarApellidoPaciente(@PathVariable Long id, @RequestParam String apellido) {
        if (apellido.length() < 4) {
            return new ResponseEntity<>("Apellido demasiado corto", HttpStatus.FORBIDDEN);
        }
        Paciente paciente = pacienteService.traerPaciente(id);
        paciente.setApellido(apellido);
        pacienteService.guardarPaciente(paciente);
        return new ResponseEntity<>("Modificacion de apellido exitosa", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/pacientes/{id}/email")
    public ResponseEntity<Object> actualizarEmailPaciente(@PathVariable Long id, @RequestParam String email) {

        Set<String> emails = pacienteService.traerPacientes().stream().map(Paciente::getEmail).collect(Collectors.toSet());

        if (emails.contains(email))
            return new ResponseEntity<>("Email ya en uso", HttpStatus.FORBIDDEN);

        Paciente paciente = pacienteService.traerPaciente(id);
        paciente.setEmail(email);
        pacienteService.guardarPaciente(paciente);
        return new ResponseEntity<>("Modificacion de email exitosa", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/pacientes/{id}")
    public ResponseEntity<Object> desactivarPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteService.traerPaciente(id);
        if (!paciente.isActivo()) {
            return new ResponseEntity<>("Paciente ya deshabiliado", HttpStatus.FORBIDDEN);
        }
        paciente.setActivo(false);
        pacienteService.guardarPaciente(paciente);
        return new ResponseEntity<>("Modificacion de estado exitosa", HttpStatus.ACCEPTED);
    }
}

