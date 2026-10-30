package main;

// MODEL
public abstract class Usuario {
    private int id;
    private String nome;
    private String login;
    private String senha;
    private String tipo;

    public Usuario(String nome, String login, String senha, String tipo) {
        this(0, nome, login, senha, tipo);
    }

    public Usuario(int id, String nome, String login, String senha, String tipo) {
        this.id = id; this.nome = nome; this.login = login; this.senha = senha; this.tipo = tipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getTipo() { return tipo; }
}
