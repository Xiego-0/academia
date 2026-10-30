package main;

// Erro de validação ou de banco, com mensagem pronta para a VIEW exibir
public class ErroAplicacao extends RuntimeException {
    public ErroAplicacao(String mensagem) { super(mensagem); }
    public ErroAplicacao(String mensagem, Throwable causa) { super(mensagem, causa); }
}
