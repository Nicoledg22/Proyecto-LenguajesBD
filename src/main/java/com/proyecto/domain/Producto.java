package com.proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "tab_catalogo_productos")

public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    /*asigna automaticamente el numero de id de los clientes*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idProducto;

    private String nombre;


    private String imagen;

    private int precio;


    private int existencias;

  
    private String estado;

    public Producto() {
    }

    public Producto(String nombre, String imagen, int precio, int existencias, String estado) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.precio = precio;
        this.existencias = existencias;
        this.estado = estado;
    }
}
