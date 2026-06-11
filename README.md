# API Test Automation - ReqRes + RestAssured

* Projeto de automação de testes de API utilizando **RestAssured**, **TestNG** e **Hamcrest**, com foco na API pública do ReqRes: https://reqres.in/
* **Report Allure:** https://danielavescia.github.io/reqres-api-automation/
---

## Tecnologias utilizadas

- Java 17+
- Maven
- RestAssured
- TestNG
- Hamcrest
- Jackson (serialização/desserialização JSON)
- Allure Reports

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

## Estratégia de Testes

Os testes estão organizados por **tipo de validação** e classificados como:

* **Happy Path**: fluxo esperado
* **Edge Cases**: limites e variações
* **Negative Cases**: erros e validações

---

## GET /users

### Contrato (Schema & Estrutura)

| ID   | Tipo       | Caso de Teste                            | Resultado Esperado                                 |
| ---- | ---------- | ---------------------------------------- | -------------------------------------------------- |
| CT01 | Happy Path | Validar schema da resposta               | Response segue users-schema.json                   |

---

### Regras de Negócio

| ID   | Tipo       | Caso de Teste                     | Resultado Esperado                 |
| ---- | ---------- | --------------------------------- | ---------------------------------- |
| CT02 | Happy Path | Listar usuários com sucesso       | 200, dados retornados corretamente |
| CT03 | Edge Case  | Validar consistência de paginação | data.size() ≤ per_page             |
| CT04 | Edge Case  | Página inexistente                | 200, data vazio                    |

---

### Segurança (Autenticação)

| ID   | Tipo     | Caso de Teste    | Resultado Esperado   |
| ---- | -------- | ---------------- | -------------------- |
| CT05 | Negative | API Key ausente  | 401, missing_api_key |
| CT06 | Negative | API Key inválida | 403, invalid_api_key |

---

### Performance

| ID   | Tipo      | Caso de Teste             | Resultado Esperado |
| ---- | --------- | ------------------------- | ------------------ |
| todos| Edge Case | Validar tempo de resposta | < 2000s           |

---

## POST /register

### Contrato (Schema & Estrutura)

| ID   | Tipo       | Caso de Teste              | Resultado Esperado                |
| ---- | ---------- | -------------------------- | --------------------------------- |
| CT07 | Happy Path | Validar schema da resposta | Response conforme contrato        |
| CT08 | Negative   | Validar contrato de erro   | Contém error, não contém id/token |

---

### Regras de Negócio

| ID   | Tipo       | Caso de Teste                 | Resultado Esperado      |
| ---- | ---------- | ----------------------------- | ----------------------- |
| CT09 | Happy Path | Registrar usuário com sucesso | 200, usuário registrado |

---

### Segurança (Autenticação)

| ID   | Tipo     | Caso de Teste                  | Resultado Esperado |
| ---- | -------- | ------------------------------ | ------------------ |
| CT11 | Negative | Registrar sem API Key          | 401, error         |
| CT12 | Negative | Registrar com API Key inválida | 403, error         |

---

### Validação de Entrada (Negative Cases)

| ID   | Tipo     | Caso de Teste          | Resultado Esperado                           |
| ---- | -------- | ---------------------- | -------------------------------------------- |
| CT13 | Negative | Registrar sem password | 400, Missing password                        |
| CT14 | Negative | Registrar sem email    | 400, Missing email                           |
| CT15 | Negative | Body vazio             | 400, Missing email                           |
| CT16 | Negative | Email inválido         | 400, Only defined users succeed registration |

---

