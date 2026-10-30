package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private static final String SELECT_BASE =
        "SELECT u.id, u.nome, u.login, u.senha, u.cpf, u.plano_id, u.modalidade, " +
        "u.data_nascimento, u.telefone, u.email, p.nome AS plano_nome, p.valor AS plano_valor " +
        "FROM usuario u LEFT JOIN plano p ON p.id = u.plano_id WHERE u.tipo = 'Aluno'";

    //INSERIR
    public void inserir(Aluno a) throws SQLException {
        String sql = "INSERT INTO usuario (nome, login, senha, tipo, cpf, plano_id, modalidade, " +
                     "data_nascimento, telefone, email) VALUES (?, ?, ?, 'Aluno', ?, ?, ?, ?, ?, ?)";
        try (Connection c = ConnectionFactory.getConexao();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getLogin());
            ps.setString(3, a.getSenha());
            ps.setString(4, a.getCpf());
            if (a.getPlano() != null) ps.setInt(5, a.getPlano().getId()); else ps.setNull(5, Types.INTEGER);
            ps.setString(6, a.getModalidade());
            ps.setString(7, a.getDataNascimento());
            ps.setString(8, a.getTelefone());
            ps.setString(9, a.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) a.setId(rs.getInt(1));
            }
        }
    }

    //ALTERAR
    public int alterar(Aluno a) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, cpf = ?, plano_id = ?, modalidade = ?, " +
                     "data_nascimento = ?, telefone = ?, email = ? WHERE id = ? AND tipo = 'Aluno'";
        try (Connection c = ConnectionFactory.getConexao();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getCpf());
            if (a.getPlano() != null) ps.setInt(3, a.getPlano().getId()); else ps.setNull(3, Types.INTEGER);
            ps.setString(4, a.getModalidade());
            ps.setString(5, a.getDataNascimento());
            ps.setString(6, a.getTelefone());
            ps.setString(7, a.getEmail());
            ps.setInt(8, a.getId());
            return ps.executeUpdate();
        }
    }

    //EXCLUIR
    public int excluir(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ? AND tipo = 'Aluno'";
        try (Connection c = ConnectionFactory.getConexao();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    //CONSULTAR
    public List<Aluno> listarTodos() throws SQLException {
        List<Aluno> lista = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConexao();
             PreparedStatement ps = c.prepareStatement(SELECT_BASE + " ORDER BY u.nome");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
            for (Aluno a : lista) carregarTreinos(c, a);
        }
        return lista;
    }

    public Aluno buscarPorId(int id) throws SQLException {
        try (Connection c = ConnectionFactory.getConexao();
             PreparedStatement ps = c.prepareStatement(SELECT_BASE + " AND u.id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Aluno a = mapear(rs);
                carregarTreinos(c, a);
                return a;
            }
        }
    }

    public int contar() throws SQLException {
        try (Connection c = ConnectionFactory.getConexao();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM usuario WHERE tipo = 'Aluno'");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public boolean cpfExiste(String cpf, int idIgnorar) throws SQLException {
        String sql = "SELECT 1 FROM usuario WHERE cpf = ? AND id <> ?";
        try (Connection c = ConnectionFactory.getConexao();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ps.setInt(2, idIgnorar);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public boolean loginExiste(String login) throws SQLException {
        String sql = "SELECT 1 FROM usuario WHERE login = ?";
        try (Connection c = ConnectionFactory.getConexao();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    //auxiliares
    private Aluno mapear(ResultSet rs) throws SQLException {
        Plano plano = null;
        int planoId = rs.getInt("plano_id");
        if (!rs.wasNull()) {
            plano = new Plano(planoId, rs.getString("plano_nome"), rs.getDouble("plano_valor"));
        }
        return new Aluno(rs.getInt("id"), rs.getString("nome"), rs.getString("login"), rs.getString("senha"),
                rs.getString("cpf"), plano, rs.getString("modalidade"),
                vazioSeNulo(rs.getString("data_nascimento")), vazioSeNulo(rs.getString("telefone")),
                vazioSeNulo(rs.getString("email")));
    }

    private String vazioSeNulo(String s) { return s == null ? "" : s; }

    private void carregarTreinos(Connection c, Aluno aluno) throws SQLException {
        String sqlTreino = "SELECT id, nome FROM treino WHERE aluno_id = ? ORDER BY id";
        String sqlEx = "SELECT nome, grupo_muscular, series, repeticoes FROM exercicio WHERE treino_id = ? ORDER BY id";
        
        try (PreparedStatement psT = c.prepareStatement(sqlTreino)) {
            psT.setInt(1, aluno.getId());
            try (ResultSet rsT = psT.executeQuery()) {
                while (rsT.next()) {
                    Treino t = new Treino(rsT.getString("nome"));
                    try (PreparedStatement psE = c.prepareStatement(sqlEx)) {
                        psE.setInt(1, rsT.getInt("id"));
                        try (ResultSet rsE = psE.executeQuery()) {
                            while (rsE.next()) {
                                t.adicionarExercicio(new Exercicio(rsE.getString("nome"),
                                        rsE.getString("grupo_muscular"), rsE.getInt("series"), rsE.getInt("repeticoes")));
                            }
                        }
                    }
                    aluno.adicionarTreino(t);
                }
            }
        }
    }
}
