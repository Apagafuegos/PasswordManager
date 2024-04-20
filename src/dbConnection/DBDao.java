package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBDao {
    /**
     * Generates a connection to the said database
     *
     * @return Connection to the "passwords DB"
     */
    protected Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/passwords";
        String driver = "com.mysql.cj.jdbc.Driver";
        String user = "user";
        String password = "user1234";

        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.err.println("No se ha podido conectar a la BBDD" + e.getMessage());
            e.getStackTrace();
            throw new RuntimeException("No se pudo conectar a la BBDD");
        }


    }

    /**
     * Method that tests if the connection generated for the DB is correct.
     */
    public void testConexion() {
        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("No hay conexion");
        } else {
            System.out.println("¡Hay conexión!");
        }
    }

}
