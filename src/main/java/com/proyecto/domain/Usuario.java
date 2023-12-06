package com.proyecto.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name="usuario")

public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @NotEmpty
    @Column(name = "username")
    private String username;
    
    @NotEmpty
    @Column(name = "password")
    private String password;
    
    @OneToMany
    @JoinColumn(name="id_usuario")
    private List<Rol> roles;
}