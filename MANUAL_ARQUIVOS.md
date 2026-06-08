# Manual Dos Arquivos

Este documento explica como cada arquivo principal do projeto funciona e qual é sua relação com o restante do programa.

## Visão geral da organização

O projeto foi dividido em quatro grupos principais:

- configuração do projeto Android
- lógica dos robôs
- interface visual
- testes

## 1. Arquivos de configuração do projeto

### [settings.gradle.kts](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/settings.gradle.kts)

Responsabilidade:

- define o nome do projeto
- registra o módulo `app`
- configura repositórios de plugins e dependências

Relação com o programa:

- sem esse arquivo, o Gradle não sabe como montar o projeto Android

### [build.gradle.kts](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/build.gradle.kts)

Responsabilidade:

- define os plugins principais do projeto

Relação com o programa:

- prepara o ambiente global para o módulo Android funcionar

### [gradle.properties](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/gradle.properties)

Responsabilidade:

- armazena propriedades do Gradle e do Android

Relação com o programa:

- influencia o build, uso de memória, AndroidX e compatibilidade do SDK

### [app/build.gradle.kts](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/build.gradle.kts)

Responsabilidade:

- define a configuração do módulo `app`
- informa `compileSdk`, `minSdk`, `targetSdk`
- declara dependências
- ativa `viewBinding`

Relação com o programa:

- é o arquivo que transforma o código Kotlin e os recursos XML em um aplicativo Android instalável

### [app/src/main/AndroidManifest.xml](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/AndroidManifest.xml)

Responsabilidade:

- registra a aplicação
- define a `MainActivity` como tela inicial

Relação com o programa:

- informa ao Android por onde o app deve começar quando for aberto

## 2. Arquivos da lógica do robô

### [Marciano.kt](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/Marciano.kt)

Responsabilidade:

- implementa a versão básica do robô
- contém as regras de resposta para:
  - pergunta
  - grito
  - pergunta com grito
  - frase com `eu`
  - silêncio
  - fala comum

Relação com o programa:

- é a base da hierarquia de robôs
- `MarcianoAvancado` herda dessa classe

### [MarcianoAvancado.kt](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/MarcianoAvancado.kt)

Responsabilidade:

- adiciona operações matemáticas ao robô
- trata comandos:
  - `some`
  - `subtraia`
  - `multiplique`
  - `divida`

Relação com o programa:

- herda todas as respostas do `Marciano`
- serve de base para o `MarcianoPremium`

### [AcaoPersonalizada.kt](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/AcaoPersonalizada.kt)

Responsabilidade:

- define a interface da ação personalizada

Relação com o programa:

- permite injetar um comportamento externo no `MarcianoPremium`
- atende ao requisito do enunciado de passar a ação por interface

### [MarcianoPremium.kt](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/MarcianoPremium.kt)

Responsabilidade:

- implementa a versão premium do robô
- detecta a palavra `agir`
- responde `É pra já!`
- executa a ação personalizada recebida no construtor

Relação com o programa:

- herda tudo do `MarcianoAvancado`
- portanto mantém respostas básicas e cálculos
- adiciona o comportamento especial do comando `agir`

### [RelatorioEspacialAcao.kt](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/RelatorioEspacialAcao.kt)

Responsabilidade:

- é a implementação concreta da interface `AcaoPersonalizada`
- transforma o texto após `agir` em um relatório com:
  - missão registrada
  - quantidade de palavras
  - quantidade de vogais
  - texto invertido
  - código marciano

Relação com o programa:

- é a ação usada atualmente pelo `MarcianoPremium`

### [TipoRobo.kt](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/TipoRobo.kt)

Responsabilidade:

- organiza as opções de robô disponíveis na interface
- define:
  - título
  - descrição
  - orientação
  - dica de entrada
  - fábrica de criação do robô

Relação com o programa:

- conecta a lógica dos robôs com a interface
- permite trocar entre `Básico`, `Avançado` e `Premium` sem duplicar código

## 3. Arquivos da interface

### [MainActivity.kt](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/MainActivity.kt)

Responsabilidade:

- é a tela principal do aplicativo
- controla o fluxo da conversa
- gerencia o seletor de robô
- interpreta o texto digitado
- atualiza o histórico
- envia mensagens pelo botão ou pela tecla `Enter`

Relação com o programa:

- é o ponto que une interface e lógica
- instancia o robô escolhido e chama `responda()`

Fluxo principal dentro da `MainActivity`:

1. mostra a tela de boas-vindas
2. usuário escolhe um tipo de robô
3. a activity cria a instância correta
4. usuário digita uma frase
5. a activity interpreta a entrada
6. o robô gera a resposta
7. a tela mostra a conversa no histórico

### [activity_main.xml](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/res/layout/activity_main.xml)

Responsabilidade:

- define a estrutura visual da tela principal

Elementos principais:

- card superior com boas-vindas e seletor do robô
- card com orientações e comandos
- card com histórico da conversa
- card fixo no rodapé com campo de entrada e botões

Relação com o programa:

- fornece os componentes que a `MainActivity` manipula

### [strings.xml](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/res/values/strings.xml)

Responsabilidade:

- armazena os textos exibidos na interface

Relação com o programa:

- centraliza os textos da aplicação
- facilita manutenção e futuras mudanças

### [themes.xml](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/res/values/themes.xml)

Responsabilidade:

- define o tema visual principal do app

Relação com o programa:

- controla a aparência base da aplicação Android

### [themes.xml (night)](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/res/values-night/themes.xml)

Responsabilidade:

- define o tema para modo noturno

Relação com o programa:

- garante compatibilidade visual quando o sistema estiver em tema escuro

## 4. Arquivos de teste

### [MarcianoTest.kt](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/test/java/com/example/robomarciano/MarcianoTest.kt)

Responsabilidade:

- valida o comportamento das classes principais

Cobertura atual:

- pergunta
- grito
- pergunta com grito
- frase com `eu`
- silêncio
- operação matemática no avançado
- operação matemática no premium
- ação personalizada no premium

Relação com o programa:

- ajuda a garantir que mudanças futuras não quebrem o comportamento esperado

## Relação entre os arquivos

### Relação da lógica

```text
Marciano
  └── MarcianoAvancado
        └── MarcianoPremium
              └── usa AcaoPersonalizada
                        └── implementada por RelatorioEspacialAcao
```

### Relação entre interface e lógica

```text
activity_main.xml
        ↓
MainActivity.kt
        ↓
TipoRobo.kt
        ↓
instancia o robô correto
        ↓
Marciano / MarcianoAvancado / MarcianoPremium
```

## Quais arquivos são mais importantes para apresentar

Se você for explicar o trabalho para o professor, os arquivos mais importantes são:

- [Marciano](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/Marciano.kt)
- [MarcianoAvancado](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/MarcianoAvancado.kt)
- [MarcianoPremium](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/MarcianoPremium.kt)
- [AcaoPersonalizada](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/AcaoPersonalizada.kt)
- [MainActivity](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/java/com/example/robomarciano/MainActivity.kt)
- [activity_main.xml](file:///Users/paulocesar/Estudo/Pos%20Graduação/Desenvolvimento%20Web%20e%20Mobile/Desenvolvimento%20App%20Moveis/Robo/app/src/main/res/layout/activity_main.xml)

## Sugestão de leitura

Se quiser entender o projeto de forma progressiva, leia nesta ordem:

1. `README.md`
2. `Marciano.kt`
3. `MarcianoAvancado.kt`
4. `AcaoPersonalizada.kt`
5. `MarcianoPremium.kt`
6. `RelatorioEspacialAcao.kt`
7. `TipoRobo.kt`
8. `MainActivity.kt`
9. `activity_main.xml`
10. `MarcianoTest.kt`
