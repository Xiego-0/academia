package main;

import java.util.ArrayList;
import java.util.List;

// MODEL
public class Aluno extends Usuario {
    private String cpf;
    private Plano plano;
    private String modalidade;
    private List<Treino> treinos;
    private String dataNascimento;
    private String telefone;
    private String email;

    public Aluno(int id, String nome, String login, String senha, String cpf, Plano plano,
                 String modalidade, String dataNascimento, String telefone, String email) {
        super(id, nome, login, senha, "Aluno");
        this.cpf = cpf; this.plano = plano; this.modalidade = modalidade;
        this.dataNascimento = dataNascimento; this.telefone = telefone; this.email = email;
        this.treinos = new ArrayList<>();
    }

    public String getCpf() {
    	return cpf; 
    }
    public void setCpf(String cpf) {
    	this.cpf = cpf;
    }
    public Plano getPlano() { 
    	return plano;
    }
    public void setPlano(Plano plano) { 
    	this.plano = plano;
    }
    public String getModalidade() { 
    	return modalidade; 
    }
    public void setModalidade(String modalidade) {
    	this.modalidade = modalidade; 
    }
    public String getDataNascimento() {
    	return dataNascimento; 
    }
    public void setDataNascimento(String dataNascimento) { 
    	this.dataNascimento = dataNascimento;
    }
    public String getTelefone() { 
    	return telefone; 
    }
    public void setTelefone(String telefone) { 
    	this.telefone = telefone;
    }
    public String getEmail() {
    	return email; 
    }
    public void setEmail(String email) { 
    	this.email = email;
    }

    public List<Treino> getTreinos() { return treinos; }
    public void adicionarTreino(Treino treino) { this.treinos.add(treino); }

    // Usados pelo controller para desfazer a alteração se o banco falhar
    public Aluno clonar() {
        return new Aluno(getId(), getNome(), getLogin(), getSenha(), cpf, plano, modalidade, dataNascimento, telefone, email);
    }
    public void copiarDe(Aluno o) {
        setNome(o.getNome()); this.cpf = o.cpf; this.plano = o.plano; this.modalidade = o.modalidade;
        this.dataNascimento = o.dataNascimento; this.telefone = o.telefone; this.email = o.email;
    }
}
