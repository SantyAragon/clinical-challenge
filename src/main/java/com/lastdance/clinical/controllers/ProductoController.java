package com.lastdance.clinical.controllers;

import com.lastdance.clinical.DTOS.ProductoDTO;
import com.lastdance.clinical.DTOS.ProfesionalDTO;
import com.lastdance.clinical.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")

public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/productos")
    public Set<ProductoDTO> traerProductos() {
        return productoService.traerProductos().stream().map(producto -> new ProductoDTO(producto)).collect(Collectors.toSet());
    }

    @GetMapping("/productos/{id}")
    public ProductoDTO traerProducto(@PathVariable Long id) {
        return productoService.traerProductoDTO(id);
    }
}
