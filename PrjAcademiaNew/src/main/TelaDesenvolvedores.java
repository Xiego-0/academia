package main;

import javax.swing.*;
import java.awt.*;

class TelaDesenvolvedores extends JPanel {
    public TelaDesenvolvedores() {
        setBackground(Estilos.COR_FUNDO);
        setLayout(null);

        JLabel titulo = new JLabel("Equipe de Desenvolvimento");
        titulo.setFont(Estilos.FONTE_TITULO);
        titulo.setForeground(Estilos.COR_TEXTO);
        titulo.setBounds(20, 20, 500, 35);
        add(titulo);

        add(criarCardDev("Seu Nome", "Desenvolvedor Principal", 20, 80));
        add(criarCardDev("Integrante 2", "Documentação / POO", 260, 80));
        add(criarCardDev("Integrante 3", "Testes e Validação", 500, 80));
    }

    private JPanel criarCardDev(String nome, String funcao, int x, int y) {
        JPanel card = new JPanel(null);
        card.setBackground(Estilos.COR_CARD);
        card.setBounds(x, y, 220, 140);

        JLabel icone = new JLabel("🧑‍💻", SwingConstants.CENTER);
        icone.setBounds(0, 10, 220, 30);

        JLabel lblNome = new JLabel(nome, SwingConstants.CENTER);
        lblNome.setFont(Estilos.FONTE_NEGRITO);
        lblNome.setForeground(Estilos.COR_TEXTO);
        lblNome.setBounds(0, 55, 220, 25);

        JLabel lblFuncao = new JLabel(funcao, SwingConstants.CENTER);
        lblFuncao.setForeground(Color.GRAY);
        lblFuncao.setBounds(0, 90, 220, 25);

        card.add(icone);
        card.add(lblNome);
        card.add(lblFuncao);
        return card;
    }
}
