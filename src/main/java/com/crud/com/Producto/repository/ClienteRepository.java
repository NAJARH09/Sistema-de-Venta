package com.crud.com.Producto.repository;

import com.crud.com.Producto.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
}
//tenia un error de Jpa pero es por la version deberia usar unaanterior porque las mas nuevas teine eporblema
