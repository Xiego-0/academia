package main;

public class Exercicio {
    private String nome, grupoMuscular;
    private int series, repeticoes;
    
    public Exercicio(String nome, String grupo, int series, int rep) {
        this.nome = nome; this.grupoMuscular = grupo; this.series = series; this.repeticoes = rep;
    }
    public String getNome() {
    	return nome;
    }
    public String getGrupoMuscular() {
    	return grupoMuscular;
    }
    public int getSeries() {
    	return series;
    }
    public int getRepeticoes() {
    	return repeticoes;
    }
}