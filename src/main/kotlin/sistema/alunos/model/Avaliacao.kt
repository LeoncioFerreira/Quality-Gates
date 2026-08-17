/**
 * Descrição: Associa um aluno e uma disciplina a exatamente duas notas
 * Autor: Leôncio Ferreira
 */
package sistema.alunos.model

data class Avaliacao(
    val aluno: Aluno,
    val disciplina: Disciplina,
    val notas: List<Nota>,
) {
    init {
        require(notas.size == 2) {
            "A avaliacao deve possuir exatamente duas notas."
        }
    }
}
