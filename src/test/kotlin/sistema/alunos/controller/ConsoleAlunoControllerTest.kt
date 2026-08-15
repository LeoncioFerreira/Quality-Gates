package sistema.alunos.controller

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import sistema.alunos.model.Aluno
import sistema.alunos.repository.AlunoRepositoryEmMemoria
import sistema.alunos.repository.AvaliacaoRepositoryEmMemoria
import sistema.alunos.service.AvaliacaoService
import sistema.alunos.service.CadastroAlunoService
import sistema.alunos.service.EstatisticaTurmaService
import sistema.alunos.service.RelatorioAcademicoService
import sistema.alunos.view.Entrada
import sistema.alunos.view.Saida

class ConsoleAlunoControllerTest {

    private class EntradaFake(
        textos: List<String> = emptyList(),
        decimais: List<Double> = emptyList(),
    ) : Entrada {
        private val textos = ArrayDeque(textos)
        private val decimais = ArrayDeque(decimais)
        val mensagens = mutableListOf<String>()

        override fun lerTexto(mensagem: String): String {
            mensagens += mensagem
            return textos.removeFirst()
        }

        override fun lerDecimal(mensagem: String): Double {
            mensagens += mensagem
            return decimais.removeFirst()
        }

        override fun lerInteiro(mensagem: String): Int = error("Leitura inteira nao esperada")

        override fun lerInteiro(mensagem: String, min: Int, max: Int): Int =
            error("Leitura inteira nao esperada")
    }

    private class SaidaFake : Saida {
        val mensagens = mutableListOf<String>()
        val erros = mutableListOf<String>()
        val resultados = mutableListOf<String>()

        override fun exibir(mensagem: String) { mensagens += mensagem }
        override fun exibirErro(erro: String) { erros += erro }
        override fun exibirResultado(resultado: String) { resultados += resultado }
        override fun limparTela() = Unit
        override fun exibirMenu(titulo: String, opcoes: List<String>) = Unit
    }

    private fun criarController(
        entrada: Entrada,
        saida: Saida,
        alunoRepository: AlunoRepositoryEmMemoria = AlunoRepositoryEmMemoria(),
    ): ConsoleAlunoController {
        val avaliacaoRepository = AvaliacaoRepositoryEmMemoria()

        return ConsoleAlunoController(
            entrada = entrada,
            saida = saida,
            cadastroService = CadastroAlunoService(alunoRepository),
            avaliacaoService = AvaliacaoService(alunoRepository, avaliacaoRepository),
            relatorioService = RelatorioAcademicoService(alunoRepository, avaliacaoRepository),
            estatisticaService = EstatisticaTurmaService(alunoRepository, avaliacaoRepository),
        )
    }

    @Test
    fun `deve validar aluno antes de pedir dados da avaliacao`() {
        val entrada = EntradaFake(textos = listOf("INEXISTENTE"))
        val saida = SaidaFake()
        val controller = criarController(entrada, saida)

        controller.registrarAvaliacao()

        assertEquals(listOf("Digite a matricula do aluno (0 para voltar): "), entrada.mensagens)
        assertTrue(saida.erros.single().contains("Nenhum aluno encontrado"))
    }

    @Test
    fun `deve cancelar registro de avaliacao ao informar zero`() {
        val entrada = EntradaFake(textos = listOf("0"))
        val saida = SaidaFake()
        val controller = criarController(entrada, saida)

        controller.registrarAvaliacao()

        assertEquals(listOf("Digite a matricula do aluno (0 para voltar): "), entrada.mensagens)
        assertTrue(saida.mensagens.contains("Operacao cancelada."))
        assertTrue(saida.erros.isEmpty())
    }

    @Test
    fun `deve cancelar consulta de relatorio ao informar zero`() {
        val entrada = EntradaFake(textos = listOf("0"))
        val saida = SaidaFake()
        val controller = criarController(entrada, saida)

        controller.consultarRelatorio()

        assertEquals(listOf("Digite a matricula do aluno (0 para voltar): "), entrada.mensagens)
        assertTrue(saida.mensagens.contains("Operacao cancelada."))
        assertTrue(saida.erros.isEmpty())
    }

    @Test
    fun `deve informar quando nao houver alunos cadastrados`() {
        val saida = SaidaFake()
        val controller = criarController(EntradaFake(), saida)

        controller.listarAlunos()

        assertEquals(listOf("Nenhum aluno cadastrado."), saida.resultados)
    }

    @Test
    fun `deve listar alunos ordenados pela matricula`() {
        val alunoRepository = AlunoRepositoryEmMemoria().apply {
            salvar(Aluno("B02", "Bruno"))
            salvar(Aluno("A01", "Ana"))
        }
        val saida = SaidaFake()
        val controller = criarController(EntradaFake(), saida, alunoRepository)

        controller.listarAlunos()

        assertEquals(
            listOf("Alunos cadastrados:\n- A01 - Ana\n- B02 - Bruno"),
            saida.resultados,
        )
    }
}
