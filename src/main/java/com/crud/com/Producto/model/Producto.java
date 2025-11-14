package com.crud.com.Producto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres.")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria.")
    @Size(min = 10, max = 255, message = "La descripción debe tener entre 10 y 255 caracteres.")
    private String des;

    @NotNull(message = "La cantidad es obligatoria.")
    @Min(value = 1, message = "La cantidad mínima es 1 unidad.")
    @Max(value = 1000, message = "La cantidad máxima permitida es 1000 unidades.")
    private Integer cantidad;

    @NotNull(message = "El precio es obligatorio.")
    @DecimalMin(value = "0.10", message = "El precio mínimo es S/ 0.10.")
    @DecimalMax(value = "9999.99", message = "El precio máximo es S/ 9999.99.")
    private Double precio;

    private double importe;

    //1 cliente puede tener muchos productos
    //muchos productos pueden tener solo 1 cliente
    // Muchos productos pertenecen a 1 cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id") // Foreign Key
    private Cliente cliente;
}
