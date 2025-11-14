package com.crud.com.Producto.repository;

import com.crud.com.Producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    List<Producto> findByClienteId(Long clienteId);
}
