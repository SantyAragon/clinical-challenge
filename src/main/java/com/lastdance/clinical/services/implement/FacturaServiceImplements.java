package com.lastdance.clinical.services.implement;

import com.lastdance.clinical.DTOS.FacturaDTO;
import com.lastdance.clinical.models.Factura;
import com.lastdance.clinical.models.Paciente;
import com.lastdance.clinical.repositories.FacturaRepository;
import com.lastdance.clinical.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacturaServiceImplements implements FacturaService {
    @Autowired
    FacturaRepository facturaRepository;

    @Override
    public List<Factura> traerFacturas() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura traerFactura(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    @Override
    public Set<FacturaDTO> traerFacturasDTO() {
        return facturaRepository.findAll().stream().map(FacturaDTO::new).collect(Collectors.toSet());
    }

    @Override
    public Factura traerFacturaPorPaciente(Paciente paciente) {
        return facturaRepository.findByPaciente(paciente);
    }

    @Override
    public FacturaDTO traerFacturaDTO(Long id) {
        return facturaRepository.findById(id).map(FacturaDTO::new).orElse(null);
    }


    @Override
    public void guardarFactura(Factura factura) {
        facturaRepository.save(factura);
    }
}
