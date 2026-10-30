package main;

import java.sql.*;

// DAO - consulta usada no login
public class UsuarioDAO {
    public Usuario autenticar(String login, String senha, String tipo) throws SQLException {
        String sql = "SELECT id, nome FROM usuario WHERE login = ? AND senha = ? AND tipo = ?";
        try (Connection c = ConnectionFactory.getConexao();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, senha);
            ps.setString(3, tipo);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                switch (tipo) {
                    case "Funcionario": return new Funcionario(id, nome, login, senha);
                    case "Instrutor":   return new Instrutor(id, nome, login, senha);
                    case "Aluno":       return new AlunoDAO().buscarPorId(id);
                    default:            return null;
                }
            }
        }
    }
}
