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
    public Set<PacienteDTO> traerPacientes() {
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
//    @PatchMapping("/pacientes/{id}")
//    public ResponseEntity<Object> actualizarPaciente(@PathVariable Long id,){
//
//    }
}
