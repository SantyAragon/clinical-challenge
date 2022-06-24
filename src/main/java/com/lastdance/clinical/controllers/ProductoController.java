package com.lastdance.clinical.controllers;

import com.lastdance.clinical.DTOS.ProductoDTO;
import com.lastdance.clinical.DTOS.ProfesionalDTO;
import com.lastdance.clinical.models.Producto;
import com.lastdance.clinical.models.TipoProducto;
import com.lastdance.clinical.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")

public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/productos")
    public Set<ProductoDTO> traerProductos() {
        return productoService.traerProductos().stream().filter(producto -> producto.isActivo()).map(producto -> new ProductoDTO(producto)).collect(Collectors.toSet());
    }

    @GetMapping("/productos/{id}")
    public ProductoDTO traerProducto(@PathVariable Long id) {
        return productoService.traerProductoDTO(id);
    }

    @PostMapping("/productos")
    public ResponseEntity<Object> nuevoProducto (@RequestParam String nombre, @RequestParam TipoProducto tipo, @RequestParam long stock, @RequestParam Double precio) {
        if(nombre.isEmpty() || tipo == null || stock <= 0 || precio <= 0) {
            return new ResponseEntity<>("Faltan datos", HttpStatus.FORBIDDEN);
        }
        Producto producto = new Producto(nombre, tipo, stock, precio);
        productoService.guardarProducto(producto);
        return new ResponseEntity<>("Producto creado", HttpStatus.CREATED);
    }

    @PatchMapping("/productos/{id}/nombre")
    public ResponseEntity<Object> modificarNombreProducto (@PathVariable Long id, @RequestParam String nombre) {
        Producto producto = productoService.traerProducto(id);
        producto.setNombre(nombre);
        productoService.guardarProducto(producto);
        return new ResponseEntity<>("Nombre Producto editado", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/productos/{id}/tipo")
    public ResponseEntity<Object> modificarTipoProducto (@PathVariable Long id, @RequestParam TipoProducto tipo) {
        Producto producto = productoService.traerProducto(id);
        producto.setTipo(tipo);
        productoService.guardarProducto(producto);
        return new ResponseEntity<>("Tipo Producto editado", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/productos/{id}/stock")
    public ResponseEntity<Object> modificarStockProducto (@PathVariable Long id, @RequestParam long stock) {
        Producto producto = productoService.traerProducto(id);
        producto.setStock(stock);
        productoService.guardarProducto(producto);
        return new ResponseEntity<>("Stock Producto editado", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/productos/{id}/precio")
    public ResponseEntity<Object> modificarPrecioProducto (@PathVariable Long id, @RequestParam Double precio) {
        Producto producto = productoService.traerProducto(id);
        producto.setPrecio(precio);
        productoService.guardarProducto(producto);
        return new ResponseEntity<>("Precio Producto editado", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/productos/{id}")
    public ResponseEntity<Object> eliminarProducto (@PathVariable Long id) {
        Producto producto = productoService.traerProducto(id);
        if(producto.getStock() > 0){
            return new ResponseEntity<>("Todavia hay stock", HttpStatus.FORBIDDEN);
        }
        producto.setActivo(false);
        productoService.guardarProducto(producto);
        return new ResponseEntity<>("Producto eliminado", HttpStatus.ACCEPTED);
    }


}
