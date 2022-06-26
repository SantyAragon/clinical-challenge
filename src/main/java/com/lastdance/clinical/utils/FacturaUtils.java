package com.lastdance.clinical.utils;

import com.lastdance.clinical.DTOS.GenerarFacturaDTO;
import com.lastdance.clinical.models.Producto;

import java.util.Set;


public final class FacturaUtils {

    public static boolean checkearStock(GenerarFacturaDTO generarFacturaDTO,Set<Producto> productos) {

        if (productos.stream().anyMatch(producto -> producto.getStock() < generarFacturaDTO.getProductos().stream().filter(prod -> prod.getIdProducto() == producto.getId()).findFirst().orElseThrow().getCantidad()))
            return true;
        if (productos.stream().anyMatch(producto -> generarFacturaDTO.getProductos().stream().filter(prod -> prod.getIdProducto() == producto.getId()).findFirst().orElseThrow().getCantidad() <=0 ))
            return true;

        else
            return false;
    }
}
