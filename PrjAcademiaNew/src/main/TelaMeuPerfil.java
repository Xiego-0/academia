package main;

import javax.swing.*;
import java.awt.*;

// VIEW - perfil do aluno; nome, nascimento, telefone e e-mail podem ser ALTERADOS
class TelaMeuPerfil extends JPanel {
    private final AlunoController controller = new AlunoController();
    private final Aluno aluno;
    private final JTextField txtNome, txtNascimento, txtTelefone, txtEmail;

    public TelaMeuPerfil(Aluno aluno) {
        this.aluno = aluno;
        setBackground(Estilos.COR_FUNDO);
        setLayout(null);

        JLabel titulo = new JLabel("Meu Perfil");
        titulo.setFont(Estilos.FONTE_TITULO);
        titulo.setForeground(Estilos.COR_DESTAQUE);
        titulo.setBounds(30, 30, 400, 35);
        add(titulo);

        txtNome = campoEditavel("Nome:", aluno.getNome(), 95);
        valorFixo("CPF:", aluno.getCpf(), 135);
        txtNascimento = campoEditavel("Nascimento:", aluno.getDataNascimento(), 175);
        txtTelefone = campoEditavel("Telefone:", aluno.getTelefone(), 215);
        txtEmail = campoEditavel("E-mail:", aluno.getEmail(), 255);
        valorFixo("Plano Atual:", aluno.getPlano() != null ? aluno.getPlano().getNome() : "Sem Plano", 295);
        valorFixo("Modalidade:", aluno.getModalidade(), 335);

        JLabel dica = new JLabel("Nascimento no formato dd/mm/aaaa. CPF, plano e modalidade são alterados pela recepção.");
        dica.setForeground(Color.GRAY);
        dica.setBounds(30, 385, 600, 22);
        add(dica);

        JButton btnSalvar = new JButton("Salvar Alterações");
        Estilos.aplicarEstiloBotao(btnSalvar);
        btnSalvar.setBounds(160, 420, 220, 40);
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                controller.alterarPerfil(aluno, txtNome.getText(), txtNascimento.getText(),
                        txtTelefone.getText(), txtEmail.getText());
                JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
            } catch (ErroAplicacao ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel rotulo(String texto, int y) {
        JLabel l = new JLabel(texto);
        l.setForeground(Color.GRAY);
        l.setFont(Estilos.FONTE_NEGRITO);
        l.setBounds(30, y, 120, 26);
        add(l);
        return l;
    }

    private JTextField campoEditavel(String rotulo, String valor, int y) {
        rotulo(rotulo, y);
        JTextField t = new JTextField(valor);
        t.setBounds(160, y, 320, 26);
        add(t);
        return t;
    }

    private void valorFixo(String rotulo, String valor, int y) {
        rotulo(rotulo, y);
        JLabel v = new JLabel(valor);
        v.setForeground(Estilos.COR_TEXTO);
        v.setFont(Estilos.FONTE_PADRAO);
        v.setBounds(160, y, 320, 26);
        add(v);
    }
}
