package com.lastdance.clinical.repositories;

import com.lastdance.clinical.models.Factura;
import com.lastdance.clinical.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Factura findByPaciente(Paciente paciente);
}
