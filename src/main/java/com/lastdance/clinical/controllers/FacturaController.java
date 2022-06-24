package com.lastdance.clinical.controllers;

import com.lastdance.clinical.DTOS.FacturaDTO;
import com.lastdance.clinical.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class FacturaController {
    @Autowired
    FacturaService facturaService;

    @GetMapping("/facturas")
    public Set<FacturaDTO> traerFacturas() {
        return facturaService.traerFacturasDTO();
    }

    @GetMapping("/facturas/{id}")
    public FacturaDTO traerFactura(@PathVariable Long id) {
        return facturaService.traerFacturaDTO(id);
    }

    
}
