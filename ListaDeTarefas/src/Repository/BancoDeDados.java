package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {

    private final String URL = "jdbc:mysql://localhost:3306/ToDoList_banco?" +
            "createDatabaseIfNotExist=true";
    private final String USER = "root";
    private final String PASSWORD = "";

    public Connection getConexao() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
