# 🌍 TerraOrbit — API de Monitoramento Agrícola Inteligente

API REST desenvolvida em **Java 21 + Spring Boot 4.0.6** para monitoramento inteligente de fazendas, sensores, incidentes climáticos e geração de recomendações via IA (Gaia AI). O projeto é composto por dois módulos independentes: a API principal e o microserviço de análise com LLM.

---

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Video Demo](#video-demonstração)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Configuração e Execução](#configuração-e-execução)
- [Autenticação](#autenticação)
- [Endpoints](#endpoints)
    - [Auth](#auth)
    - [Users](#users)
    - [Farms](#farms)
    - [Sensors](#sensors)
    - [Incidents](#incidents)
    - [Climate Alerts](#climate-alerts)
    - [AI Recommendations](#ai-recommendations)
- [Modelos de Dados](#modelos-de-dados)
- [HATEOAS](#hateoas)
- [Módulo Gaia AI](#módulo-gaia-ai)
- [Documentação Swagger](#documentação-swagger)
- [Integrantes](#integrantes)

---

## Visão Geral

O **TerraOrbit** é uma plataforma de monitoramento agrícola que permite:

- Cadastrar fazendas e vinculá-las a proprietários
- Monitorar sensores de temperatura e umidade em tempo real
- Registrar e acompanhar incidentes nas fazendas
- Receber alertas climáticos gerados automaticamente
- Gerar recomendações agrícolas inteligentes via LLM (Gaia AI), baseadas nas leituras dos sensores

---

## Video Demonstração
Você pode ver uma demonstração básica do funcionamento da API neste [link](https://youtu.be/pnGlyrtOQuY)

## Arquitetura

O sistema é dividido em dois serviços Spring Boot independentes que se comunicam via HTTP:

```
┌─────────────────────────────────────────┐        ┌──────────────────────────────┐
│         terraorbit (porta 8080)         │        │  terraorbit-gaia-ai (8081)   │
│                                         │        │                              │
│  Controllers → Services → Repositories │───────▶│  AiController                │
│  Spring Security (JWT)                  │  HTTP  │  OpenRouterService           │
│  HATEOAS                                │        │  OpenRouter API (LLM)        │
│  Oracle DB (JPA/Hibernate)              │        │                              │
└─────────────────────────────────────────┘        └──────────────────────────────┘
```

**Fluxo de geração de recomendação:**
1. Cliente chama `POST /recommendations/generate/{farmId}`
2. API principal busca sensores `ACTIVE` da fazenda no banco (por tipo: TEMPERATURE e HUMIDITY)
3. Calcula médias de temperatura e umidade
4. Se temperatura ≥ 30°C, cria automaticamente um `ClimateAlert` do tipo `DROUGHT`
5. Chama o microserviço Gaia AI com as médias
6. Gaia AI consulta o LLM via OpenRouter e retorna `riskLevel` + `recommendation`
7. API principal persiste e retorna o resultado

---

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.6 | Framework base |
| Spring Security | via Boot | Autenticação e autorização |
| Spring HATEOAS | via Boot | Hypermedia nos responses |
| Spring Data JPA | via Boot | Persistência |
| Spring WebFlux | via Boot | Cliente HTTP reativo (WebClient) |
| Hibernate | via Boot | ORM |
| Oracle Database | OJDBC 11 | Banco de dados principal |
| jjwt | 0.12.6 | Geração e validação de tokens JWT |
| Lombok | via Boot | Redução de boilerplate |
| SpringDoc OpenAPI | 3.0.2 | Documentação Swagger |
| OpenRouter API | — | Gateway para LLM (Gaia AI) |

---

## Estrutura do Projeto

```
java/
├── src/main/java/br/com/fiap/terraorbit/
│   ├── assembler/              # Conversão de entidades para EntityModel (HATEOAS)
│   │   ├── AIRecommendationAssembler.java
│   │   ├── ClimateAlertAssembler.java
│   │   ├── FarmAssembler.java
│   │   ├── IncidentAssembler.java
│   │   ├── SensorAssembler.java
│   │   └── UserAssembler.java
│   ├── client/
│   │   └── GaiaAiClient.java  # Chamada HTTP para o microserviço de IA
│   ├── config/
│   │   ├── CorsConfig.java    # CORS liberado para todas as origens
│   │   └── WebClientConfig.java
│   ├── controller/            # Camada de entrada HTTP
│   │   ├── AiRecommendationController.java
│   │   ├── AuthController.java
│   │   ├── ClimateAlertController.java
│   │   ├── FarmController.java
│   │   ├── IncidentController.java
│   │   ├── SensorController.java
│   │   └── UserController.java
│   ├── dto/
│   │   ├── auth/              # LoginDTO, RegisterDTO
│   │   ├── request/           # FarmNewRequest, SensorNewRequest, etc.
│   │   └── response/          # FarmDTO, SensorDTO, JwtResponse, etc.
│   ├── entity/                # Entidades JPA
│   │   ├── AiRecommendation.java
│   │   ├── ClimateAlert.java
│   │   ├── Farm.java
│   │   ├── Incident.java
│   │   ├── RISKLEVEL.java     # Enum: LOW, MEDIUM, HIGH
│   │   ├── Sensor.java
│   │   ├── USERROLE.java      # Enum: USER, ADMIN
│   │   └── User.java
│   ├── exception/
│   │   └── EmailAlreadyExists.java
│   ├── repository/            # Interfaces JpaRepository
│   ├── security/              # JWT Filter, JwtService, SecurityConfig
│   └── service/               # Lógica de negócio
│
├── terraorbit-gaia-ai/        # Microserviço de IA (Gradle, porta 8081)
│   └── src/main/java/br/com/fiap/terraorbitgaiaai/
│       ├── controller/AiController.java
│       ├── service/OpenRouterService.java
│       └── dto/ (ClimateRequest, AiAnalysisResponse)
│
├── pom.xml
└── src/main/resources/
    └── application.yaml
```

---

## Configuração e Execução

### Pré-requisitos

- Java 21+
- Maven 3.9+
- Gradle 8+ (para o módulo gaia-ai)
- Acesso ao banco Oracle (ou configurar outro banco compatível com JPA)
- Chave de API do [OpenRouter](https://openrouter.ai)

### Variáveis de ambiente

Configure as seguintes variáveis antes de iniciar os serviços:

```bash
# Banco de dados (application.yaml usa essas variáveis)
export DB_USER=seu_usuario
export DB_PASS=sua_senha

# JWT (configure no JwtService)
export JWT_SECRET=sua_chave_secreta_com_32_chars_minimo

# Gaia AI (application.yaml do módulo gaia-ai)
export API_KEY_OPENROUTER=sk-or-xxxxxxxxxxxxxxxx
```

### Executar a API principal (porta 8080)

```bash
cd java/
./mvnw spring-boot:run
```

### Executar o microserviço Gaia AI (porta 8081)

```bash
cd java/terraorbit-gaia-ai/
./gradlew bootRun
```

> **Importante:** O microserviço Gaia AI **deve estar rodando** antes de usar o endpoint `POST /recommendations/generate/{farmId}`. A API principal se comunica com ele em `http://localhost:8081/ai/analyze`.

### Banco de dados

O projeto usa `ddl-auto: update` — o Hibernate cria e atualiza as tabelas automaticamente ao iniciar. As tabelas criadas são:

| Tabela | Descrição |
|---|---|
| `TO_USERS` | Usuários da plataforma |
| `TO_FARMS` | Fazendas |
| `TO_SENSORS` | Sensores das fazendas |
| `TO_INCIDENTS` | Incidentes registrados |
| `TO_CLIMATE_ALERTS` | Alertas climáticos gerados pela IA |
| `TO_AI_RECOMMENDATIONS` | Recomendações geradas pela Gaia AI |

---

## Autenticação

A API utiliza **JWT (JSON Web Token)** com Spring Security. Todos os endpoints — exceto `/auth/**`, `/swagger-ui/**` e `/v3/**` — exigem autenticação.

### Como usar

1. Registre um usuário em `POST /auth/register` ou faça login em `POST /auth/login`
2. Copie o token retornado no campo `token`
3. Inclua o token no header de todas as requisições:

```
Authorization: Bearer <seu_token_aqui>
```

**Validade do token:** 24 horas (86400000 ms)

**Roles disponíveis:**
- `USER` — acesso padrão a todos os endpoints autenticados
- `ADMIN` — acesso adicional às rotas `/admin/**`

---

## Endpoints

Base URL: `http://localhost:8080`

> Todos os endpoints (exceto `/auth`) exigem o header `Authorization: Bearer <token>`.
> Todos os responses de listagem seguem o padrão paginado do Spring HATEOAS com links de navegação.

---

### Auth

| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| `POST` | `/auth/register` | Registra novo usuário | ❌ Público |
| `POST` | `/auth/login` | Autentica usuário | ❌ Público |

#### `POST /auth/register`

**Request body:**
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "minhasenha123"
}
```

**Validações:** `name` obrigatório, `email` válido, `password` mínimo 3 caracteres.

**Response `201 Created`:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

#### `POST /auth/login`

**Request body:**
```json
{
  "email": "joao@email.com",
  "password": "minhasenha123"
}
```

**Response `200 OK`:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Users

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/users` | Lista todos os usuários (paginado) |
| `GET` | `/users/{id}` | Busca usuário por ID |
| `PUT` | `/users/{id}` | Atualiza nome e/ou senha |
| `DELETE` | `/users/{id}` | Remove usuário |

> Usuários são criados exclusivamente via `POST /auth/register`.

#### `GET /users`

**Parâmetros de paginação (query):** `page`, `size`, `sort`

**Response `200 OK`:**
```json
{
  "_embedded": {
    "users": [
      {
        "id": 1,
        "name": "João Silva",
        "email": "joao@email.com",
        "role": "USER",
        "createdAt": "2025-06-01T10:00:00",
        "_links": { "self": { "href": "http://localhost:8080/users/1" } }
      }
    ]
  },
  "_links": { "links":  "..."},
  "page": { "size": 20, "totalElements": 1, "totalPages": 1, "number": 0 }
}
```

---

#### `GET /users/{id}`

**Response `200 OK`:** objeto do usuário com links HATEOAS.
**Response `404 Not Found`:** se o ID não existir.

---

#### `PUT /users/{id}`

**Request body** (campos opcionais — envie apenas o que deseja alterar):
```json
{
  "name": "João Atualizado",
  "password": "novasenha456"
}
```

**Response `200 OK`:** usuário atualizado com links HATEOAS.

---

#### `DELETE /users/{id}`

**Response `200 OK`** — sem corpo.
**Response `404 Not Found`** — se o ID não existir.

---

### Farms

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/farms` | Lista fazendas (paginado, filtrável por userId) |
| `GET` | `/farms/{id}` | Busca fazenda por ID |
| `POST` | `/farms` | Cria nova fazenda |
| `PUT` | `/farms/{id}` | Atualiza fazenda |
| `DELETE` | `/farms/{id}` | Remove fazenda |

#### `GET /farms`

**Query params opcionais:**
- `userId` — filtra fazendas de um usuário específico
- `page`, `size`, `sort` — paginação

---

#### `POST /farms`

**Response `201 Created`.**

**Request body:**
```json
{
  "name": "Fazenda São João",
  "location": "Ribeirão Preto, SP",
  "farmSizeHectares": 150.5,
  "ownerId": 1
}
```

**Response `201 Created`:**
```json
{
  "id": 1,
  "farmName": "Fazenda São João",
  "location": "Ribeirão Preto, SP",
  "farmSizeHectares": 150.5,
  "ownerId": 1,
  "createAt": "2025-06-01T10:00:00",
  "_links": {
    "self": { "href": "http://localhost:8080/farms/1" },
    "all-farms": { "href": "http://localhost:8080/farms" },
    "user": { "href": "http://localhost:8080/users/1" },
    "alerts": { "href": "http://localhost:8080/alerts?farmId=1" },
    "recommendations": { "href": "http://localhost:8080/recommendations?farmId=1" }
  }
}
```

---

#### `PUT /farms/{id}`

**Request body:** mesmo formato do `POST`. Substitui todos os campos.

---

### Sensors

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/sensors` | Lista sensores (paginado, filtrável por farmId) |
| `GET` | `/sensors/{id}` | Busca sensor por ID |
| `POST` | `/sensors` | Cria novo sensor |
| `PUT` | `/sensors/{id}` | Atualiza sensor |
| `DELETE` | `/sensors/{id}` | Remove sensor |

#### `POST /sensors`

**Request body:**
```json
{
  "sensorName": "Sensor Temp Norte",
  "sensorType": "TEMPERATURE",
  "sensorStatus": "ACTIVE",
  "lastReading": 28.5,
  "farmId": 1
}
```

**Valores recomendados para `sensorType`:** `TEMPERATURE`, `HUMIDITY`

**Valores recomendados para `sensorStatus`:** `ACTIVE`, `INACTIVE`

> Apenas sensores com `sensorStatus = "ACTIVE"` são considerados na geração de recomendações.

---

### Incidents

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/incidents` | Lista incidentes (paginado, filtrável por farmId) |
| `GET` | `/incidents/{id}` | Busca incidente por ID |
| `POST` | `/incidents` | Registra novo incidente |
| `PUT` | `/incidents/{id}` | Atualiza incidente |
| `DELETE` | `/incidents/{id}` | Remove incidente |

#### `POST /incidents`

**Request body:**
```json
{
  "incidentType": "PRAGA",
  "incidentDescription": "Infestação de cigarrinhas detectada no setor B",
  "incidentStatus": "ABERTO",
  "farmId": 1
}
```

> `incidentDate` é preenchido automaticamente com `LocalDateTime.now()` no momento do cadastro.

---

### Climate Alerts

Alertas climáticos são **gerados automaticamente** pela API quando o endpoint de recomendação detecta temperatura média ≥ 30°C nos sensores ativos. Não é possível criá-los manualmente.

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/alerts` | Lista alertas (paginado, filtrável por farmId) |
| `GET` | `/alerts/{id}` | Busca alerta por ID |
| `DELETE` | `/alerts/{id}` | Remove alerta |

#### Exemplo de alerta gerado automaticamente:
```json
{
  "id": 1,
  "alertType": "DROUGHT",
  "severity": "HIGH",
  "message": "Potential drought detected",
  "alertDate": "2025-06-01T14:30:00",
  "farmId": 1,
  "_links": { "links": "..." }
}
```

---

### AI Recommendations

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/recommendations` | Lista recomendações (paginado, filtrável por farmId) |
| `GET` | `/recommendations/{id}` | Busca recomendação por ID |
| `POST` | `/recommendations/generate/{farmId}` | Gera nova recomendação via Gaia AI |
| `DELETE` | `/recommendations/{id}` | Remove recomendação |

#### `POST /recommendations/generate/{farmId}`

Dispara o fluxo completo de análise para a fazenda informada:

1. Busca sensores `ACTIVE` do tipo `TEMPERATURE` e `HUMIDITY` vinculados à fazenda
2. Calcula médias de leitura
3. Se temperatura média ≥ 30°C → cria `ClimateAlert` do tipo `DROUGHT / HIGH`
4. Envia médias ao microserviço Gaia AI (`localhost:8081`)
5. Persiste e retorna a recomendação gerada

> **Pré-requisito:** o microserviço `terraorbit-gaia-ai` deve estar em execução.

**Response `200 OK`:**
```json
{
  "id": 1,
  "recommendation": "Irrigate crops immediately. High temperature may cause soil dehydration.",
  "risklevel": "HIGH",
  "farmId": 1,
  "generatedAt": "2025-06-01T14:30:00",
  "_links": {
    "self": { "href": "http://localhost:8080/recommendations/1" },
    "all-recommendations": { "href": "http://localhost:8080/recommendations" },
    "farm": { "href": "http://localhost:8080/farms/1" }
  }
}
```

**`riskLevel` possíveis:** `LOW`, `MEDIUM`, `HIGH`

---

## Modelos de Dados

### User
| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | ID gerado automaticamente |
| `name` | String | Nome do usuário |
| `email` | String | E-mail único |
| `passwordHash` | String | Senha criptografada com BCrypt |
| `userRole` | String | `USER` ou `ADMIN` |
| `createdAt` | LocalDateTime | Data de criação (auto) |

### Farm
| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | ID gerado automaticamente |
| `farmName` | String | Nome da fazenda |
| `location` | String | Localização |
| `farmSizeHectares` | BigDecimal | Tamanho em hectares |
| `owner` | User | Proprietário (FK) |
| `createdAt` | LocalDateTime | Data de criação (auto) |

### Sensor
| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | ID gerado automaticamente |
| `sensorName` | String | Nome do sensor |
| `sensorType` | String | `TEMPERATURE` ou `HUMIDITY` |
| `sensorStatus` | String | `ACTIVE` ou `INACTIVE` |
| `lastReading` | BigDecimal | Última leitura registrada |
| `installedAt` | LocalDateTime | Data de instalação (auto) |
| `farm` | Farm | Fazenda vinculada (FK) |

### Incident
| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | ID gerado automaticamente |
| `incidentType` | String | Tipo do incidente |
| `incidentDescription` | String | Descrição detalhada |
| `incidentDate` | LocalDateTime | Data do incidente (auto) |
| `incidentStatus` | String | Status atual |
| `farm` | Farm | Fazenda vinculada (FK) |

### ClimateAlert
| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | ID gerado automaticamente |
| `alertType` | String | Ex: `DROUGHT` |
| `severity` | String | `LOW`, `MEDIUM`, `HIGH` |
| `message` | String | Mensagem descritiva |
| `alertDate` | LocalDateTime | Data do alerta |
| `farm` | Farm | Fazenda vinculada (FK) |

### AiRecommendation
| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | ID gerado automaticamente |
| `recommendation` | String | Texto da recomendação gerada pela IA |
| `riskLevel` | RISKLEVEL | `LOW`, `MEDIUM` ou `HIGH` |
| `generatedAt` | LocalDateTime | Data de geração (auto) |
| `farm` | Farm | Fazenda vinculada (FK) |

---

## HATEOAS

Todos os responses individuais e páginas incluem links de navegação no padrão HAL (Hypertext Application Language):

```json
{
  "_links": {
    "self": { "href": "http://localhost:8080/farms/1" },
    "all-farms": { "href": "http://localhost:8080/farms" },
    "user": { "href": "http://localhost:8080/users/1" },
    "alerts": { "href": "http://localhost:8080/alerts?farmId=1" },
    "recommendations": { "href": "http://localhost:8080/recommendations?farmId=1" }
  }
}
```

Esses links permitem que o cliente navegue pela API sem conhecer previamente as URLs — seguindo o princípio HATEOAS do REST.

---

## Módulo Gaia AI

O microserviço `terraorbit-gaia-ai` é um serviço auxiliar responsável por intermediar a comunicação com a LLM via **OpenRouter**.

**Tecnologias:** Spring Boot 4.0.6 (Gradle), Java 21, Spring WebFlux, Lombok

**Único endpoint exposto:**

```
POST http://localhost:8081/ai/analyze
```

**Request body:**
```json
{
  "temperature": 32.5,
  "humidity": 18.0
}
```

**Response:**
```json
{
  "riskLevel": "HIGH",
  "recommendation": "Irrigate crops immediately. High temperature may cause soil dehydration."
}
```

O serviço monta um prompt estruturado com os dados climáticos, consulta o modelo `openrouter/owl-alpha` e faz o parse do JSON retornado pelo LLM.

**Configuração (`application.yaml`):**
```yaml
server:
  port: 8081

openrouter:
  api-key: ${API_KEY_OPENROUTER}
  model: openrouter/owl-alpha
```

---

## Documentação Swagger

Com a API principal em execução, acesse a documentação interativa em:

```
http://localhost:8080/swagger-ui.html
```

O Swagger permite testar todos os endpoints diretamente pelo navegador, incluindo autenticação via JWT.

---

## Integrantes

Projeto desenvolvido para a disciplina de **Global Solution 2026** — FIAP.

| Nome | RM |
|---|---|
| Felipe Anselmo| RM560661 |
| João vinicius  | RM559369 |
| Matheus Mariotto | RM560276 |
| Henrique Sladkevicius | RM560698 |
| Victor de Carvalho | RM560395 |