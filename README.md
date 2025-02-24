# Estruturas de Dados - Projeto Final
> Projeto desenvolvido como trabalho final da disciplina de Estruturas de Dados do curso de Sistemas e Mídias Digitais da UFC.

## 📋 Sobre o Projeto

Este projeto é uma aplicação Java que implementa diferentes estruturas de dados estudadas durante a disciplina. O projeto utiliza o padrão de arquitetura MVC (Model-View-Controller) para organizar o código de forma clara e modular.

## 🚀 Tecnologias Utilizadas

- Java
- Gradle (Sistema de Build)

## 📁 Estrutura do Projeto

```
src/
├── main/java/org/edd/
│   ├── model/      # Classes de modelo e estruturas de dados
│   ├── view/       # Interface com o usuário
│   ├── controller/ # Lógica de controle da aplicação
│   └── Main.java   # Ponto de entrada da aplicação
```

## ⚙️ Pré-requisitos

Para executar este projeto, você precisará ter instalado em sua máquina:

- Java JDK 17.0.12
- Gradle 7.x ou superior

## ⬇️ Instalação dos pré-requisitos

Para instalar o Java JDK 17.0.12, siga os passos abaixo:

1.  **Acesse o link:** Oracle JDK 17 Downloads - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

2.  **Escolha a versão:** Selecione a versão que melhor atenda ao seu sistema operacional e baixe o arquivo de instalação.

3.  **Execute o arquivo de instalação:** Siga as instruções do instalador para completar a instalação.

4.  **Verifique a instalação:** Abra o seu terminal de comando e execute o seguinte comando:

    ```
    java --version
    ```

    Caso o comando retorne a versão do Java corretamente, significa que a instalação foi bem-sucedida.


Se houver algum problema com a instalação, verifique sua máquina e tente novamente. Após confirmar que tudo está funcionando corretamente, prossiga para a execução do seu sistema.

## 🔧 Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/joaomarcosmb/edd-db.git
```

2. Navegue até o diretório do projeto:
```bash
cd edd-db
```

3. Compile o projeto:
```bash
./gradlew build
```

4. Execute a aplicação:
```bash
./gradlew run
```

## 🔧 Como abrir o arquivo executavel

1. Clone o repositório:
```bash
git clone https://github.com/joaomarcosmb/edd-db.git
```

2. Navegue até o diretório do projeto:
```bash
cd edd-db
```

3. Navegue até a pasta do arquivo executavel:
```bash
cd out
```

4. Execute um dos seguintes comando no terminal:
```bash
java -cp edd-db.jar org.edd.Main
```
ou
```bash
java -jar edd-db.jav
```

## Recomendações

Recomendamos que o aquivo do projeto seja aberto em uma IDE de sua escolha, segue abaixo algumas opções:

- IntelliJ - https://www.jetbrains.com/pt-br/idea/download/
- Visual Studio Code - https://code.visualstudio.com/

## 📚 Estruturas de Dados Implementadas

- Lista encadeada
- Árvore AVL
- Pilhas
- Filas

## 👥 Autores

- [João Marcos Moura](https://github.com/joaomarcosmb) 
- [Davi Calô](https://github.com/DaviCalo)
