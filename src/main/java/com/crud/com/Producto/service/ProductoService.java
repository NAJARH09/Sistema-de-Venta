package com.crud.com.Producto.service;

import org.springframework.stereotype.Service;

/**
 * ==========================================
 *  PRODUCTO SERVICE (LÓGICA DE NEGOCIO)
 * ==========================================
 *
 * 1. @Service indica que esta clase pertenece
 *    a la capa de Servicio en la arquitectura.
 *
 * 2. Aquí van las reglas de negocio. En este caso:
 *      - Cálculo del importe = precio × cantidad.
 *
 * 3. Tenerlo en un servicio permite:
 *      - Reutilizarlo desde varios controladores.
 *      - Mantener limpio el controlador.
 *      - Seguir el patrón MVC correctamente.
 */
@Service
public class ProductoService {

    /**
     * Calcula el importe del producto aplicando
     * la fórmula clásica:
     *
     *      importe = precio * cantidad
     *
     * Este método no guarda ni validad
     * a BD. Solo hace lógica pura.
     *
     * @param precio   Precio unitario del producto
     * @param cantidad Cantidad ingresada
     * @return importe total calculado
     */
    public double calcularImporte(double precio, int cantidad) {
        return precio * cantidad;
    }
}
