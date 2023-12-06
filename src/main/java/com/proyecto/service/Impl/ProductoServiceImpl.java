package com.proyecto.service.Impl;

import com.proyecto.dao.ProductoDao;
import com.proyecto.domain.Producto;
import com.proyecto.service.ProductoService;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {
    
    //Esto crea una unica copia en un objeto //
    @Autowired
    public ProductoDao productoDao;
    
    @Override
    public List<Producto> getProductos() {
        
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";

        List<Producto> lista = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            System.out.println("Conectado a la base de datos");

            String callStatement = "{ call C##finnk.PCK_FINNK_PRODUCTOS_OBTENER.ObtenerProductosSP(?) }";
            System.out.println("Paquete preparado");

            // Prepara la llamada al execute
            try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
                
                llamadaExecute.registerOutParameter(1, OracleTypes.CURSOR);

                // Ejecuta el store procedure
                llamadaExecute.execute();

                // Recive resultados y utiliza una libreria llamada resultset
                ResultSet resultSet = (ResultSet) llamadaExecute.getObject(1);

               
                while (resultSet.next()) {
                    Producto producto = new Producto();
                    producto.setIdProducto(resultSet.getLong("id_catalogo"));
                    producto.setImagen(resultSet.getString("imagen_producto"));
                    producto.setNombre(resultSet.getString("nombre_producto"));
                    producto.setPrecio(resultSet.getInt("precio_producto"));
                    producto.setExistencias(resultSet.getInt("existencias_producto"));
                    producto.setEstado(resultSet.getString("estado_producto"));
                    
                    System.out.println(producto.getImagen());
                    lista.add(producto);
                }

                resultSet.close();

               
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

       return lista;

    }

    
    @Override
    public Producto getProducto(Producto producto) {
        
        
        List<Producto> productos=getProductos();
        Producto productoRetorno = new Producto();
        int idProducto=producto.getIdProducto().intValue();
        
       
       
        for (int i=0; i<productos.size(); i++){
              
            if (productos.get(i).getIdProducto()==idProducto){
                System.out.println(idProducto+"ID a editar");
                
                System.out.println("I del producto del bucle"+productos.get(i).getIdProducto());
                productoRetorno=productos.get(i);
           
                break;
            }
            
        }
 
       return productoRetorno;
    }
 
    @Override
    
    public void deleteProducto(Producto producto) {
        Long id = producto.getIdProducto();
         String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String callStatement = "{ call C##finnk.PCK_FINNK_PRODUCTOS_ELIMINAR.EliminarProductoSP(?) }";
        System.out.println("Eliminacion preparada con paquete");
        
        try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            CallableStatement callableStatement = conexion.prepareCall(callStatement);
             int pid= producto.getIdProducto().intValue();
            callableStatement.setInt(1, pid );
           callableStatement.execute();

            System.out.println("Stored procedure executed successfully.");
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }       
}
        
    @Override
    public void saveProducto(Producto producto) {
         
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";

  
        try (Connection connection = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            System.out.println("Connected to the Oracle database");

            String callStatement = "{ call C##finnk.PCK_FINNK_PRODUCTOS_AGREGAR.AgregarProductoSP(?, ?, ?, ?, ?) }";
            System.out.println("Agregar mediante paquete");
            try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {
                
                callableStatement.setString(1, producto.getImagen());
                callableStatement.setString(2, producto.getNombre());
                callableStatement.setDouble(3, producto.getPrecio());
                callableStatement.setInt(4, producto.getExistencias());
                callableStatement.setString(5, producto.getEstado());

                callableStatement.execute();
                System.out.println("Stored procedure de creacion ejecutado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }
        
    }

    @Override
    public void editarProducto(Producto producto) {
        
        
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";

  
        try (Connection connection = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            System.out.println("Connected to the Oracle database");

            String callStatement = "{ call C##finnk.PCK_FINNK_PRODUCTOS_EDITAR.EditarProductoSP(?, ?, ?, ?, ?, ?) }";
            
            System.out.println("Edicion mediante paquete");

            try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {
                callableStatement.setInt(1, producto.getIdProducto().intValue());
                callableStatement.setString(2, producto.getImagen());
                callableStatement.setString(3, producto.getNombre());
                callableStatement.setDouble(4, producto.getPrecio());
                callableStatement.setInt(5, producto.getExistencias());
                callableStatement.setString(6, producto.getEstado());

                callableStatement.execute();
                System.out.println("Stored procedure executed successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }   
    } 
}