# Lactare Connect API

API REST do Lactare Connect, solução para conectar nutrizes, bancos de leite humano (BLHs) e gestores. O projeto representa o fluxo definido nas Sprints 1 e 2: quiz de elegibilidade, matching com BLH, agendamento, registro de doação, impacto social, campanhas e priorização por score de IA.

## Tecnologias

- Java 21
- Spring Boot 4.0.0 gerado pelo Spring Initializr
- Spring Web MVC
- Spring Data JPA
- Bean Validation
- H2 persistente em arquivo
- Springdoc OpenAPI/Swagger
- Maven e Docker

## Arquitetura

O projeto segue a separação de responsabilidades usada no projeto de referência:

```text
src/main/java/br/com/lactare/connect
├── config          # OpenAPI e carga inicial de dados
├── controller      # endpoints REST versionados
├── dto             # contratos de entrada e saída
├── entity          # entidades JPA persistidas
├── exception       # exceções e tratamento global
├── repository      # acesso ao banco via Spring Data
└── service         # regras de negócio e transações
```

Os controllers não acessam repositories diretamente. As entidades persistidas também não são expostas nas respostas: os endpoints usam DTOs.

## Execução com Docker — recomendada

Requisitos: Docker Desktop instalado e em execução.

Na raiz do projeto:

```bash
docker compose up --build
```

A API ficará disponível em `http://localhost:8080`.

Para interromper os containers:

```bash
docker compose down
```

Para interromper e remover também o volume de dados:

```bash
docker compose down -v
```

O volume `lactare-data` mantém o banco H2 entre reinicializações normais. Use `down -v` somente quando quiser começar com o banco vazio.

## Execução local

Requisitos: JDK 21 e Maven 3.9+.

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Alternativamente, execute `mvn spring-boot:run` caso o Maven esteja instalado.

## Documentação e banco

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Console H2: http://localhost:8080/h2-console

Dados de acesso ao H2:

```text
JDBC URL: jdbc:h2:file:./data/lactare
Usuário: sa
Senha: (vazia)
```

O banco é criado/atualizado automaticamente na inicialização. A aplicação insere dados demonstrativos apenas quando o banco está vazio.

## Principais endpoints

Todos os endpoints da API estão versionados com `/api/v1`.

| Recurso | Rotas principais |
|---|---|
| Nutrizes | `GET/POST /api/v1/nutrizes`, `GET/PUT/DELETE /api/v1/nutrizes/{id}` |
| Impacto da nutriz | `GET /api/v1/nutrizes/{id}/impacto` |
| BLHs | `GET/POST /api/v1/blhs`, `GET/PUT/DELETE /api/v1/blhs/{id}` |
| Quiz | `GET/POST /api/v1/quiz-respostas`, `GET/DELETE /api/v1/quiz-respostas/{id}` |
| Agendamentos | `GET/POST /api/v1/agendamentos`, `GET/PUT/DELETE /api/v1/agendamentos/{id}` |
| Status do agendamento | `PATCH /api/v1/agendamentos/{id}/status?status=CONFIRMADO` |
| Doações | `GET/POST /api/v1/doacoes`, `GET/DELETE /api/v1/doacoes/{id}` |
| Campanhas | `GET/POST /api/v1/campanhas`, `GET/PUT/DELETE /api/v1/campanhas/{id}` |
| Disparo | `POST /api/v1/campanhas/{id}/disparar` |
| IA preditiva | `GET/POST /api/v1/ia-preditiva`, `GET/PUT/DELETE /api/v1/ia-preditiva/{id}` |
| Dashboard | `GET /api/v1/dashboard` |

Filtros disponíveis:

```text
GET /api/v1/nutrizes?status=ATIVA
GET /api/v1/blhs?cidade=São Paulo
GET /api/v1/quiz-respostas?nutrizId=1
GET /api/v1/agendamentos?nutrizId=1
GET /api/v1/doacoes?nutrizId=1
GET /api/v1/ia-preditiva?prioridade=ALTA
```

## Exemplo de fluxo

Criar nutriz:

```bash
curl -X POST http://localhost:8080/api/v1/nutrizes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Oliveira",
    "telefone": "11977776666",
    "email": "maria@example.com",
    "cidade": "São Paulo",
    "estado": "SP",
    "semanasPosParto": 5,
    "consentimentoLgpd": true
  }'
```

Registrar resposta do quiz:

```bash
curl -X POST http://localhost:8080/api/v1/quiz-respostas \
  -H "Content-Type: application/json" \
  -d '{
    "nutrizId": 1,
    "pergunta": "Você está amamentando atualmente?",
    "resposta": "Sim, estou amamentando",
    "elegivel": true
  }'
```

Criar agendamento:

```bash
curl -X POST http://localhost:8080/api/v1/agendamentos \
  -H "Content-Type: application/json" \
  -d '{
    "nutrizId": 1,
    "blhId": 1,
    "dataHora": "2030-06-15T10:30:00",
    "tipoColeta": "BLH",
    "observacoes": "Primeira coleta"
  }'
```

Registrar doação:

```bash
curl -X POST http://localhost:8080/api/v1/doacoes \
  -H "Content-Type: application/json" \
  -d '{
    "nutrizId": 1,
    "blhId": 1,
    "volumeMl": 120,
    "dataDoacao": "2026-08-15",
    "bebesBeneficiados": 2
  }'
```

## Validação e erros

Os dados recebidos são validados com Bean Validation. Respostas inválidas retornam `422` com a lista de campos e mensagens. Também são tratados:

- `400` para JSON malformado ou parâmetros inválidos;
- `404` para recursos inexistentes;
- `409` para conflitos de regra de negócio ou integridade do banco;
- `500` para erros inesperados.

## Testes

Executar os testes:

```bash
./mvnw test
```

Gerar o pacote:

```bash
./mvnw clean package
```

## Observações de escopo

As integrações reais com WhatsApp Business API, geocodificação e motor externo de IA são representadas nesta entrega por endpoints e dados persistidos internos. Isso permite demonstrar o fluxo REST completo e mantém a API preparada para futuras integrações sem alterar o domínio definido nas Sprints 1 e 2.
