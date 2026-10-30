package main;

import javax.swing.*;
import java.awt.*;

// VIEW - cadastro de aluno pelo funcionário (via controller)
class TelaCadastrarAluno extends JPanel {
    private final AlunoController alunoController = new AlunoController();
    private final PlanoController planoController = new PlanoController();

    public TelaCadastrarAluno() {
        setBackground(Estilos.COR_FUNDO);
        setLayout(null);

        JLabel titulo = new JLabel("Cadastrar Novo Aluno (Funcionário)");
        titulo.setFont(Estilos.FONTE_TITULO);
        titulo.setForeground(Estilos.COR_TEXTO);
        titulo.setBounds(40, 30, 600, 35);
        add(titulo);

        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtUser = new JTextField();
        JComboBox<Plano> cbPlano = new JComboBox<>();
        try {
            for (Plano p : planoController.listar()) cbPlano.addItem(p);
        } catch (ErroAplicacao ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        adicionarLinha("Nome:", txtNome, 100);
        adicionarLinha("CPF:", txtCpf, 150);
        adicionarLinha("Usuário:", txtUser, 200);
        adicionarLinha("Plano:", cbPlano, 250);

        JButton btnSalvar = new JButton("Salvar Aluno");
        Estilos.aplicarEstiloBotao(btnSalvar);
        btnSalvar.setBounds(140, 315, 300, 40);
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                alunoController.cadastrar(txtNome.getText(), txtCpf.getText(), txtUser.getText(), "123",
                        (Plano) cbPlano.getSelectedItem());
                JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso! Senha padrão: 123");
                txtNome.setText(""); txtCpf.setText(""); txtUser.setText("");
            } catch (ErroAplicacao ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void adicionarLinha(String texto, JComponent campo, int y) {
        JLabel l = new JLabel(texto);
        l.setForeground(Estilos.COR_TEXTO);
        l.setBounds(40, y, 90, 28);
        campo.setBounds(140, y, 300, 28);
        add(l);
        add(campo);
    }
}
