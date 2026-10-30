package main;

import java.sql.SQLException;

// CONTROLLER
public class LoginController {
    private final UsuarioDAO dao = new UsuarioDAO();

    /** Retorna o usuário logado ou null se as credenciais forem inválidas. */
    public Usuario autenticar(String login, String senha, String tipo) {
        try {
            return dao.autenticar(login, senha, tipo);
        } catch (SQLException e) {
            throw new ErroAplicacao("Erro ao acessar o banco: " + e.getMessage(), e);
        }
    }
}
