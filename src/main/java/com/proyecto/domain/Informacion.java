package com.proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "tab_listado_clientes")

public class Informacion implements Serializable {

    private static final long serialVersionUID = 1L;
    /*asigna automaticamente el numero de id de los clientes*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "correo_cliente")
    private String correo;

    public Informacion() {
    }

    public Informacion(String correo) {

        this.correo = correo;
    }
}
