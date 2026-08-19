/**
 * Descrição: Verifica o cadastro, a duplicidade, a busca e a listagem de alunos
 * Autor: Pedro Kauan Cardoso da Silva
 */
package sistema.alunos.service

import org.junit.jupiter.api.Disabled
import sistema.alunos.repository.AlunoRepositoryEmMemoria
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@Disabled("Experimento JaCoCo")
class CadastroAlunoServiceTest {
    @Test
    fun `deve cadastrar aluno com dados validos`() {
        val servico = CadastroAlunoService(AlunoRepositoryEmMemoria())

        val aluno = servico.cadastrar(id = "A01", nome = "Maria Souza")

        assertEquals("A01", aluno.id)
        assertEquals("Maria Souza", aluno.nome)
        assertEquals(aluno, servico.buscarPorId("A01"))
    }

    @Test
    fun `deve rejeitar cadastro com identificador vazio`() {
        val servico = CadastroAlunoService(AlunoRepositoryEmMemoria())

        assertFailsWith<IllegalArgumentException> {
            servico.cadastrar(id = "", nome = "Maria Souza")
        }
    }

    @Test
    fun `deve rejeitar cadastro com nome vazio`() {
        val servico = CadastroAlunoService(AlunoRepositoryEmMemoria())

        assertFailsWith<IllegalArgumentException> {
            servico.cadastrar(id = "A01", nome = "")
        }
    }

    @Test
    fun `deve impedir cadastro de identificador duplicado`() {
        val servico = CadastroAlunoService(AlunoRepositoryEmMemoria())
        servico.cadastrar(id = "A01", nome = "Maria Souza")

        assertFailsWith<AlunoJaCadastradoException> {
            servico.cadastrar(id = "A01", nome = "Outro Nome")
        }
    }

    @Test
    fun `deve lancar erro explicito ao buscar aluno inexistente`() {
        val servico = CadastroAlunoService(AlunoRepositoryEmMemoria())

        assertFailsWith<AlunoNaoEncontradoException> {
            servico.buscarPorId("INEXISTENTE")
        }
    }

    @Test
    fun `deve listar todos os alunos cadastrados`() {
        val servico = CadastroAlunoService(AlunoRepositoryEmMemoria())
        servico.cadastrar(id = "A01", nome = "Maria Souza")
        servico.cadastrar(id = "A02", nome = "João Pedro")

        val alunos = servico.listarAlunos()

        assertEquals(2, alunos.size)
    }
}
