package com.lastdance.clinical.controllers;

import com.lastdance.clinical.DTOS.ServicioDTO;
import com.lastdance.clinical.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ServicioController {
    @Autowired
    ServicioService servicioService;

    @GetMapping("/servicios")
    public Set<ServicioDTO> traerServicios() {
        return servicioService.traerServicios().stream().map(servicio -> new ServicioDTO(servicio)).collect(Collectors.toSet());
    }

    @GetMapping("/servicios/{id}")
    public ServicioDTO traerServicio(@PathVariable Long id) {
        return servicioService.traerServicioDTO(id);
    }

}


