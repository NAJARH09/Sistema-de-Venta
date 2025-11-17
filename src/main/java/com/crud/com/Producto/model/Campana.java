package com.crud.com.Producto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data//para mazimianr los getters and setterds
public class Campana {
    //paso 1: id autoincrementado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //en palabras sencillas la estrategia es identity
    private int id;
    //No recuerdo como se realiza esto ,osea se que es con generated.value

    @NotBlank
    private String nombre;
    @NotNull(message = "La fecha es obligatoria.")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha es obligatoria.")
    private LocalDateTime fechaFin;

    //private Double totalRecaudado;//no se si ponerlo porque seria la suma de todos los productos por cleinte y cea que al final haria un reporte de ventas


    /*
    Un campaña puede pertencer a muchos clientes
    y muchos clientes pueden pertener a muchas campañas
    Pero ojo:C12->Tiene a carola ,yomira y a damaris
    C13->lilia ,carola y sofia
    es decir que cada campaña tiene unalista de cientes que pueden repetirse en otras campañas



     */

    // ================= RELACIÓN ======================
    // 1. @ManyToMany → relación muchos a muchos
    // 2. Campaña es el DUEÑO → por eso tiene @JoinTable
    //@ManyToMany
    /*@JoinTable(
            name = "campana_cliente",  // ← Tabla intermedia
            joinColumns = @JoinColumn(name = "campana_id"), // FK que apunta a Campaña
            inverseJoinColumns = @JoinColumn(name = "cliente_id") // FK a Cliente
    )
    private List<Cliente> clientes = new ArrayList<>();
    // Esta lista es la lista de clientes que pertenecen a la campaña
    // Campaña controla esta relación
    // Si agregas aquí → se guarda en la tabla intermedia*/


    @OneToMany(mappedBy = "campana", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cliente> clientes = new ArrayList<>();


}



