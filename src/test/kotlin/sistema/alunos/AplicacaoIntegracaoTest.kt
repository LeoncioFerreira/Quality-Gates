package sistema.alunos

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import sistema.alunos.controller.ConsoleAlunoController
import sistema.alunos.repository.AlunoRepositoryEmMemoria
import sistema.alunos.repository.AvaliacaoRepositoryEmMemoria
import sistema.alunos.service.AvaliacaoService
import sistema.alunos.service.CadastroAlunoService
import sistema.alunos.service.EstatisticaTurmaService
import sistema.alunos.service.RelatorioAcademicoService
import sistema.alunos.view.Entrada
import sistema.alunos.view.MenuPrincipal
import sistema.alunos.view.Saida

/**
 * Descrição: Teste de integração de ponta a ponta simulando o uso completo do sistema
 * Autor: Paulo
 */
class AplicacaoIntegracaoTest {

    // Cria um "Mock" da Entrada para simular um usuário digitando no teclado
    class EntradaMock(
        private val inteiros: MutableList<Int>,
        private val textos: MutableList<String>,
        private val decimais: MutableList<Double>
    ) : Entrada {
        override fun lerInteiro(mensagem: String): Int = inteiros.removeAt(0)
        override fun lerInteiro(mensagem: String, min: Int, max: Int): Int = inteiros.removeAt(0)
        override fun lerTexto(mensagem: String): String = textos.removeAt(0)
        override fun lerDecimal(mensagem: String): Double = decimais.removeAt(0)
    }

    // Cria um "Mock" da Saída para podermos ler o que o sistema imprimiu e verificar se está certo
    class SaidaMock : Saida {
        val mensagensExibidas = mutableListOf<String>()

        override fun exibir(mensagem: String) { mensagensExibidas.add(mensagem) }
        override fun exibirErro(erro: String) { mensagensExibidas.add("ERRO: $erro") }
        override fun exibirResultado(resultado: String) { mensagensExibidas.add("RESULTADO: $resultado") }
        override fun limparTela() {}
        override fun exibirMenu(titulo: String, opcoes: List<String>) {}
    }

    @Test
    fun `deve cadastrar aluno, lancar notas, consultar situacao e sair do sistema`() {
        // 1. Preparação (Setup) dos serviços reais
        val alunoRepo = AlunoRepositoryEmMemoria()
        val avaliacaoRepo = AvaliacaoRepositoryEmMemoria()

        val cadastro = CadastroAlunoService(alunoRepo)
        val avaliacao = AvaliacaoService(alunoRepo, avaliacaoRepo)
        val relatorio = RelatorioAcademicoService(alunoRepo, avaliacaoRepo)
        val estatistica = EstatisticaTurmaService(alunoRepo, avaliacaoRepo)

        // 2. Simulação de Dados do Usuário
        // Simulando a ordem exata do que o usuário digitaria:
        val entradasInt = mutableListOf(
            1, // Escolhe opção 1 do menu principal (Cadastrar)
            5, // Escolhe opção 5 do menu principal (Listar alunos)
            2, // Escolhe opção 2 do menu principal (Avaliação)
            3, // Escolhe opção 3 do menu principal (Relatório)
            6  // Escolhe opção 6 do menu principal (Sair)
        )
        val entradasString = mutableListOf(
            "A001", "Paulo Landim", // Para o cadastro
            "A001", "POO", "Programacao Orientada a Objetos", // Para a avaliação
            "A001" // Para o relatório
        )
        val entradasDouble = mutableListOf(
            8.0, 9.5 // Para a avaliação
        )

        val entradaMock = EntradaMock(entradasInt, entradasString, entradasDouble)
        val saidaMock = SaidaMock()

        val controller = ConsoleAlunoController(
            entrada = entradaMock,
            saida = saidaMock,
            cadastroService = cadastro,
            avaliacaoService = avaliacao,
            relatorioService = relatorio,
            estatisticaService = estatistica
        )

        val menu = MenuPrincipal(entradaMock, saidaMock, controller)

        // 3. Ação (Executar o sistema)
        menu.iniciar()

        // 4. Verificação (Asserts)
        val todasAsSaidas = saidaMock.mensagensExibidas.joinToString("\n")

        // Verifica se o aluno foi cadastrado
        assertTrue(todasAsSaidas.contains("Aluno Paulo Landim cadastrado com sucesso!"), "Deveria ter cadastrado o aluno")
        assertTrue(todasAsSaidas.contains("- A001 - Paulo Landim"), "Deveria listar o aluno cadastrado")

        // Verifica se as notas foram lançadas
        assertTrue(todasAsSaidas.contains("Avaliacao registrada com sucesso para o aluno A001!"), "Deveria ter registrado a avaliação")

        // Verifica se o relatório foi consultado e a média calculada
        assertTrue(todasAsSaidas.contains("Relatorio de Paulo Landim:"), "Deveria ter gerado o relatório")
        assertTrue(todasAsSaidas.contains("Sair"), "O programa deveria ter recebido o comando de saída")
    }
}
