package main;

import java.sql.SQLException;
import java.util.List;

// CONTROLLER
public class PlanoController {
    private final PlanoDAO dao = new PlanoDAO();

    public List<Plano> listar() {
        try {
            return dao.listar();
        } catch (SQLException e) {
            throw new ErroAplicacao("Erro ao consultar os planos: " + e.getMessage(), e);
        }
    }
}
