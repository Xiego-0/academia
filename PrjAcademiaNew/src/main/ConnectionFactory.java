package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// DAO - conexão com o banco (ajuste URL/USUARIO/SENHA para o seu MySQL)
public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/academia_plus"+ "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "1234";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
