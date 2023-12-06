package com.proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="tab_listado_proveedores")
public class Proveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    /*asigna automaticamente el numero de id de los clientes*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long idProveedor;

    @Column(name = "nombre_proveedor")
    private String nombre;

    @Column(name = "apellidos_proveedor")
    private String apellidos;

    @Column(name = "correo_proveedor")
    private String correo;

    @Column(name = "telefono_proveedor")
    private int telefono;

    @Column(name = "marca_proveedor")
    private String marca;

    @Column(name = "nacionalidad_proveedor")
    private String nacionalidad;

    @Column(name = "estado_proveedor")
    private String estado;

    public Proveedor() {
    }

    public Proveedor(String nombre, String apellidos, String correo, int telefono, String marca, String nacionalidad, String estado) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.marca = marca;
        this.nacionalidad = nacionalidad;
        this.estado = estado;
    }
}