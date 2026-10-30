package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedHashMap;
import java.util.Map;

class TelaPrincipal extends JFrame {
    private Usuario usuarioLogado;
    private JPanel painelCentral;
    // Sem CardLayout: todas as telas ficam sobrepostas e só a escolhida fica visível
    private Map<String, JPanel> telas = new LinkedHashMap<>();

    public TelaPrincipal(Usuario usuario) {
        this.usuarioLogado = usuario;
        setTitle("Academia+ | Olá, " + usuario.getNome());
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        setLayout(null);

        painelCentral = new JPanel(null);
        painelCentral.setBackground(Estilos.COR_FUNDO);
        painelCentral.setBounds(0, 0, 950, 600);
        add(painelCentral);

        // Ajusta o painel central e as telas quando a janela muda de tamanho
        getContentPane().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarTamanho();
            }
        });

        adicionarTela("Dashboard", criarDashboard());
        adicionarTela("Desenvolvedores", new TelaDesenvolvedores());

        JMenuBar barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemInicio = new JMenuItem("Início");
        JMenuItem itemSair = new JMenuItem("Sair");
        menuArquivo.add(itemInicio);
        menuArquivo.addSeparator();
        menuArquivo.add(itemSair);
        barraMenu.add(menuArquivo);

        itemInicio.addActionListener(e -> mostrarTela("Dashboard"));
        itemSair.addActionListener(e -> { new TelaLogin().setVisible(true); this.dispose(); });

        if (usuarioLogado instanceof Funcionario) {
            adicionarTela("Alunos", new TelaVerAlunos());
            adicionarTela("CadastrarAluno", new TelaCadastrarAluno());

            JMenu menuFuncionario = new JMenu("Gestão");
            JMenuItem itemAlunos = new JMenuItem("Ver Alunos");
            JMenuItem itemCadAluno = new JMenuItem("Cadastrar Aluno");
            menuFuncionario.add(itemAlunos);
            menuFuncionario.add(itemCadAluno);

            JMenuItem itemTreinos = new JMenuItem("Treinos"); itemTreinos.setEnabled(false);
            JMenuItem itemPlanos = new JMenuItem("Planos"); itemPlanos.setEnabled(false);
            menuFuncionario.add(itemTreinos);
            menuFuncionario.add(itemPlanos);
            barraMenu.add(menuFuncionario);

            itemAlunos.addActionListener(e -> mostrarTela("Alunos"));
            itemCadAluno.addActionListener(e -> mostrarTela("CadastrarAluno"));

        } else if (usuarioLogado instanceof Aluno) {
            adicionarTela("MeuPerfil", new TelaMeuPerfil((Aluno) usuarioLogado));
            adicionarTela("MeusTreinos", new TelaMeusTreinos((Aluno) usuarioLogado));

            JMenu menuAluno = new JMenu("Minha Conta");
            JMenuItem itemPerfil = new JMenuItem("Meu Perfil");
            JMenuItem itemTreinos = new JMenuItem("Meus Treinos");
            menuAluno.add(itemPerfil);
            menuAluno.add(itemTreinos);

            JMenuItem itemHistorico = new JMenuItem("Histórico"); itemHistorico.setEnabled(false);
            menuAluno.add(itemHistorico);
            barraMenu.add(menuAluno);

            itemPerfil.addActionListener(e -> mostrarTela("MeuPerfil"));
            itemTreinos.addActionListener(e -> mostrarTela("MeusTreinos"));

        } else if (usuarioLogado instanceof Instrutor) {
            adicionarTela("MeusAlunos", new TelaVerAlunos());

            JMenu menuInstrutor = new JMenu("Painel Instrutor");
            JMenuItem itemMeusAlunos = new JMenuItem("Ver Alunos");
            menuInstrutor.add(itemMeusAlunos);

            JMenuItem itemAvaliacoes = new JMenuItem("Avaliações Físicas"); itemAvaliacoes.setEnabled(false);
            menuInstrutor.add(itemAvaliacoes);
            barraMenu.add(menuInstrutor);

            itemMeusAlunos.addActionListener(e -> mostrarTela("MeusAlunos"));
        }

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemDevs = new JMenuItem("Desenvolvedores");
        menuAjuda.add(itemDevs);
        barraMenu.add(menuAjuda);
        itemDevs.addActionListener(e -> mostrarTela("Desenvolvedores"));

        mostrarTela("Dashboard");
        ajustarTamanho();
    }

    private void adicionarTela(String nome, JPanel painel) {
        painel.setBounds(0, 0, painelCentral.getWidth(), painelCentral.getHeight());
        painel.setVisible(false);
        telas.put(nome, painel);
        painelCentral.add(painel);
    }

    private void mostrarTela(String nome) {
        for (Map.Entry<String, JPanel> t : telas.entrySet()) {
            t.getValue().setVisible(t.getKey().equals(nome));
        }
    }

    private void ajustarTamanho() {
        int w = getContentPane().getWidth();
        int h = getContentPane().getHeight();
        painelCentral.setBounds(0, 0, w, h);
        for (JPanel tela : telas.values()) tela.setBounds(0, 0, w, h);
    }

    private JPanel criarDashboard() {
        JPanel dash = new JPanel(null);
        dash.setBackground(Estilos.COR_FUNDO);

        String qtdAlunos = "?", qtdPlanos = "?";
        try {
            qtdAlunos = String.valueOf(new AlunoController().quantidade());
            qtdPlanos = String.valueOf(new PlanoController().listar().size());
        } catch (ErroAplicacao ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        dash.add(criarCardInfo("Alunos Matriculados", qtdAlunos, 20, 20));
        dash.add(criarCardInfo("Planos Disponíveis", qtdPlanos, 260, 20));
        dash.add(criarCardInfo("Seu Perfil", usuarioLogado.getTipo(), 500, 20));
        return dash;
    }

    private JPanel criarCardInfo(String titulo, String valor, int x, int y) {
        JPanel card = new JPanel(null);
        card.setBackground(Estilos.COR_CARD);
        card.setBounds(x, y, 220, 110);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValor.setForeground(Estilos.COR_DESTAQUE);
        lblValor.setBounds(15, 10, 190, 50);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Estilos.COR_TEXTO);
        lblTitulo.setBounds(15, 65, 190, 25);

        card.add(lblValor);
        card.add(lblTitulo);
        return card;
    }
}
