/**
 * Descrição: Verifica a apresentação do menu e a delegação das opções ao controlador
 * Autora: Ramona
 */
package sistema.alunos.view

import kotlin.test.*

class MenuPrincipalTest {

    private class MockEntrada(private val valores: List<Int>) : Entrada {
        private var index = 0
        override fun lerTexto(mensagem: String) = "teste"
        override fun lerInteiro(mensagem: String) = valores[index++]
        override fun lerDecimal(mensagem: String) = 0.0
        override fun lerInteiro(mensagem: String, min: Int, max: Int) = valores[index++]
    }

    private class MockSaida : Saida {
        val mensagens = mutableListOf<String>()
        override fun exibir(mensagem: String) { mensagens.add(mensagem) }
        override fun exibirErro(erro: String) { mensagens.add(erro) }
        override fun exibirResultado(resultado: String) { mensagens.add(resultado) }
        override fun limparTela() {}
        override fun exibirMenu(titulo: String, opcoes: List<String>) {}
    }

    private class MockController : Controlador {
        var aluno = false
        var avaliacao = false
        var relatorio = false
        var estatisticas = false
        var listagem = false

        override fun cadastrarAluno() { aluno = true }
        override fun registrarAvaliacao() { avaliacao = true }
        override fun consultarRelatorio() { relatorio = true }
        override fun verEstatisticas() { estatisticas = true }
        override fun listarAlunos() { listagem = true }
    }

    @Test
    fun `deve chamar cadastrarAluno e encerrar`() {
        val entrada = MockEntrada(listOf(1, 6))
        val saida = MockSaida()
        val controller = MockController()
        val menu = MenuPrincipal(entrada, saida, controller)

        menu.iniciar()

        assertTrue(controller.aluno)
        assertTrue(saida.mensagens.any { it.contains("Encerrando") })
    }

    @Test
    fun `deve apresentar todas as opcoes do menu`() {
        val saida = MockSaida()
        val menu = MenuPrincipal(MockEntrada(listOf(6, 5)), saida, MockController())

        menu.iniciar()

        assertTrue(saida.mensagens.any { it.contains("1. Cadastrar aluno") })
        assertTrue(saida.mensagens.any { it.contains("2. Registrar avaliacao") })
        assertTrue(saida.mensagens.any { it.contains("3. Consultar relatorio") })
        assertTrue(saida.mensagens.any { it.contains("4. Ver estatisticas") })
        assertTrue(saida.mensagens.any { it.contains("5. Listar alunos") })
        assertTrue(saida.mensagens.any { it.contains("6. Sair") })
    }

    @Test
    fun `deve delegar registro de avaliacao`() {
        val controller = MockController()
        MenuPrincipal(MockEntrada(listOf(2, 6)), MockSaida(), controller).iniciar()

        assertTrue(controller.avaliacao)
    }

    @Test
    fun `deve delegar consulta de relatorio`() {
        val controller = MockController()
        MenuPrincipal(MockEntrada(listOf(3, 6)), MockSaida(), controller).iniciar()

        assertTrue(controller.relatorio)
    }

    @Test
    fun `deve delegar exibicao de estatisticas`() {
        val controller = MockController()
        MenuPrincipal(MockEntrada(listOf(4, 6)), MockSaida(), controller).iniciar()

        assertTrue(controller.estatisticas)
    }

    @Test
    fun `deve delegar listagem de alunos`() {
        val controller = MockController()
        MenuPrincipal(MockEntrada(listOf(5, 6)), MockSaida(), controller).iniciar()

        assertTrue(controller.listagem)
    }
}
