package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

// VIEW - lista alunos; permite ALTERAR e excluir (via controller)
class TelaVerAlunos extends JPanel {
    private final AlunoController alunoController = new AlunoController();
    private final PlanoController planoController = new PlanoController();
    private List<Aluno> alunos = new ArrayList<>();   // mesma ordem das linhas da tabela

    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JScrollPane scroll;
    private JButton btnAtualizar, btnAlterar, btnExcluir;

    public TelaVerAlunos() {
        setBackground(Estilos.COR_FUNDO);
        setLayout(null);

        JLabel titulo = new JLabel("Lista de Alunos Cadastrados");
        titulo.setFont(Estilos.FONTE_TITULO);
        titulo.setForeground(Estilos.COR_TEXTO);
        titulo.setBounds(20, 20, 600, 35);
        add(titulo);

        modeloTabela = new DefaultTableModel(new String[]{"Nome", "CPF", "Plano", "Modalidade"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setBackground(Estilos.COR_CARD);
        tabela.setForeground(Estilos.COR_TEXTO);
        tabela.getTableHeader().setBackground(Estilos.COR_MENU);
        tabela.getTableHeader().setForeground(Color.WHITE);
        scroll = new JScrollPane(tabela);
        add(scroll);

        btnAtualizar = new JButton("Atualizar Lista");
        Estilos.aplicarEstiloBotao(btnAtualizar);
        btnAtualizar.addActionListener(e -> carregarDados());
        add(btnAtualizar);

        btnAlterar = new JButton("Alterar Selecionado");
        Estilos.aplicarEstiloBotao(btnAlterar);
        btnAlterar.addActionListener(e -> alterarAluno());
        add(btnAlterar);

        btnExcluir = new JButton("Excluir Selecionado");
        btnExcluir.setBackground(new Color(200, 50, 50));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.addActionListener(e -> excluirAluno());
        add(btnExcluir);

        reposicionar(900, 600);
        addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) { reposicionar(getWidth(), getHeight()); }
        });

        carregarDados();
    }

    private void reposicionar(int w, int h) {
        scroll.setBounds(20, 70, w - 40, h - 150);
        btnAtualizar.setBounds(w - 640, h - 65, 190, 40);
        btnAlterar.setBounds(w - 440, h - 65, 210, 40);
        btnExcluir.setBounds(w - 220, h - 65, 200, 40);
    }

    private void carregarDados() {
        modeloTabela.setRowCount(0);
        try {
            alunos = alunoController.listar();
        } catch (ErroAplicacao ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Aluno a : alunos) {
            modeloTabela.addRow(new Object[]{a.getNome(), a.getCpf(),
                    a.getPlano() != null ? a.getPlano().getNome() : "Sem Plano", a.getModalidade()});
        }
    }

    private void alterarAluno() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela.");
            return;
        }
        try {
            DialogAlterarAluno dialogo = new DialogAlterarAluno(SwingUtilities.getWindowAncestor(this),
                    alunos.get(linha), alunoController, planoController.listar());
            dialogo.setVisible(true);
            if (dialogo.foiAlterado()) carregarDados();
        } catch (ErroAplicacao ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirAluno() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela.");
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este aluno?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta != JOptionPane.YES_OPTION) return;
        try {
            alunoController.excluir(alunos.get(linha));
            carregarDados();
            JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso.");
        } catch (ErroAplicacao ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
