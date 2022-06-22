package com.lastdance.clinical.repositories;

import com.lastdance.clinical.models.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RestController;

@RepositoryRestResource
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {
}
