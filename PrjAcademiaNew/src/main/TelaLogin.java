package main;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaLogin extends JFrame {
    private final LoginController loginController = new LoginController();

    public TelaLogin() {
        setTitle("Academia+ | Login");
        setSize(400, 550);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.COR_FUNDO);
        setLayout(null);

        // Card central (x=25, y=25, 335x455)
        JPanel painelCard = new JPanel(null);
        painelCard.setBackground(Estilos.COR_CARD);
        painelCard.setBounds(25, 25, 335, 455);

        JLabel lblTitulo = new JLabel("ACADEMIA+");
        lblTitulo.setFont(Estilos.FONTE_TITULO);
        lblTitulo.setForeground(Estilos.COR_DESTAQUE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 25, 335, 40);
        painelCard.add(lblTitulo);

        JLabel lblTipo = new JLabel("Perfil:");
        lblTipo.setForeground(Estilos.COR_TEXTO);
        lblTipo.setBounds(30, 95, 70, 28);
        painelCard.add(lblTipo);
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Funcionario", "Aluno", "Instrutor"});
        cbTipo.setBounds(110, 95, 195, 28);
        painelCard.add(cbTipo);

        JLabel lblUser = new JLabel("Usuário:");
        lblUser.setForeground(Estilos.COR_TEXTO);
        lblUser.setBounds(30, 145, 70, 28);
        painelCard.add(lblUser);
        JTextField txtUser = new JTextField();
        txtUser.setBounds(110, 145, 195, 28);
        painelCard.add(txtUser);

        JLabel lblPass = new JLabel("Senha:");
        lblPass.setForeground(Estilos.COR_TEXTO);
        lblPass.setBounds(30, 195, 70, 28);
        painelCard.add(lblPass);
        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(110, 195, 195, 28);
        painelCard.add(txtPass);

        JButton btnEntrar = new JButton("Entrar");
        Estilos.aplicarEstiloBotao(btnEntrar);
        btnEntrar.setBounds(30, 260, 275, 40);
        painelCard.add(btnEntrar);

        JButton btnCadastrar = new JButton("Novo Aluno? Cadastre-se");
        btnCadastrar.setBackground(Estilos.COR_MENU);
        btnCadastrar.setForeground(Estilos.COR_DESTAQUE);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBounds(30, 315, 275, 40);
        painelCard.add(btnCadastrar);

        JButton btnSair = new JButton("Sair");
        btnSair.setBackground(new Color(200, 50, 50));
        btnSair.setForeground(Color.WHITE);
        btnSair.setBounds(30, 370, 275, 40);
        painelCard.add(btnSair);

        add(painelCard);

        btnSair.addActionListener(e -> System.exit(0));
        btnCadastrar.addActionListener(e -> new DialogCadastro(this).setVisible(true));

        btnEntrar.addActionListener(e -> {
            try {
                Usuario u = loginController.autenticar(txtUser.getText(), new String(txtPass.getPassword()), cbTipo.getSelectedItem().toString());
                if (u != null) {
                    new TelaPrincipal(u).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciais inválidas!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ErroAplicacao ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

class DialogCadastro extends JDialog {
    private final AlunoController alunoController = new AlunoController();
    private final PlanoController planoController = new PlanoController();

    public DialogCadastro(JFrame parent) {
        super(parent, "Cadastro de Novo Aluno", true);
        setSize(350, 400);
        setResizable(false);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Estilos.COR_FUNDO);
        setLayout(null);

        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtUsuario = new JTextField();
        JPasswordField txtSenha = new JPasswordField();

        adicionarLinha("Nome:", txtNome, 30);
        adicionarLinha("CPF:", txtCpf, 80);
        adicionarLinha("Criar Usuário:", txtUsuario, 130);
        adicionarLinha("Criar Senha:", txtSenha, 180);

        JButton btnSalvar = new JButton("Finalizar Cadastro");
        Estilos.aplicarEstiloBotao(btnSalvar);
        btnSalvar.setBounds(25, 245, 285, 40);
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                List<Plano> planos = planoController.listar();
                alunoController.cadastrar(txtNome.getText(), txtCpf.getText(), txtUsuario.getText(),
                        new String(txtSenha.getPassword()), planos.isEmpty() ? null : planos.get(0));
            } catch (ErroAplicacao ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Cadastro realizado! Faça seu login.");
            dispose();
        });
    }

    private void adicionarLinha(String texto, JComponent campo, int y) {
        JLabel l = new JLabel(texto);
        l.setForeground(Estilos.COR_TEXTO);
        l.setBounds(25, y, 100, 28);
        campo.setBounds(130, y, 180, 28);
        add(l);
        add(campo);
    }
}
