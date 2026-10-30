package main;

// MODEL
public class Instrutor extends Usuario {
    public Instrutor(String nome, String login, String senha) {
        super(nome, login, senha, "Instrutor");
    }
    public Instrutor(int id, String nome, String login, String senha) {
        super(id, nome, login, senha, "Instrutor");
    }
}
