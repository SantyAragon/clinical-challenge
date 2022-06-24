package com.lastdance.clinical.services.implement;

import com.lastdance.clinical.models.PacienteProducto;
import com.lastdance.clinical.repositories.PacienteProductoRepository;
import com.lastdance.clinical.services.PacienteProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteProductoServiceImplements implements PacienteProductoService {
    @Autowired
    PacienteProductoRepository pacienteProductoRepository;

    @Override
    public void guardarPacienteProducto(PacienteProducto pacienteProducto) {
        pacienteProductoRepository.save(pacienteProducto);
    }
}
