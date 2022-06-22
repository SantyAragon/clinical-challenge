package com.lastdance.clinical.repositories;

import com.lastdance.clinical.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RestController;

@RepositoryRestResource
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
}
