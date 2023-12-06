package com.proyecto.service.Impl;

import com.proyecto.dao.EmpleadoDao;
import com.proyecto.dao.TiendaDao;
import com.proyecto.domain.Empleado;
import com.proyecto.domain.Producto;
import com.proyecto.domain.Tienda;
import com.proyecto.service.EmpleadoService;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import oracle.jdbc.OracleTypes;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoDao empleadoDao;


    @Autowired
    public EmpleadoServiceImpl(EmpleadoDao empleadoDao) {
        this.empleadoDao = empleadoDao;
     
    }
    

    @Override
    public List<Empleado> getEmpleados() {
    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
    List<Empleado> lista = new ArrayList<>();

    try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
        System.out.println("Conectado a la base de datos");

        String callStatement = "{call C##finnk.PCK_FINNK_EMPLEADOS_OBTENER.ObtenerEmpleadosSP(?)}";
        System.out.println("Obtencion de empleados mediante paquete");
        try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
            llamadaExecute.registerOutParameter(1, OracleTypes.CURSOR);

            llamadaExecute.execute();
            ResultSet resultSet = (ResultSet) llamadaExecute.getObject(1);

            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(resultSet.getLong("id_empleado"));
                empleado.setNombre(resultSet.getString("nombre_empleado"));
                empleado.setApellidos(resultSet.getString("apellidos_empleado"));
                empleado.setCorreo(resultSet.getString("correo_empleado"));
                empleado.setTelefono(resultSet.getInt("telefono_empleado"));
                empleado.setSalario(resultSet.getInt("salario_empleado"));
                empleado.setPuesto(resultSet.getString("puesto_empleado"));
                empleado.setNacionalidad(resultSet.getString("nacionalidad_empleado"));
                empleado.setEstado(resultSet.getString("estado_empleado"));
                empleado.setInfoAdicional(resultSet.getString("info_adicional"));
                
             
                lista.add(empleado);
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
    public Empleado getEmpleado(Empleado empleado) {
        
        
        List<Empleado> empleados=getEmpleados();
        Empleado empleadoRetorno = new Empleado();
        int idEmpleado=empleado.getIdEmpleado().intValue();
        
       
       
        for (int i=0; i<empleados.size(); i++){
              
            if (empleados.get(i).getIdEmpleado()==idEmpleado){
                System.out.println(idEmpleado+"ID a editar");
                
                System.out.println("I del producto del bucle"+empleados.get(i).getIdEmpleado());
                empleadoRetorno=empleados.get(i);
           
                break;
            }
            
        }
 
       return empleadoRetorno;
    }
   
    
    //eliminar empleado
    @Override
     public void deleteEmpleado(Empleado empleado) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String callStatement = "{call C##finnk.PCK_FINNK_EMPLEADOS_ELIMINAR.EliminarEmpleadoSP(?, ?)}";
        System.out.println("Elimina mediante paquete");
        String resultado = "";

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            CallableStatement callableStatement = conexion.prepareCall(callStatement);
            callableStatement.setLong(1, empleado.getIdEmpleado());
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            callableStatement.execute();

            ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

            if (resultSet.next()) {
                resultado = resultSet.getString("mensaje");
                System.out.println(resultado);
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }   
    }

    @Override
    public void saveEmpleado(Empleado empleado) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
                String callStatement = "{call C##finnk.PCK_FINNK_EMPLEADOS_AGREGAR.AgregarEmpleadoSP(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                
                System.out.println("Creacion de empleado mediante paquete");

                try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {
                    callableStatement.setString(1, empleado.getNombre());
                    callableStatement.setString(2, empleado.getApellidos());
                    callableStatement.setString(3, empleado.getCorreo());
                    callableStatement.setInt(4, empleado.getTelefono());
                    callableStatement.setDouble(5, empleado.getSalario());
                    callableStatement.setString(6, empleado.getPuesto());
                    callableStatement.setString(7, empleado.getNacionalidad());
                    callableStatement.setString(8, empleado.getEstado());
                    callableStatement.registerOutParameter(9, OracleTypes.CURSOR);

                    callableStatement.execute();

                    ResultSet resultSet = (ResultSet) callableStatement.getObject(9);
                    // Ahora puedes procesar el resultado del cursor como desees
                }
            } catch (SQLException e) {
                System.out.println("Error detected");
                e.printStackTrace();
            }
    }
    
    @Override
 
        public void editarEmpleado(Empleado empleado) {
            String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
            String callStatement = "{call C##finnk.PCK_FINNK_EMPLEADOS_EDITAR.EditarEmpleadoSP(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            System.out.println("Edicion de empleado mediante paquete");
            String resultado = "";

            try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
                CallableStatement callableStatement = conexion.prepareCall(callStatement);
                callableStatement.setLong(1, empleado.getIdEmpleado());
                callableStatement.setString(2, empleado.getNombre());
                callableStatement.setString(3, empleado.getApellidos());
                callableStatement.setString(4, empleado.getCorreo());
                callableStatement.setInt(5, empleado.getTelefono());
                callableStatement.setDouble(6, empleado.getSalario());
                callableStatement.setString(7, empleado.getPuesto());
                callableStatement.setString(8, empleado.getNacionalidad());
                callableStatement.setString(9, empleado.getEstado());
                callableStatement.registerOutParameter(10, OracleTypes.CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(10);
                // Manejar el resultado del cursor como sea necesario
            } catch (SQLException e) {
                System.out.println("Error detected");
                e.printStackTrace();
            }
        } 
}

