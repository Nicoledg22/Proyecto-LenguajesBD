package com.proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "tab_listado_tienda")
public class Tienda implements Serializable {

    private static final long serialVersionUID = 1L;
    /*asigna automaticamente el numero de id de los clientes*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tienda")
    private Long idTienda;

    @Column(name = "localidad_tienda")
    private String localidad;

    @Column(name = "estado_tienda")
    private String estado;

    public Tienda() {
    }

    public Tienda(String localidad, String estado) {
        this.localidad = localidad;
        this.estado = estado;
    }
}
