package main;

import javax.swing.*;
import java.sql.Connection;

public class AcademiaPlus {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Testa a conexão antes de abrir o sistema
        try (Connection c = ConnectionFactory.getConexao()) {
            // conexão ok
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Não foi possível conectar ao banco de dados.\nVerifique o MySQL, o script_banco.sql e a classe ConexaoBanco.\n\n" + e.getMessage(),
                "Erro de conexão", JOptionPane.ERROR_MESSAGE);
            return;
        }
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
