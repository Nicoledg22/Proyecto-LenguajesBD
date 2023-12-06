package com.proyecto;

import com.proyecto.domain.Producto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoApplication {
 
	public static void main(String[] args) {
            
		SpringApplication.run(ProyectoApplication.class, args);
            
	}

}

