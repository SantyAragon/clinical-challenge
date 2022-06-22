package com.lastdance.clinical.services;

import com.lastdance.clinical.DTOS.ProductoDTO;
import com.lastdance.clinical.models.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> traerProductos();
    ProductoDTO traerProductoDTO(Long id);
    Producto traerProducto(Long id);
    void guardarProducto(Producto producto);






}
