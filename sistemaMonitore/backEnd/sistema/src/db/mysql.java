package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class mysql {

    // Conexion base de datos

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/baseDatosAuto?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection conectar() {

        try {
            Connection conexion = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD);

            System.out.println("✅ Conexión exitosa a la base de datos.");

            return conexion;

        } catch (SQLException e) {
            System.out.println("❌ Error al conectar: " + e.getMessage());
            return null;
        }
    }



  

  

}
