package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.Producto;
import com.lastdance.clinical.models.TipoProducto;

public class ProductoDTO {

    private Long id;
    private String nombre;
    private TipoProducto tipo;
    private long stock;
    private Double precio;

    public ProductoDTO() {  }
    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.tipo = producto.getTipo();
        this.stock = producto.getStock();
        this.precio = producto.getPrecio();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public long getStock() {
        return stock;
    }

    public Double getPrecio() {
        return precio;
    }
}
