package com.lastdance.clinical.services.implement;

import com.lastdance.clinical.DTOS.ProductoDTO;
import com.lastdance.clinical.models.Producto;
import com.lastdance.clinical.repositories.ProductoRepository;
import com.lastdance.clinical.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImplements implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> traerProductos() {
        return productoRepository.findAll();
    }

    @Override
    public ProductoDTO traerProductoDTO(Long id) {
        return productoRepository.findById(id).map(ProductoDTO::new).orElse(null);
    }

    @Override
    public Producto traerProducto(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public void guardarProducto(Producto producto) {
        productoRepository.save(producto);

    }
}
