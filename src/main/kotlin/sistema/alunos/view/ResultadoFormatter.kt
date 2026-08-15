package sistema.alunos.view

import sistema.alunos.model.Aluno
import sistema.alunos.service.EstatisticasTurma
import sistema.alunos.service.RelatorioAcademico

/**
 * Descrição: Transforma os dados brutos do sistema em texto formatado para a tela.
 * Autor: Paulo
 */
object ResultadoFormatter {

    fun formatarAlunos(alunos: List<Aluno>): String {
        if (alunos.isEmpty()) return "Nenhum aluno cadastrado."

        return alunos
            .sortedBy { it.id }
            .joinToString(
                separator = "\n",
                prefix = "Alunos cadastrados:\n",
            ) { aluno -> "- ${aluno.id} - ${aluno.nome}" }
    }

    fun formatarRelatorio(relatorio: RelatorioAcademico): String {
        val builder = StringBuilder()
        builder.appendLine("Relatorio de ${relatorio.nomeAluno}:")
        builder.appendLine("Media Geral: ${relatorio.mediaGeral}")

        relatorio.disciplinas.forEach { item ->
            val notasStr = item.notas.joinToString(", ") { it.valor.toString() }
            builder.appendLine("  - ${item.nomeDisciplina}: Notas [$notasStr], Media: ${item.media} (${item.situacao})")
        }

        return builder.toString().trimEnd()
    }

    fun formatarEstatisticas(estatisticas: EstatisticasTurma): String {
        val builder = StringBuilder()
        builder.appendLine("Media Geral da Turma: ${estatisticas.mediaGeralTurma}")

        estatisticas.alunosPorSituacao.forEach { (situacao, alunos) ->
            builder.appendLine("Situacao $situacao: ${alunos.size} aluno(s)")
        }

        return builder.toString().trimEnd()
    }
}
