package com.lastdance.clinical.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private boolean activo;
    private String nombre;
    private TipoProducto tipo;
    private long stock;
    private Double precio;
    private String imagen;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private Set<PacienteProducto> pacienteProductos = new HashSet<>();

    public Producto() {
    }

    public Producto(String nombre, TipoProducto tipo, long stock, Double precio, String imagen) {
        this.activo = true;
        this.nombre = nombre;
        this.tipo = tipo;
        this.stock = stock;
        this.precio = precio;
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<PacienteProducto> getPacienteProductos() {
        return pacienteProductos;
    }

    public void setPacienteProductos(Set<PacienteProducto> pacienteProductos) {
        this.pacienteProductos = pacienteProductos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
