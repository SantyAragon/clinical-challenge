package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.Producto;
import com.lastdance.clinical.models.TipoProducto;

public class ProductoDTO {

    private Long id;
    private boolean activo;
    private String nombre;
    private TipoProducto tipo;
    private long stock;
    private Double precio;
    private String imagen;


    public ProductoDTO() {
    }

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.activo = producto.isActivo();
        this.nombre = producto.getNombre();
        this.tipo = producto.getTipo();
        this.stock = producto.getStock();
        this.precio = producto.getPrecio();
        this.imagen= producto.getImagen();
    }

    public Long getId() {
        return id;
    }

    public boolean isActivo() {
        return activo;
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

    public String getImagen() {
        return imagen;
    }
}
