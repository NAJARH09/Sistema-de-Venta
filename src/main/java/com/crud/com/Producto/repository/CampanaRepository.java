package com.crud.com.Producto.repository;

import com.crud.com.Producto.model.Campana;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampanaRepository extends JpaRepository<Campana,Integer> {
    boolean existsByNombre(String nombre);
}
