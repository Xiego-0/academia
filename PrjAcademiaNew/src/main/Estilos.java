package main;

import javax.swing.*;
import java.awt.*;

public class Estilos {
    public static final Color COR_FUNDO = new Color(30, 30, 30);
    public static final Color COR_CARD = new Color(45, 45, 45);
    public static final Color COR_TEXTO = new Color(230, 230, 230);
    public static final Color COR_DESTAQUE = new Color(0, 123, 255);
    public static final Color COR_MENU = new Color(20, 20, 20);
    
    public static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONTE_PADRAO = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONTE_NEGRITO = new Font("Segoe UI", Font.BOLD, 14);

    public static void aplicarEstiloBotao(JButton botao) {
        botao.setBackground(COR_DESTAQUE);
        botao.setForeground(Color.WHITE);
        botao.setFont(FONTE_NEGRITO);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}