package com.lastdance.clinical.repositories;

import com.lastdance.clinical.models.PacienteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PacienteProductoRepository extends JpaRepository<PacienteProducto, Long> {
}
