package com.crud.com.Producto.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data //usamos esto porque crea los getters and setters
public class Cliente {
    //creamjo slos datos de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
//Una entidad es una clase Java que representa una tabla en la base
// UNA campaña tiene MUCHOS clientes


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>();
    //para campanas
    @ManyToOne
    @JoinColumn(name = "campana_id")
    private Campana campana;



    // =============== RELACIÓN INVERSA =================
    // Cliente NO controla → usa mappedBy
    // "clientes" es el nombre de la lista en Campana
    // Esta lista solo refleja
    // Cliente NO crea tabla intermedia
}
/*
Los datos que necesitamos el cliente :
nombre ,telefono ,apellido.
mappedBy indica que Producto tiene la FK.

cascade = ALL permite guardar cliente + productos juntos.

orphanRemoval = true elimina productos quitados de la lista.
 */