package main;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// VIEW - diálogo de alteração de aluno (usado em TelaVerAlunos)
class DialogAlterarAluno extends JDialog {
    private boolean alterado = false;

    public DialogAlterarAluno(Window dono, Aluno aluno, AlunoController controller, List<Plano> planos) {
        super(dono, "Alterar Aluno", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(380, 340);
        setResizable(false);
        setLocationRelativeTo(dono);
        getContentPane().setBackground(Estilos.COR_FUNDO);
        setLayout(null);

        JTextField txtNome = new JTextField(aluno.getNome());
        JTextField txtCpf = new JTextField(aluno.getCpf());
        JComboBox<Plano> cbPlano = new JComboBox<>(planos.toArray(new Plano[0]));
        cbPlano.setSelectedItem(aluno.getPlano());
        JTextField txtModalidade = new JTextField(aluno.getModalidade());

        adicionarLinha("Nome:", txtNome, 30);
        adicionarLinha("CPF:", txtCpf, 80);
        adicionarLinha("Plano:", cbPlano, 130);
        adicionarLinha("Modalidade:", txtModalidade, 180);

        JButton btnSalvar = new JButton("Salvar");
        Estilos.aplicarEstiloBotao(btnSalvar);
        btnSalvar.setBounds(25, 245, 155, 40);
        add(btnSalvar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(Estilos.COR_MENU);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBounds(185, 245, 155, 40);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> {
            try {
                controller.alterar(aluno, txtNome.getText(), txtCpf.getText(),
                        (Plano) cbPlano.getSelectedItem(), txtModalidade.getText());
                alterado = true;
                JOptionPane.showMessageDialog(this, "Aluno alterado com sucesso!");
                dispose();
            } catch (ErroAplicacao ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public boolean foiAlterado() { return alterado; }

    private void adicionarLinha(String texto, JComponent campo, int y) {
        JLabel l = new JLabel(texto);
        l.setForeground(Estilos.COR_TEXTO);
        l.setBounds(25, y, 90, 28);
        campo.setBounds(120, y, 220, 28);
        add(l);
        add(campo);
    }
}
