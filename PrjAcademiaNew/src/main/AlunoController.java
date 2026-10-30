package main;

import java.sql.SQLException;
import java.util.List;

// CONTROLLER - valida os dados vindos das telas e chama o DAO
public class AlunoController {
    private final AlunoDAO dao = new AlunoDAO();

    public void cadastrar(String nome, String cpf, String login, String senha, Plano plano) {
        nome = nome.trim(); cpf = cpf.trim(); login = login.trim();
        if (nome.isEmpty() || cpf.isEmpty() || login.isEmpty() || senha.isEmpty()) {
            throw new ErroAplicacao("Preencha os campos obrigatórios!");
        }
        try {
            if (dao.loginExiste(login)) throw new ErroAplicacao("Este usuário já existe.");
            if (dao.cpfExiste(cpf, 0)) throw new ErroAplicacao("Já existe um aluno com este CPF.");
            dao.inserir(new Aluno(0, nome, login, senha, cpf, plano, "Musculação", "", "", login + "@academia.com"));
        } catch (SQLException e) {
            throw new ErroAplicacao("Erro ao cadastrar o aluno: " + e.getMessage(), e);
        }
    }

    // ALTERAÇÃO feita pelo funcionário/instrutor (tela Ver Alunos). 
    public void alterar(Aluno aluno, String nome, String cpf, Plano plano, String modalidade) {
        nome = nome.trim(); cpf = cpf.trim(); modalidade = modalidade.trim();
        if (nome.isEmpty() || cpf.isEmpty() || modalidade.isEmpty()) {
            throw new ErroAplicacao("Preencha todos os campos!");
        }
        if (plano == null) throw new ErroAplicacao("Selecione um plano.");

        Aluno backup = aluno.clonar();
        try {
            if (dao.cpfExiste(cpf, aluno.getId())) throw new ErroAplicacao("Já existe outro aluno com este CPF.");
            aluno.setNome(nome); aluno.setCpf(cpf); aluno.setPlano(plano); aluno.setModalidade(modalidade);
            if (dao.alterar(aluno) == 0) {
                aluno.copiarDe(backup);
                throw new ErroAplicacao("Aluno não encontrado (talvez já tenha sido excluído).");
            }
        } catch (SQLException e) {
            aluno.copiarDe(backup);
            throw new ErroAplicacao("Erro ao alterar o aluno: " + e.getMessage(), e);
        }
    }

    /** ALTERAÇÃO feita pelo próprio aluno (tela Meu Perfil). */
    public void alterarPerfil(Aluno aluno, String nome, String nascimento, String telefone, String email) {
        nome = nome.trim(); nascimento = nascimento.trim(); telefone = telefone.trim(); email = email.trim();
        if (nome.isEmpty()) throw new ErroAplicacao("O nome é obrigatório.");
        if (!nascimento.isEmpty() && !nascimento.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new ErroAplicacao("Data de nascimento inválida. Use o formato dd/mm/aaaa.");
        }
        if (!email.isEmpty() && !email.contains("@")) throw new ErroAplicacao("E-mail inválido.");

        Aluno backup = aluno.clonar();
        aluno.setNome(nome); aluno.setDataNascimento(nascimento);
        aluno.setTelefone(telefone); aluno.setEmail(email);
        try {
            dao.alterar(aluno);
        } catch (SQLException e) {
            aluno.copiarDe(backup);
            throw new ErroAplicacao("Erro ao atualizar o perfil: " + e.getMessage(), e);
        }
    }

    public void excluir(Aluno aluno) {
        try {
            dao.excluir(aluno.getId());
        } catch (SQLException e) {
            throw new ErroAplicacao("Erro ao excluir o aluno: " + e.getMessage(), e);
        }
    }

    public List<Aluno> listar() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new ErroAplicacao("Erro ao consultar os alunos: " + e.getMessage(), e);
        }
    }

    public int quantidade() {
        try {
            return dao.contar();
        } catch (SQLException e) {
            throw new ErroAplicacao("Erro ao contar os alunos: " + e.getMessage(), e);
        }
    }
}
