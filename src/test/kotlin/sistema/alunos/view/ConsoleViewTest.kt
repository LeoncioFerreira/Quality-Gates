/**
 * Descrição: Verifica as principais saídas apresentadas no terminal
 * Autora: Ramona
 */
package sistema.alunos.view

import kotlin.test.*
import java.io.*

class ConsoleViewTest {

    private val originalOut: PrintStream = System.out
    private lateinit var output: ByteArrayOutputStream
    private lateinit var consoleView: ConsoleView

    @BeforeTest
    fun setup() {
        output = ByteArrayOutputStream()
        System.setOut(PrintStream(output))
        consoleView = ConsoleView()
    }

    @AfterTest
    fun restoreStreams() {
        System.setOut(originalOut)
    }

    @Test
    fun `deve exibir mensagem simples`() {
        consoleView.exibir("Olá mundo")
        assertTrue(output.toString().contains("Olá mundo"))
    }

    @Test
    fun `deve exibir erro formatado`() {
        consoleView.exibirErro("Falha")
        assertTrue(output.toString().contains("[ERRO] Falha"))
    }

    @Test
    fun `deve exibir resultado formatado`() {
        consoleView.exibirResultado("Aprovado")
        val saida = output.toString()
        assertTrue(saida.contains("RESULTADO"))
        assertTrue(saida.contains("Aprovado"))
    }

    @Test
    fun `deve manter resultados longos e multilinha dentro das bordas`() {
        consoleView.exibirResultado("Primeira linha\n${"A".repeat(39)}")

        val linhasDeConteudo = output.toString()
            .lineSequence()
            .filter { it.startsWith("| ") && !it.contains("RESULTADO") }
            .toList()

        assertEquals(
            listOf(
                "| Primeira linha".padEnd(40) + " |",
                "| ${"A".repeat(38)} |",
                "| A".padEnd(40) + " |",
            ),
            linhasDeConteudo,
        )
    }

    @Test
    fun `deve quebrar resultado entre palavras quando houver espaco`() {
        consoleView.exibirResultado("Aluno Maria Silva cadastrado com sucesso!")

        val linhasDeConteudo = output.toString()
            .lineSequence()
            .filter { it.startsWith("| ") && !it.contains("RESULTADO") }
            .toList()

        assertEquals(
            listOf(
                "| Aluno Maria Silva cadastrado com".padEnd(40) + " |",
                "| sucesso!".padEnd(40) + " |",
            ),
            linhasDeConteudo,
        )
    }

    @Test
    fun `deve exibir menu com opcoes`() {
        consoleView.exibirMenu("Menu Teste", listOf("Opção 1", "Opção 2"))
        val saida = output.toString()
        assertTrue(saida.contains("Menu Teste"))
        assertTrue(saida.contains("1. Opção 1"))
        assertTrue(saida.contains("2. Opção 2"))
    }

    @Test
    fun `deve limpar tela sem falhar`() {
        try {
            consoleView.limparTela()
            assertTrue(true) // passou sem erro
        } catch (e: Exception) {
            fail("limparTela lançou uma exceção inesperada: $e")
        }
    }
}
