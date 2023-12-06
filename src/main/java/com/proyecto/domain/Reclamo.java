package com.proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "tab_listado_reclamos")

public class Reclamo implements Serializable {

    private static final long serialVersionUID = 1L;
    /*asigna automaticamente el numero de id de los clientes*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reclamo")
    private Long idReclamo;

    @Column(name = "comentario_reclamo")
    private String consulta;

    @Column(name = "nombre_reclamo")
    private String nombre;

    public Reclamo() {
    }

    public Reclamo(String consulta, String nombre) {
        this.consulta = consulta;
        this.nombre = nombre;
    }
}
