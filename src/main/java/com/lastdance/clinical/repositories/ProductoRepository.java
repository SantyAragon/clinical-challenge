package com.lastdance.clinical.repositories;

import com.lastdance.clinical.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductoRepository extends JpaRepository<Producto,Long> {
}
