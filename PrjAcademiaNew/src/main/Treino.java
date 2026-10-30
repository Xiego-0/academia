package main;

import java.util.ArrayList;
import java.util.List;

public class Treino {
    private String nome;
    private List<Exercicio> exercicios;
    
    public Treino(String nome) {
    	this.nome = nome; this.exercicios = new ArrayList<>();
    }
    
    public String getNome() {
    	return nome;
    }
    
    public List<Exercicio> getExercicios() {
    	return exercicios;
    }
    
    public void adicionarExercicio(Exercicio ex) {
    	this.exercicios.add(ex);
    }
}