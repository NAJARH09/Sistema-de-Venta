package com.crud.com.Producto.repository;

import com.crud.com.Producto.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    List<Cliente> findByCampanaId(Integer idCampana);

    // Para creación
    boolean existsByNombreAndCampanaId(String nombre, int campanaId);
    boolean existsByTelefonoAndCampanaId(String telefono, int campanaId);

    // Para edición (excluyendo el cliente actual)
    boolean existsByNombreAndCampanaIdAndIdNot(String nombre, int campanaId, int id);
    boolean existsByTelefonoAndCampanaIdAndIdNot(String telefono, int campanaId, int id);






}
//tenia un error de Jpa pero es por la version deberia usar unaanterior porque las mas nuevas teine eporblema
