package lk.ijse.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection  {
    private static DbConnection dbConnection;
    private Connection connection;

    private DbConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/SunFlower",
                "root",
                "Ijse@123"
        );
    }
    public static DbConnection getDbConnection() throws SQLException, ClassNotFoundException {
        return dbConnection == null ? dbConnection= new DbConnection() : dbConnection;
    }

    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if(dbConnection==null){
            dbConnection = new DbConnection();
        }
        return dbConnection;

    }

    public Connection getConnection() {
        return connection;
    }
}
