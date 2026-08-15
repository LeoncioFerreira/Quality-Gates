/**
 * Descrição: Apresenta o menu principal e delega as opções escolhidas ao controlador
 * Autora: Ramona
 */
package sistema.alunos.view

/**
 * Interface para desacoplar a View do Controller.
 * O MenuPrincipal não precisa conhecer os detalhes do Controller.
 */
interface Controlador {
    fun cadastrarAluno()
    fun registrarAvaliacao()
    fun consultarRelatorio()
    fun verEstatisticas()
    fun listarAlunos()
}

/**
 * Menu principal do sistema acadêmico.
 * Responsável apenas por:
 * - Exibir opções
 * - Capturar entrada do usuário
 * - Delegar ações para o Controller
 *
 * NÃO faz:
 * - Cálculos de média
 * - Acesso ao repositório
 * - Lógica de negócio
 *
 * Adaptado para:
 * - Usar exibir() em vez de exibirMensagem()
 * - Usar lerInteiro(min, max) com validação de intervalo
 */
class MenuPrincipal(
    private val input: Entrada,
    private val view: Saida,
    private val controller: Controlador
) {

    fun iniciar() {
        var executando = true

        while (executando) {
            exibirMenuPrincipal()
            val opcao = input.lerInteiro("Escolha uma opcao: ")

            if (opcao !in 1..6) {
                view.exibir("[ERRO] Opcao invalida. Escolha entre 1 e 6.")
                continue
            }

            when (opcao) {
                1 -> controller.cadastrarAluno()
                2 -> controller.registrarAvaliacao()
                3 -> controller.consultarRelatorio()
                4 -> controller.verEstatisticas()
                5 -> controller.listarAlunos()
                6 -> {
                    view.exibir("Encerrando o sistema... Ate logo!")
                    executando = false
                }
            }
        }
    }

    private fun exibirMenuPrincipal() {
        view.exibir("\n+========================================+")
        view.exibir("|   SISTEMA ACADEMICO - MENU PRINCIPAL   |")
        view.exibir("+========================================+")
        view.exibir("| 1. Cadastrar aluno                     |")
        view.exibir("| 2. Registrar avaliacao                 |")
        view.exibir("| 3. Consultar relatorio                 |")
        view.exibir("| 4. Ver estatisticas                    |")
        view.exibir("| 5. Listar alunos                       |")
        view.exibir("| 6. Sair                                |")
        view.exibir("+========================================+")
    }
}
