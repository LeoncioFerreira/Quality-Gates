# Arquitetura do sistema

O sistema utiliza Model-View-Controller (MVC) e mantém os dados apenas durante a execução.

## Model

Contém os dados, as validações e as regras acadêmicas. Essa camada inclui entidades, serviços e repositórios.

## View

Recebe dados pelo terminal e apresenta os resultados. A View não calcula médias nem acessa diretamente o armazenamento.

## Controller

Interpreta as ações recebidas da View e coordena os serviços do Model. O Controller não implementa regras acadêmicas.

## Regras acadêmicas

- Cada avaliação possui duas notas.
- Cada nota deve estar entre 0 e 10.
- Média maior ou igual a 7 representa aprovação.
- Média maior ou igual a 4 e menor que 7 representa recuperação.
- Média menor que 4 representa reprovação.
- Os dados permanecem somente em memória durante a execução.

## Contratos iniciais

- `AlunoRepository`: salva, busca e lista alunos.
- `CalculadoraAcademica`: calcula a média e determina a situação.
- `Entrada`: lê textos, inteiros e números decimais.
- `Saida`: apresenta mensagens.
