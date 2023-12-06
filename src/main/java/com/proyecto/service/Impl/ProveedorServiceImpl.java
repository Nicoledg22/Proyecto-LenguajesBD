package com.proyecto.service.Impl;

import com.proyecto.dao.ProveedorDao;
import com.proyecto.domain.Proveedor;
import com.proyecto.service.ProveedorService;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServiceImpl implements ProveedorService{
  
    //Esto crea una unica copia en un objeto //
  @Autowired
    public ProveedorDao proveedorDao;
    
  
    @Override
        public List<Proveedor> getProveedors() {
            String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
            List<Proveedor> lista = new ArrayList<>();

            try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
                System.out.println("Conectado a la base de datos");

                String callStatement = "{ call C##finnk.obtener_proveedores_sp(?) }";

                try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
                    llamadaExecute.registerOutParameter(1, OracleTypes.CURSOR);

                    llamadaExecute.execute();
                    ResultSet resultSet = (ResultSet) llamadaExecute.getObject(1);

                    while (resultSet.next()) {
                        Proveedor proveedor = new Proveedor();
                        proveedor.setIdProveedor(resultSet.getLong("id_proveedor"));
                        proveedor.setNombre(resultSet.getString("nombre_proveedor"));
                        proveedor.setApellidos(resultSet.getString("apellidos_proveedor"));
                        proveedor.setCorreo(resultSet.getString("correo_proveedor"));
                        proveedor.setTelefono(resultSet.getInt("telefono_proveedor"));
                        proveedor.setMarca(resultSet.getString("marca_proveedor"));
                        proveedor.setNacionalidad(resultSet.getString("nacionalidad_proveedor"));
                        proveedor.setEstado(resultSet.getString("estado_proveedor"));

                        lista.add(proveedor);
                    }
                    System.out.println("Lectura de proveedores realizada correctamente");

                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Error");
                e.printStackTrace();
            }

            return lista;
        }

     @Override
    public Proveedor getProveedor(Proveedor proveedor) {
        
        
        List<Proveedor> provedores=getProveedors();
        Proveedor proveedorRetorno = new Proveedor();
        int idProveedor=proveedor.getIdProveedor().intValue();
        
       
       
        for (int i=0; i<provedores.size(); i++){
              
            if (provedores.get(i).getIdProveedor()==idProveedor){
                System.out.println(idProveedor+"ID a editar");
                
                System.out.println("I del producto del bucle"+provedores.get(i).getIdProveedor());
                proveedorRetorno=provedores.get(i);
           
                break;
            }
            
        }
       return proveedorRetorno;
    }

    @Override
  
    public void deleteProveedor(Proveedor proveedor) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String callStatement = "{ call C##finnk.EliminarProveedorSP(?, ?) }";
        String resultado = "";

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            CallableStatement callableStatement = conexion.prepareCall(callStatement);
            callableStatement.setLong(1, proveedor.getIdProveedor());
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            callableStatement.execute();

            ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

            if (resultSet.next()) {
                resultado = resultSet.getString("mensaje");
                System.out.println(resultado+" EXITO");
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }
    }


    @Override
    public void saveProveedor(Proveedor proveedor) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, "C##finnk", "kaws2023")) {
            String callStatement = "{ call AgregarProveedorSP(?, ?, ?, ?, ?, ?, ?, ?) }";

            try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {
                callableStatement.setString(1, proveedor.getNombre());
                callableStatement.setString(2, proveedor.getApellidos());
                callableStatement.setString(3, proveedor.getCorreo());
                callableStatement.setInt(4, proveedor.getTelefono());
                callableStatement.setString(5, proveedor.getMarca());
                callableStatement.setString(6, proveedor.getNacionalidad());
                callableStatement.setString(7, proveedor.getEstado());
                callableStatement.registerOutParameter(8, OracleTypes.CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(8);

                if (resultSet.next()) {
                    System.out.println("Proveedor agregado correctamente. ID: " + resultSet.getInt("id_proveedor"));
                    
                }
                
                resultSet.close();

                
            }
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }
    }
 
    
    @Override
    public void editarProveedor(Proveedor proveedor) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            System.out.println("Connected to the Oracle database");

            String callStatement = "{ call C##finnk.EditarProveedorSP(?, ?, ?, ?, ?, ?, ?, ?, ?) }";

            try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {
                callableStatement.setInt(1, proveedor.getIdProveedor().intValue());
                callableStatement.setString(2, proveedor.getNombre());
                callableStatement.setString(3, proveedor.getApellidos());
                callableStatement.setString(4, proveedor.getCorreo());
                callableStatement.setInt(5, proveedor.getTelefono());
                callableStatement.setString(6, proveedor.getMarca());
                callableStatement.setString(7, proveedor.getNacionalidad());
                callableStatement.setString(8, proveedor.getEstado());

                // Registro del parámetro de salida para el cursor
                callableStatement.registerOutParameter(9, OracleTypes.CURSOR);

                callableStatement.execute();
                System.out.println("Edicion de proveedor completada.");

                // Obtener el resultado del cursor
                ResultSet resultSet = (ResultSet) callableStatement.getObject(9);
                while (resultSet.next()) {
                    // Procesar los resultados si es necesario
                }
            }
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }
    } 
}