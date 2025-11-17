package com.crud.com.Producto.service;

import com.crud.com.Producto.model.Campana;
import com.crud.com.Producto.repository.CampanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampanaService {
    @Autowired
    private CampanaRepository repo;

    public void guardar(Campana campana) {

        // Validar nombre vacío
        if (campana.getNombre() == null || campana.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la campaña no puede estar vacío");
        }

        // Validar fechas nulas
        if (campana.getFechaInicio() == null || campana.getFechaFin() == null) {
            throw new IllegalArgumentException("Debe ingresar fecha de inicio y fecha de fin");
        }

        // Validar orden de fechas
        if (campana.getFechaFin().isBefore(campana.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser menor que la fecha de inicio");
        }

        // Validar nombre duplicado
        if (repo.existsByNombre(campana.getNombre())) {
            throw new IllegalArgumentException("Ya existe una campaña con ese nombre");
        }

        // Guardar
        repo.save(campana);
    }
    public void eliminar(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("La campaña no existe");
        }
        repo.deleteById(id);
    }



}