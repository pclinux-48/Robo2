# Robo Marciano

Projeto Android em Kotlin que implementa três versões do robô Marciano:

- `Marciano Básico`
- `Marciano Avançado`
- `Marciano Premium`

O aplicativo permite selecionar a versão do robô na interface, conversar com ele, realizar operações matemáticas nas versões avançadas e usar uma ação personalizada na versão premium.

## Objetivo do projeto

Este projeto foi desenvolvido para atender ao enunciado da atividade, explorando:

- herança entre classes
- interface para ação personalizada
- modularização do código
- organização em camadas simples
- uso de Kotlin em um app Android executável no simulador

## Funcionalidades

### 1. Marciano Básico

Responde frases conforme as regras:

- pergunta: `Certamente`
- grito: `Opa! Calma aí!`
- pergunta com grito: `Relaxa, eu sei o que estou fazendo!`
- frase com a palavra `eu`: `A responsabilidade é sua`
- silêncio: `Não me incomode`
- qualquer outro caso: `Tudo bem, como quiser`

### 2. Marciano Avançado

Possui todas as funções do básico e também realiza operações matemáticas:

- `some`
- `subtraia`
- `multiplique`
- `divida`

Exemplos:

- `some 2 3`
- `subtraia 10 4`
- `multiplique 5 6`
- `divida 20 2`

Resposta esperada:

- `Essa eu sei: <resultado>`

### 3. Marciano Premium

Possui todas as funções do avançado e também aceita o comando `agir`.

Quando a frase contém `agir`, o robô:

- responde `É pra já!`
- executa a ação personalizada definida por interface

No projeto atual, a ação personalizada escolhida foi `RelatorioEspacialAcao`, que:

- registra o texto da missão
- conta palavras
- conta vogais
- inverte o texto
- gera um código marciano

## Estrutura de versões

A hierarquia principal do projeto é:

```text
Marciano
  └── MarcianoAvancado
        └── MarcianoPremium
```

Isso significa que:

- o `MarcianoAvancado` herda o comportamento do `Marciano`
- o `MarcianoPremium` herda o comportamento do `MarcianoAvancado`

## Interface do aplicativo

A tela principal permite:

- escolher qual robô será usado
- visualizar descrição e orientações do robô selecionado
- ler o histórico da conversa
- digitar mensagens
- enviar pelo botão `Enviar`
- enviar pressionando `Enter`
- limpar a conversa com o botão `Limpar`
- encerrar a conversa digitando `FIM`

## Como executar

### Requisitos

- Android Studio
- JDK compatível
- Android SDK instalado
- emulador Android configurado

### Abrindo no Android Studio

1. Abra o Android Studio.
2. Escolha `Open`.
3. Selecione a pasta do projeto `Robo`.
4. Aguarde o Gradle sincronizar.
5. Escolha um dispositivo virtual.
6. Execute o app.

### Executando pela linha de comando

Na pasta do projeto:

```bash
./gradlew assembleDebug
```

Para rodar os testes:

```bash
./gradlew testDebugUnitTest
```

## Exemplos de uso

### Frases normais

- `Olá Marciano`
- `Você está bem?`
- `EU JÁ DISSE!`
- `Hoje eu resolvo isso`

### Operações matemáticas

Use apenas no `Marciano Avançado` ou `Marciano Premium`:

- `some 3 4`
- `subtraia 10 3`
- `multiplique 4 5`
- `divida 12 3`

### Ação personalizada

Use apenas no `Marciano Premium`:

- `agir explorar marte`
- `agir abrir compartimento`
- `agir analisar solo vermelho`

### Encerramento

- `FIM`

## Testes

O projeto possui testes unitários cobrindo:

- respostas do robô básico
- operação matemática no avançado
- operação matemática no premium
- ação personalizada no premium

Arquivo de testes:

- [MarcianoTest](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/test/java/com/example/robomarciano/MarcianoTest.kt)

## Principais arquivos

- [MainActivity](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/MainActivity.kt)
- [Marciano](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/Marciano.kt)
- [MarcianoAvancado](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/MarcianoAvancado.kt)
- [MarcianoPremium](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/MarcianoPremium.kt)
- [AcaoPersonalizada](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/AcaoPersonalizada.kt)
- [RelatorioEspacialAcao](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/RelatorioEspacialAcao.kt)
- [TipoRobo](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/TipoRobo.kt)
- [activity_main.xml](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/res/layout/activity_main.xml)

## Documentação complementar

Além deste `README`, o projeto possui um manual separado com a explicação de cada arquivo principal e sua relação com o funcionamento do sistema.

## Inovacoes realizadas

Nas ultimas atualizacoes, o aplicativo recebeu melhorias funcionais e visuais para deixá-lo mais completo e mais aderente ao enunciado da atividade.

### Melhorias de interface

- tela de abertura (`Splash Screen`) antes da tela principal
- tela de resposta separada da tela de entrada
- identidade visual com tema inspirado em ambiente marciano
- icone proprio do aplicativo
- reorganizacao da area inferior para melhorar a leitura em telas menores

### Melhorias de navegacao

- retorno da tela de resposta para a tela principal com o campo de mensagem limpo
- suporte ao botao voltar do Android mantendo o fluxo correto
- abertura direta da tela principal apos a splash

### Melhorias funcionais

- historico de comandos clicavel para reenviar mensagens ao robo
- persistencia do historico entre aberturas do aplicativo usando `SharedPreferences`
- tela intermediaria para montagem guiada de operacoes matematicas
- integracao da tela matematica com os robos `Marciano Avançado` e `Marciano Premium`

### Validacao

As melhorias foram verificadas com:

- compilacao do aplicativo com `assembleDebug`
- instalacao no emulador com `installDebug`
- execucao no simulador Android
- testes unitarios com `testDebugUnitTest`
