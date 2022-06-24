package com.lastdance.clinical.services;

import com.lastdance.clinical.DTOS.FacturaDTO;
import com.lastdance.clinical.models.Factura;
import com.lastdance.clinical.models.Paciente;

import java.util.List;
import java.util.Set;

public interface FacturaService {
    List<Factura> traerFacturas();

    Factura traerFactura(Long id);

    Set<FacturaDTO> traerFacturasDTO();

    FacturaDTO traerFacturaDTO(Long id);

    Factura traerFacturaPorPaciente(Paciente paciente);

    void guardarFactura(Factura factura);

}
