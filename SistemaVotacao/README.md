# Guia da Aplicação

## Requisitos de sistema

### Dependências

*Todas estas dependências podem ser encontradas no [pom.xml](pom.xml)*

- Spring JPA
- Spring Test
- Spring Validation
- Spring Web
- H2 Database
- Selenium Java
- Selenium API
- Selenium Edge Driver

### Informações importantes

1. > O sistema possui um controle de horário, portanto não se pode votar a qualquer hora, apenas entre as 18 horas e as 11 horas do dia seguinte.

2. > Cada pessoa pode votar apenas uma vez por dia, e essa validação é feita usando o nome, portanto, recomenda-se colocar o nome completo.

3. > Os testes de selenium utilizam a aplicação real, com dados pré populados, encontrados no [data.sql](src/main/resources/data.sql), portanto, 
   > a mesma deve estar rodando antes de iniciar os testes.

4. > Pelos motivos citados no item anterior, os testes de selenium irão falhar caso se tente votar fora do horário, isto é, entre as 11 e 18 horas.
 
5. > O selenium requer um executável do webdriver do navegador que será utilizado para os testes, que geralmente se encontra numa pasta pessoal, 
   > entretanto, para facilitar a execução, o mesmo foi incluído no projeto: [EdgeWebDriver](src/main/resources/msedgedriver.exe)
   
6. > A aplicação utiliza a porta padrão do localhost, que geralmente é 8080, porém, pode variar entre máquinas.

7. > Todos os funcionários e votos são apagados do banco de dados todos os dias às 18 horas, apenas os restaurantes permanecem, 
   > devido à necessidade de se controlar a data da última visita à cada um.
   
## [Informações database](../SistemaVotacao/src/main/resources/application.properties)

- #### [Path do database console (pode variar)](http://localhost:8080/h2)
- #### URL do datasource: *jdbc:h2:mem:dbVotacao*
- #### Usuário: *sa*
- #### Senha: *Nenhuma*

## Métodos HTTP da API

> Todos os métodos HTTP trabalham com o formato JSON.

### Votos

- #### [Incluir Voto (POST)](http://localhost:8080/votos)

- #### [Listar Votos (GET)](http://localhost:8080/votos)

- #### [Buscar Estado Votação (GET)](http://localhost:8080/votos/estado)

## Exemplos JSON de Entrada

### Votos(POST)

```
{
    "pessoa": {
        "nomePessoa" : "Lucas Lopes"
    },
    "restaurante": {
        "nomeRestaurante" : "Restaurante Favorito"
    }
}
```

```
{
    "pessoa": {
        "nomePessoa" : "João Silva"
    },
    "restaurante": {
        "nomeRestaurante" : "Restaurante 2"
    }
}
```

```
{
    "pessoa": {
        "nomePessoa" : "Alexandre Martins"
    },
    "restaurante": {
        "nomeRestaurante" : "Restaurante 3"
    }
}
```