package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO - consulta de planos
public class PlanoDAO {
    public List<Plano> listar() throws SQLException {
        List<Plano> lista = new ArrayList<>();
        String sql = "SELECT id, nome, valor FROM plano ORDER BY id";
        try (Connection c = ConnectionFactory.getConexao();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Plano(rs.getInt("id"), rs.getString("nome"), rs.getDouble("valor")));
            }
        }
        return lista;
    }
}
