package main;

import java.util.Objects;

// MODEL
public class Plano {
    private int id;
    private String nome;
    private double valor;

    public Plano(int id, String nome, double valor) {
        this.id = id; this.nome = nome; this.valor = valor;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getValor() { return valor; }

    // equals por id: permite o JComboBox selecionar o plano atual do aluno
    @Override
    public boolean equals(Object o) {
        return o instanceof Plano && ((Plano) o).id == id;
    }
    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() { return nome; }
}
