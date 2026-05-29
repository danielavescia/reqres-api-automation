# API Test Automation - ReqRes + RestAssured

Projeto de automação de testes de API utilizando **RestAssured**, **TestNG** e **Hamcrest**, com foco na API pública do ReqRes: https://reqres.in/

---

## Tecnologias utilizadas

- Java 17+
- Maven
- RestAssured
- TestNG
- Hamcrest
- Jackson (serialização/desserialização JSON)

---

## Objetivo do projeto

Validar endpoints da API ReqRes com testes automatizados, cobrindo:

* Users
- Listagem de usuários
- Busca de usuário por ID
- Criação de usuário
- Atualização de usuário (PUT/PATCH)
- Remoção de usuário
- Cenários de erro (404, validações, etc.)

---

## Configuração do projeto

O projeto utiliza um arquivo de configuração local para armazenar variáveis sensíveis, como API Key. O projeto conta com um template: config-example.properties. Insira sua api-key no campo e renomeie para o arquivo para config.properties.


## Execução dos testes
 ``` mvn clean test ```


 ## Casos de teste
 https://github.com/danielavescia/reqres-api-automation/wiki