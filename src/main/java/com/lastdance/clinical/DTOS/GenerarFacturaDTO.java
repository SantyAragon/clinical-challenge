package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.PacienteProducto;

import java.util.HashSet;
import java.util.Set;

public class GenerarFacturaDTO {


    private Set<ProductoPedirDTO> productos = new HashSet<>();
    private Set<ServicioPedirDTO> servicios= new HashSet<>();

    public GenerarFacturaDTO(Set<ProductoPedirDTO> productos, Set<ServicioPedirDTO> servicios) {
        this.productos = productos;
        this.servicios = servicios;
    }

    public Set<ProductoPedirDTO> getProductos() {
        return productos;
    }

    public Set<ServicioPedirDTO> getServicios() {
        return servicios;
    }
//    {
//        productos:[{id:1,cantidad:2},{id:2,cantidad:5}{id:3,cantidad:10}],
//        servicios:[{id:1,fecha:LocalDateTime},{id:2,fecha:LocalDateTime},{id:3,fecha:LocalDateTime}]
//    }
}
