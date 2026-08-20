# Quality Gates em um projeto Kotlin

[![Language: Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)
[![Build: Gradle](https://img.shields.io/badge/Build-Gradle-02303A.svg?logo=gradle)](https://gradle.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> **Acesse a documentação completa dos nossos experimentos e análises na [Página Inicial da Wiki (Home)](https://github.com/LeoncioFerreira/Quality-Gates/wiki/Home).** Toda a pesquisa e o passo a passo da implantação estão reunidos lá!

---

## 1. A Equipe

| Nome | GitHub |
| :--- | :--- |
| André Wesley Barbosa Rodrigues Filho | [@awesleyy](https://github.com/awesleyy) |
| Leôncio Ferreira Flores Neto | [@LeoncioFerreira](https://github.com/LeoncioFerreira) |
| Paulo Gabriel Leite Landim | [@LandimPG](https://github.com/LandimPG) |
| Pedro Kauan Cardoso da Silva | [@DevPKauan01](https://github.com/DevPKauan01) |
| Ramona Vitória Clemente Cardoso | [@ramona-dev](https://github.com/ramona-dev) |
| Salomão Rodrigues Silva | [@salomaosilvaa](https://github.com/salomaosilvaa) |

---

## 2. O Projeto e o Contexto de Evolução

Este repositório documenta a evolução e implantação de um **Quality Gate** completo em um sistema acadêmico construído em Kotlin para a JVM.

Originalmente desenvolvido para a disciplina de Paradigmas de Programação (com código fonte e arquitetura originais documentados no repositório [Kotlin-Evolucao-da-Programacao-Orientada-a-Objetos-na-JVM](https://github.com/LeoncioFerreira/Kotlin-Evolucao-da-Programacao-Orientada-a-Objetos-na-JVM)), o projeto contava apenas com um pipeline básico de integração contínua (CI) que executava builds e testes unitários.

Neste repositório, o foco é 100% na **garantia de qualidade automática**. Elevamos a maturidade do pipeline integrando as seguintes ferramentas:

- **KtLint**: Padronização de estilo e formatação.
- **Detekt**: Análise estática, detecção de code smells e más práticas.
- **JaCoCo**: Verificação de cobertura mínima de testes automatizados.
- **SonarQube Cloud**: Dashboard centralizado para rastreabilidade de bugs, vulnerabilidades e dívida técnica.

---

## 3. Como Rodar as Verificações Locais

Antes de abrir uma Pull Request, você pode (e deve) executar localmente as ferramentas de qualidade configuradas no projeto. Certifique-se de ter o JDK 17+ instalado.

Na raiz do projeto, execute os comandos através do Gradle Wrapper:

- **Executar os testes automatizados:**
  ```bash
  ./gradlew test
  ```
- **Verificar a formatação do código (KtLint):**
  ```bash
  ./gradlew ktlintCheck
  ```
- **Rodar a análise estática (Detekt):**
  ```bash
  ./gradlew detekt
  ```
- **Verificar a cobertura de testes (JaCoCo):**
  ```bash
  ./gradlew jacocoTestCoverageVerification
  ```

---

## 4. Entregas e Pesquisa (Wiki)

Nossa equipe pesquisou a fundo os fundamentos e configurou, na prática, as ferramentas para bloquear Pull Requests inadequadas. O detalhamento do que cada integrante produziu pode ser lido na nossa Wiki.

| Integrante | Tema | Acesso à Wiki |
| :--- | :--- | :--- |
| **André Wesley Barbosa Rodrigues Filho** | Fundamentos, CI/CD, DevOps, QA e revisão humana | [Acessar página](https://github.com/LeoncioFerreira/Quality-Gates/wiki/Fundamentos-de-Quality-Gates) |
| **Leôncio Ferreira Flores Neto** | Ferramentas, integração, SonarQube e reprodução técnica | [Ferramentas e integração](https://github.com/LeoncioFerreira/Quality-Gates/wiki/Ferramentas-e-integracao-do-Quality-Gate)<br>[SonarQube e Cloud](https://github.com/LeoncioFerreira/Quality-Gates/wiki/SonarQube-e-SonarQube-Cloud)<br>[Reprodução](https://github.com/LeoncioFerreira/Quality-Gates/wiki/Reproducao-do-experimento) |
| **Pedro Kauan Cardoso da Silva** | Testes automatizados e cobertura JaCoCo | [Acessar página](https://github.com/LeoncioFerreira/Quality-Gates/wiki/Testes-automatizados-e-cobertura) |
| **Salomão Rodrigues Silva** | Métricas e análise estática (Detekt) | [Acessar página](https://github.com/LeoncioFerreira/Quality-Gates/wiki/Metricas-e-analise-estatica) |
| **Paulo Gabriel Leite Landim** | Qualidade e segurança de software | [Acessar página](https://github.com/LeoncioFerreira/Quality-Gates/wiki/Qualidade-e-seguranca-de-software) |
| **Ramona Vitória Clemente Cardoso** | Experimento: PR reprovada, corrigida e aprovada | [Acessar página](https://github.com/LeoncioFerreira/Quality-Gates/wiki/Experimento-PR-bloqueada-e-aprovada) |

---

## 5. Artefatos Finais

- **A documentação central (Wiki):** [Nossos Experimentos e Conclusões](https://github.com/LeoncioFerreira/Quality-Gates/wiki)
- **Repositório da arquitetura base (Paradigmas):** [Acessar](https://github.com/LeoncioFerreira/Kotlin-Evolucao-da-Programacao-Orientada-a-Objetos-na-JVM)
