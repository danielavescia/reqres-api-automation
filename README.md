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
- Criação de usuário
- Cenários de erro (404, validações, etc.)

---

## Configuração do projeto

O projeto utiliza um arquivo de configuração local para armazenar variáveis sensíveis, como a API Key.

1. Obtenha sua API Key em: https://app.reqres.in/api-keys
2. Copie o template disponível em `src/main/resources/config-example.properties`
3. Renomeie para `config.properties` e insira sua API Key


## Execução dos testes
 ``` mvn clean test ```

---

## Cenários de teste

### GET /users

**Casos de Sucesso**
| ID | Cenário | Resultado Esperado |
|---|---|---|
| CT01 | Listar usuários com sucesso | 200, response com dados da página solicitada |
| CT02 | Validar campos obrigatórios dos usuários | Cada usuário contém: id, email, first_name, last_name, avatar |
| CT03 | Validar schema da resposta | Response segue o contrato users-schema.json |
| CT04 | Validar consistência de paginação | page, per_page presentes e data.size() ≤ per_page |
| CT05 | Validar tempo de resposta | Resposta em menos de 2000ms |

**Casos de Erro**
| ID | Cenário | Resultado Esperado |
|---|---|---|
| CT06 | Página inexistente | 200, data vazio |
| CT07 | API Key ausente | 401, error: missing_api_key |
| CT08 | API Key inválida | 403, error: invalid_api_key |

---

### POST /register

**Casos de Sucesso**
| ID | Cenário | Resultado Esperado |
|---|---|---|
| CT09 | Registrar usuário com sucesso | 200, Content-Type JSON |
| CT10 | Validar schema da resposta | 200, response segue contrato do schema |
| CT11 | Token não deve ser vazio | 200, token presente e não vazio |

**Casos de Erro**
| ID | Cenário | Resultado Esperado |
|---|---|---|
| CT12 | Registrar sem API Key | 401, response contém campo error |
| CT13 | Registrar com API Key inválida | 403, response contém campo error |
| CT14 | Registrar sem password | 400, error: Missing password |
| CT15 | Registrar sem email | 400, error: Missing email or username |
| CT16 | Registrar com body vazio | 400, error: Missing email or username |
| CT17 | Registrar com e-mail em formato inválido | 400, error: Only defined users succeed registration |
| CT18 | Validar contrato do response de erro | 400, contém error não vazio, não contém id ou token |

 ## Documentação
 https://github.com/danielavescia/reqres-api-automation/wiki