package com.lastdance.clinical.repositories;

import com.lastdance.clinical.models.PacienteServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PacienteServicioRepository extends JpaRepository<PacienteServicio, Long> {
}
