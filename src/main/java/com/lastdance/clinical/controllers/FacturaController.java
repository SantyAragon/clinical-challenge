package com.lastdance.clinical.controllers;

import com.lastdance.clinical.DTOS.FacturaDTO;
import com.lastdance.clinical.DTOS.GenerarFacturaDTO;
import com.lastdance.clinical.models.*;
import com.lastdance.clinical.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FacturaController {
    @Autowired
    FacturaService facturaService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    ServicioService servicioService;
    @Autowired
    ProductoService productoService;
    @Autowired
    PacienteServicioService pacienteServicioService;
    @Autowired
    PacienteProductoService pacienteProductoService;

    @GetMapping("/facturas")
    public Set<FacturaDTO> traerFacturas() {
        return facturaService.traerFacturasDTO();
    }
    @GetMapping("/facturas/{id}")
    public FacturaDTO traerFactura(@PathVariable Long id) {
        return facturaService.traerFacturaDTO(id);
    }

    @PostMapping("/facturas/create")
    public ResponseEntity<Object> crearFactura(@RequestBody GenerarFacturaDTO generarFacturaDTO) {
        Paciente paciente = pacienteService.traerPaciente(1L);//authentication

        Set<Servicio> servicios = generarFacturaDTO.getServicios().stream().map(serv -> servicioService.traerServicio(serv.getIdServicio())).collect(Collectors.toSet());
        Set<Producto> productos = generarFacturaDTO.getProductos().stream().map(prod -> productoService.traerProducto(prod.getIdProducto())).collect(Collectors.toSet());


        Factura factura = new Factura(paciente);
        facturaService.guardarFactura(factura);
        Set<Double> subTotalServicios = new HashSet<>();
        if (servicios.size() > 0) {
            servicios.forEach(servicioATomar -> {
                        LocalDateTime fechaServicioATomar = generarFacturaDTO.getServicios().stream().filter(serv1 -> serv1.getIdServicio() == servicioATomar.getId()).findFirst().orElseThrow().getFecha();

                        PacienteServicio pacienteServicio = new PacienteServicio(servicioATomar.getMonto(), fechaServicioATomar, factura, servicioATomar);
                        subTotalServicios.add(pacienteServicio.getMonto());
                        pacienteServicioService.guardarPacienteServicio(pacienteServicio);
                        factura.addPacienteServicio(pacienteServicio);
                    }
            );
        }
        Set<Double> subTotalProductos = new HashSet<>();
        if (productos.size() > 0) {
            productos.forEach(productoAComprar -> {
                int cantidadAComprar = generarFacturaDTO.getProductos().stream().filter(prod -> prod.getIdProducto() == productoAComprar.getId()).findFirst().orElseThrow().getCantidad();

                PacienteProducto pacienteProducto = new PacienteProducto(cantidadAComprar, LocalDateTime.now(), factura, productoAComprar);
                subTotalProductos.add(pacienteProducto.getMonto());
                pacienteProductoService.guardarPacienteProducto(pacienteProducto);
                factura.addPacienteProducto(pacienteProducto);
            });
        }
        Double totalServicios = subTotalServicios.stream().reduce(Double::sum).orElse(0d);
        Double totalProductos = subTotalProductos.stream().reduce(Double::sum).orElse(0d);
        factura.setMonto(totalProductos + totalServicios);
        facturaService.guardarFactura(factura);


        return new ResponseEntity<>("Factura generada exitosamente", HttpStatus.ACCEPTED);
    }
}
