package com.proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "tab_listado_empleados")
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long idEmpleado; 
    
    @Column(name = "nombre_empleado")
    private String nombre;
    
    @Column(name = "apellidos_empleado")
    private String apellidos;
    
    @Column(name = "correo_empleado")
    private String correo;
    
    @Column(name = "telefono_empleado")
    private int telefono;
    
    @Column(name = "salario_empleado")
    private int salario;
    
    @Column(name = "puesto_empleado")
    private String puesto;
    
    @Column(name = "nacionalidad_empleado")
    private String nacionalidad;
    
    @Column(name = "estado_empleado")
    private String estado;
    
    @Transient
    private String infoAdicional;  // Agregar este campo

    public Empleado() {
    }

    public Empleado(Long idEmpleado, String nombre, String apellidos, String correo, int telefono, int salario, String puesto, String nacionalidad, String estado) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.salario = salario;
        this.puesto = puesto;
        this.nacionalidad = nacionalidad;
        this.estado = estado;
    }
}

