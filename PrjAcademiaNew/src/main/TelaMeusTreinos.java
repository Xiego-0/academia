package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class TelaMeusTreinos extends JPanel {
    private JScrollPane scroll;

    public TelaMeusTreinos(Aluno aluno) {
        setBackground(Estilos.COR_FUNDO);
        setLayout(null);

        JLabel titulo = new JLabel("Minhas Fichas de Treino");
        titulo.setFont(Estilos.FONTE_TITULO);
        titulo.setForeground(Estilos.COR_TEXTO);
        titulo.setBounds(20, 20, 600, 35);
        add(titulo);

        DefaultTableModel modelo = new DefaultTableModel(new String[]{"Exercício", "Grupo Muscular", "Séries", "Repetições"}, 0);
        JTable tabela = new JTable(modelo);
        tabela.setBackground(Estilos.COR_CARD);
        tabela.setForeground(Estilos.COR_TEXTO);
        tabela.getTableHeader().setBackground(Estilos.COR_MENU);
        tabela.getTableHeader().setForeground(Color.WHITE);

        for (Treino t : aluno.getTreinos()) {
            modelo.addRow(new Object[]{"► " + t.getNome().toUpperCase(), "", "", ""});
            for (Exercicio ex : t.getExercicios()) {
                modelo.addRow(new Object[]{ex.getNome(), ex.getGrupoMuscular(), ex.getSeries(), ex.getRepeticoes()});
            }
        }

        scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 70, 860, 480);
        add(scroll);

        // Acompanha o tamanho da janela (a tabela ocupa o espaço abaixo do título)
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scroll.setBounds(20, 70, getWidth() - 40, getHeight() - 90);
            }
        });
    }
}
