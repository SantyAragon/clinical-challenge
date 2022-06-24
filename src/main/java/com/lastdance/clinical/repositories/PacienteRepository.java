package com.lastdance.clinical.repositories;

import com.lastdance.clinical.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByEmail(String email);
    Paciente findByIdentificacion(Long identificacion);
}
