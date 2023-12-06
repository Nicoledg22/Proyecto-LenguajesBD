package com.proyecto.service.Impl;

import com.proyecto.dao.TiendaDao;
import com.proyecto.domain.Tienda;
import com.proyecto.service.TiendaService;
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
public class TiendaServiceImpl implements TiendaService{
  
    //Esto crea una unica copia en un objeto //
    @Autowired
    public TiendaDao tiendaDao;
    
   @Override
    public List<Tienda> getTiendas() {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        List<Tienda> lista = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            System.out.println("Conectado a la base de datos");

            String callStatement = "{ call C##finnk.obtener_tiendas_sp(?) }";

            try (CallableStatement callableStatement = conexion.prepareCall(callStatement)) {
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                while (resultSet.next()) {
                    Tienda tienda = new Tienda();
                    tienda.setIdTienda(resultSet.getLong("id_tienda"));
                    tienda.setLocalidad(resultSet.getString("localidad_tienda"));
                    tienda.setEstado(resultSet.getString("estado_tienda"));

                    lista.add(tienda);
                }
                System.out.println("Lectura de tiendas realizada correctamente desde procedimiento almacenado");

                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        return lista;
    }


    @Override
    
    public Tienda gettienda(Tienda tienda) {
        
        
        List<Tienda> tiendas=getTiendas();
        Tienda tiendaRetorno = new Tienda();
        int idTienda=tienda.getIdTienda().intValue();
        
       
        System.out.println("Ventana de lectura de tienda ejecutada con exito");
        for (int i=0; i<tiendas.size(); i++){
              
            if (tiendas.get(i).getIdTienda()==idTienda){
                System.out.println(idTienda+"ID a editar");
                
                System.out.println("I del producto del bucle"+tiendas.get(i).getIdTienda());
                tiendaRetorno=tiendas.get(i);
                
                break;
            }
            
        }

       return tiendaRetorno;
    }

    @Override
    public void deletetienda(Tienda tienda) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String callStatement = "{ call C##finnk.EliminarTiendaSP(?, ?) }";
        String resultado = "";

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            CallableStatement callableStatement = conexion.prepareCall(callStatement);
            callableStatement.setLong(1, tienda.getIdTienda());
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            callableStatement.execute();

            ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

            if (resultSet.next()) {
                resultado = resultSet.getString("mensaje");
                System.out.println(resultado + " EXITO");
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }
    }


    @Override
    public void savetienda(Tienda tienda) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, "C##finnk", "kaws2023")) {
            String callStatement = "{ call AgregarTiendaSP(?, ?, ?) }";

            try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {
                callableStatement.setString(1, tienda.getLocalidad());
                callableStatement.setString(2, tienda.getEstado());
                callableStatement.registerOutParameter(3, OracleTypes.CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(3);
                System.out.println("tienda agregada mediante sp ");
                if (resultSet.next()) {
                    System.out.println("Tienda agregada correctamente. ID: " + resultSet.getInt("id_tienda"));
                }

                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }
    }

    
    @Override
    public void editarTienda(Tienda tienda) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            System.out.println("Connected to the Oracle database");

            String callStatement = "{ call C##finnk.EditarTiendaSP(?, ?, ?, ?) }";

            try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {
                callableStatement.setInt(1, tienda.getIdTienda().intValue());
                callableStatement.setString(2, tienda.getLocalidad());
                callableStatement.setString(3, tienda.getEstado());
                callableStatement.registerOutParameter(4, OracleTypes.CURSOR);

                callableStatement.execute();
                System.out.println("Edición de tienda completada.");

                // Obtener el resultado del cursor
                ResultSet resultSet = (ResultSet) callableStatement.getObject(4);
                while (resultSet.next()) {
                    int idTienda = resultSet.getInt("id_tienda");
                    String localidad = resultSet.getString("localidad_tienda");
                    String estado = resultSet.getString("estado_tienda");

                    System.out.println("Resultado del cursor - ID Tienda: " + idTienda + ", Localidad: " + localidad + ", Estado: " + estado);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }
    }
}
