package com.proyecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }
     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        
        http
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                        "/",
                        "/index/**",           
                        "/finnk/Productos/**",                    
                        "/finnk/New arrivals/**",
                        "/finnk/blogs/**",
                        "/finnk/informacion/**",
                        "/blogs/**",                      
                        "/informacion/guardar/**", 
                        "/Reclamos/guardar/**",
                        "/errores/**",
                        "/carrito/**",
                        "/css/**",
                        "/img/**",                   
                        "/cdnjs/**",
                     
                        "/empleado/nuevo/**",
                        "/empleado/eliminar/**",
                        "/empleado/modificarB/**",
                        "/empleado/guardar/**",
                        "/empleado/editar/**",
                
                        "/empleado/InventarioB",
                        "/empleado/modificaB",
                        
                        "/informacion/listado", 
                        "/informacion/eliminar/**",
                        
                        "/productos/nuevo/**",
                        "/productos/eliminar/**",
                        "/productos/modificar/**",
                        "/productos/guardar/**",
                        "/productos/editar/**",
                       
                        
                        "/Reclamos/listado",
                        "/Reclamos/eliminar/**",
                          
                        "/proveedor/nuevo/**",
                        "/proveedor/eliminar/**",
                        "/proveedor/modifica/**",
                        "/proveedor/guardar/**", 
                        "/proveedor/Inventario",
                        "/proveedor/modifica",
                        "/proveedor/editar/**",
                        
                        "/tienda/nuevo/**",
                        "/tienda/eliminar/**",
                        "/tienda/modificar/**",
                        "/tienda/guardar/**",                                         
                        "/tienda/listado",
                        "/tienda/modifica",
                        "/tienda/editar/**",
                        
                        "/reportes/**",     
                                 
                  
                        "/productos/Inventario",
                      
                        "/productos/modifica",
                 
                     
                        "/webjars/**").permitAll()            
                
                )
                .formLogin((form) -> form
                .loginPage("/login")
                .permitAll())
                .logout((logout) -> logout.permitAll())
                .exceptionHandling().accessDeniedPage("/errores/403");
        return http.build();
    }
    
}
