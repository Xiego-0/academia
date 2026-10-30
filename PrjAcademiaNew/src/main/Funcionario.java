package main;

// MODEL
public class Funcionario extends Usuario {
    public Funcionario(String nome, String login, String senha) {
        super(nome, login, senha, "Funcionario");
    }
    public Funcionario(int id, String nome, String login, String senha) {
        super(id, nome, login, senha, "Funcionario");
    }
}
