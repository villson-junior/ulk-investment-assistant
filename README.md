# ulk investment assistant - CRUD de Usuários, Contas e Tickers

## Descrição

Este projeto é uma aplicação backend simples desenvolvida com Spring Boot, projetada para auxiliar
no controle de investimentos. Através dela, é possível cadastrar usuários, contas e associar tickers
a essas contas, permitindo o acompanhamento das quantidades adquiridas e seus preços atualizados.

### Funcionalidades

- **Cadastro de Usuários:** Criação de perfis de usuários que podem possuir diversas contas de
  investimento.
- **Cadastro de Contas:** Cada usuário pode ter uma ou mais contas, associadas a tickers.
- **Cadastro de Tickers:** Cada ticker pode ser associado a uma quantidade em uma conta específica
  de um usuário.
- **Consulta de Preços:** O preço do ticker é obtido de uma API externa utilizando OpenFeign.

### Tecnologias Utilizadas

- **Spring Boot:** Framework utilizado para construção da aplicação.
- **Spring Data JPA:** Para a persistência de dados no banco de dados MySQL.
- **MySQL:** Banco de dados relacional utilizado para armazenar os dados dos usuários, contas e
  tickers.
- **Lombok:** Biblioteca para redução de boilerplate code (como getters, setters, etc).
- **Spring Cloud OpenFeign:** Para consumo da API externa para consultar os preços dos ativos.

## Configurações de Ambiente

- **Java Version**: 21
- **IDE**: Eclipse, IntelliJ ou outro suporte ao Spring Boot

## Pré-requisitos

- **Docker**: Você precisa ter o [Docker](https://www.docker.com/get-started) instalado em sua
  máquina para rodar o banco de dados em contêineres.
- **Docker Compose**: Necessário para orquestrar múltiplos contêineres de uma vez.
  O [Docker Compose](https://docs.docker.com/compose/install/) é usado para definir os serviços no
  ambiente Docker.

## Dependências do Projeto

O `pom.xml` contém todas as dependências necessárias para o funcionamento da aplicação.

## Como Executar

1. Clone o repositório do projeto.
2. Inicie o banco de dados utilizando o docker-compose:

```console
   docker-compose up
```

3. Inicie o projeto

```console
   ./mvnw spring-boot:run
```