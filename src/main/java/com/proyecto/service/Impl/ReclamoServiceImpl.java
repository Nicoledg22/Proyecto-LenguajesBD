package com.proyecto.service.Impl;

import com.proyecto.dao.ReclamoDao;
import com.proyecto.domain.Reclamo;
import com.proyecto.service.ReclamoService;
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
public class ReclamoServiceImpl implements ReclamoService{
  
    //Esto crea una unica copia en un objeto //
    @Autowired
    public ReclamoDao reclamodao;
    
   @Override
    public List<Reclamo> getReclamos() {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        List<Reclamo> listaReclamos = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            System.out.println("Connected to the Oracle database");

            String callStatement = "{call C##finnk.PCK_FINNK_RECLAMOS_OBTENER.ObtenerReclamosSP(?)}";
            System.out.println("Obtener reclamos mediante el paquete");

            try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                while (resultSet.next()) {
                    Reclamo reclamo = new Reclamo();
                    reclamo.setIdReclamo(resultSet.getLong("id_reclamos"));
                    reclamo.setNombre(resultSet.getString("nombre_reclamo"));
                    reclamo.setConsulta(resultSet.getString("comentario_reclamo"));

                    listaReclamos.add(reclamo);
                }

                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }

        return listaReclamos;
    }


    @Override
    public Reclamo getReclamo(Reclamo reclamo) {
       return reclamodao.findById(reclamo.getIdReclamo()).orElse(null);
    }

    @Override
    public void deleteReclamo(Reclamo reclamo) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String callStatement = "{call C##finnk.PCK_FINNK_RECLAMOS_ELIMINAR.EliminarReclamoSP(?, ?)}";
        System.out.println("Reclamo eliminado mediante paquete");
        String resultado = "";

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, "nicole", "987654")) {
            CallableStatement callableStatement = conexion.prepareCall(callStatement);
            callableStatement.setLong(1, reclamo.getIdReclamo());
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            callableStatement.execute();

            ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
            System.out.println("Reclamo eliminado mediante procedmiento, funcion y cursor.");
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
    public void saveReclamo(Reclamo reclamo) {
        reclamodao.save(reclamo);
    }
}