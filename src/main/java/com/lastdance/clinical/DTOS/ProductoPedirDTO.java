package com.lastdance.clinical.DTOS;

public class ProductoPedirDTO {
    private Long idProducto;
    private int cantidad;

    public ProductoPedirDTO(Long idProducto, int cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }
}
