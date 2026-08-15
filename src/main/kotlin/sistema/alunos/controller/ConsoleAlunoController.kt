/**
 * Descrição: Implementa o Controlador do sistema acadêmico integrando View e Serviços (Model).
 * Autor: Paulo
 */
package sistema.alunos.controller

import sistema.alunos.model.Disciplina
import sistema.alunos.model.Nota
import sistema.alunos.service.AvaliacaoService
import sistema.alunos.service.CadastroAlunoService
import sistema.alunos.service.EstatisticaTurmaService
import sistema.alunos.service.RelatorioAcademicoService
import sistema.alunos.view.Controlador
import sistema.alunos.view.Entrada
import sistema.alunos.view.ResultadoFormatter
import sistema.alunos.view.Saida

class ConsoleAlunoController(
    private val entrada: Entrada,
    private val saida: Saida,
    private val cadastroService: CadastroAlunoService,
    private val avaliacaoService: AvaliacaoService,
    private val relatorioService: RelatorioAcademicoService,
    private val estatisticaService: EstatisticaTurmaService
) : Controlador {

    override fun cadastrarAluno() {
        saida.exibir("\n--- CADASTRO DE ALUNO ---")
        try {
            val id = entrada.lerTexto("Digite a matricula do aluno: ")
            val nome = entrada.lerTexto("Digite o nome completo: ")

            val aluno = cadastroService.cadastrar(id, nome)
            saida.exibirResultado("Aluno ${aluno.nome} cadastrado com sucesso!")
        } catch (e: Exception) {
            saida.exibirErro(e.message ?: "Erro desconhecido ao cadastrar aluno.")
        }
    }

    override fun registrarAvaliacao() {
        saida.exibir("\n--- REGISTRO DE NOTAS ---")
        try {
            val id = entrada.lerTexto("Digite a matricula do aluno (0 para voltar): ")
            if (id == "0") {
                saida.exibir("Operacao cancelada.")
                return
            }
            cadastroService.buscarPorId(id)

            val codigoDisciplina = entrada.lerTexto("Digite o codigo da disciplina: ")
            val nomeDisciplina = entrada.lerTexto("Digite o nome da disciplina: ")
            val disciplina = Disciplina(codigoDisciplina, nomeDisciplina)

            val nota1 = entrada.lerDecimal("Digite a 1a nota (0 a 10): ")
            val nota2 = entrada.lerDecimal("Digite a 2a nota (0 a 10): ")

            avaliacaoService.registrarAvaliacao(
                idAluno = id,
                disciplina = disciplina,
                notas = listOf(Nota(nota1), Nota(nota2))
            )

            saida.exibirResultado("Avaliacao registrada com sucesso para o aluno $id!")
        } catch (e: Exception) {
            saida.exibirErro(e.message ?: "Erro desconhecido ao registrar avaliacao.")
        }
    }

    override fun consultarRelatorio() {
        saida.exibir("\n--- CONSULTA DE BOLETIM ---")
        try {
            val id = entrada.lerTexto("Digite a matricula do aluno (0 para voltar): ")
            if (id == "0") {
                saida.exibir("Operacao cancelada.")
                return
            }
            val relatorio = relatorioService.gerarRelatorio(id)

            saida.exibirResultado(sistema.alunos.view.ResultadoFormatter.formatarRelatorio(relatorio))
        } catch (e: Exception) {
            saida.exibirErro(e.message ?: "Erro desconhecido ao gerar relatorio.")
        }
    }

    override fun verEstatisticas() {
        saida.exibir("\n--- ESTATISTICAS DA TURMA ---")
        try {
            val estatisticas = estatisticaService.calcularEstatisticas()
            saida.exibirResultado(sistema.alunos.view.ResultadoFormatter.formatarEstatisticas(estatisticas))
        } catch (e: Exception) {
            saida.exibirErro(e.message ?: "Erro desconhecido ao calcular estatisticas.")
        }
    }

    override fun listarAlunos() {
        val alunos = cadastroService.listarAlunos()
        saida.exibirResultado(ResultadoFormatter.formatarAlunos(alunos))
    }
}
