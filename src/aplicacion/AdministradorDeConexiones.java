package aplicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class AdministradorDeConexiones {
    
    private AdministradorDeConexiones() {};
    public static Connection getConnection() throws ReflectiveOperationException, SQLException{
        String dbDriver = "com.mysql.jdbc.Driver";        
        String dbConnString = "jdbc:mysql://localhost:3306/bdrestaurantes";
        String dbUser = "root";        
        String dbPassword = "";        
        Class.forName(dbDriver).newInstance();
        return DriverManager.getConnection(dbConnString, dbUser, dbPassword);
    }    
}
