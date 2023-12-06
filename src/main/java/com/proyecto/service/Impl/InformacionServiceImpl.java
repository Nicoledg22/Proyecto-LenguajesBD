package com.proyecto.service.Impl;

import com.proyecto.dao.InformacionDao;
import com.proyecto.domain.Informacion;
import com.proyecto.service.InformacionService;
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

@Service /*Se utiliza para que sea visible en el controlador */

public class InformacionServiceImpl implements InformacionService{
  
    //Esto crea una unica copia en un objeto //
    @Autowired
    public InformacionDao informaciondao;
    
   @Override
public List<Informacion> getInformacion() {
    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
    List<Informacion> lista = new ArrayList<>();

    try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
        System.out.println("Conectado a la base de datos");

        String callStatement = "{ call C##finnk.obtener_informacion_clientes_sp(?) }";

        try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
            llamadaExecute.registerOutParameter(1, OracleTypes.CURSOR);

            llamadaExecute.execute();
            ResultSet resultSet = (ResultSet) llamadaExecute.getObject(1);

            while (resultSet.next()) {
                Informacion informacion = new Informacion();
                informacion.setIdPersona(resultSet.getLong("id_cliente"));
                informacion.setCorreo(resultSet.getString("correo_cliente"));
                
                lista.add(informacion);
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
    public Informacion getInformacion(Informacion informacion) {
       return informaciondao.findById(informacion.getIdPersona()).orElse(null);
    }

// ...

    @Override
    public void deleteInformacion(Informacion informacion) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String callStatement = "{ call C##finnk.eliminar_cliente_sp(?, ?) }";
        String resultado = "";

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            CallableStatement callableStatement = conexion.prepareCall(callStatement);
            callableStatement.setLong(1, informacion.getIdPersona());
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
    public void saveInformacion(Informacion informacion) {
        informaciondao.save(informacion);
    }
}