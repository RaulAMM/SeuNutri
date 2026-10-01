# AGENTS.md

## Escopo do Projeto
Aplicação **monolítica** com **Spring Boot** e **Thymeleaf**, voltada à promoção da alimentação saudável.
Não haverá separação backend/frontend, endpoints REST, nem migrations (Flyway/Liquibase).
O banco é manipulado exclusivamente via `src/main/resources/schema.sql` e `data.sql`.

## Tecnologias
- **JDK:** 21 · **Spring Boot:** 4.1.1 · **MySQL** · **JPA/Hibernate** · **Thymeleaf**
- IDE principal: Eclipse; VS Code apenas com Copilot. Metodologia: Scrum com ciclo **GVR**.

## Comandos (Windows)
- Rodar app: `.\gradlew.bat bootRun`
- Testes: `.\gradlew.bat test` (JUnit Platform)
- Build: `.\gradlew.bat build`

## Banco de Dados — pré-requisitos de execução
- MySQL esperado em `localhost:3307`, usuário `root`, senha `root`, schema `SeuNutri` (ver `application.properties`).
- MySQL **não** é banco embutido: `schema.sql`/`data.sql` **não** são executados automaticamente — crie schema/tabelas manualmente antes de subir a app.
- `spring.jpa.hibernate.ddl-auto=validate`: qualquer divergência entre entidades e `schema.sql` derruba a aplicação no boot.
- O teste único (`SeunutriApplicationTests.contextLoads`, `@SpringBootTest`) **exige** o MySQL acessível em 3307; sem ele, o teste falha.

## Armadilhas estruturais (verificadas no código)
- Os pacotes `repository` e `services` estão **fora** de `edu.ifsp.seunutri`: o component scan do `@SpringBootApplication` **não** os registra como beans. Novos repositórios/serviços devem ficar em `edu.ifsp.seunutri.<camada>` ou a injeção quebra.
- Mapeamentos JPA atuais são inválidos/incompatíveis com o schema (`@ManyToAny` em campos `int`, `Prato.id` vs coluna `idPrato`, naming strategy snake_case do Spring vs colunas camelCase como `valorNutricional`): o `ddl-auto=validate` tende a falhar até isso ser reconciliado.
- Ainda não existem pacote de controllers nem pasta `src/main/resources/templates` (Thymeleaf não tem view para renderizar).
- Headers das entidades do schema usam `CREATE SCHEMA IF NOT EXISTS`, mas `application.properties` aponta para `SeuNutri` — o schema precisa existir antes do boot.

## Dependências — inconsistência conhecida
- A regra antiga deste arquivo dizia "Não utilizar Lombok", mas **Lombok está no `build.gradle`** (`compileOnly` + `annotationProcessor`) e já é usado em `Prato`. Ao mexer nesse ponto, confirme a decisão do time antes de adicionar/remover.
- Spring Boot 4 renomeou starters: use `spring-boot-starter-webmvc`, `-thymeleaf`, `-data-jpa` e variantes `-test` (não o antigo `spring-boot-starter-web`).

## Regras de Arquitetura
1. Camadas **Controlador → Service → Repositório**; entidades JPA trafegam entre camadas.
2. DTOs só para representar elementos de interface; mapeamento DTO↔Entity manual, sem bibliotecas.
3. Sem REST controllers, sem separar backend/frontend.

## Restrições Normativas
- Não alterar/propor novos requisitos ou funcionalidades fora do escopo.
- Não adicionar dependências sem aprovação explícita.
- Toda manipulação de banco via `schema.sql`/`data.sql`; sem migrations automáticas.
- IA atua **somente como suporte no ciclo GVR**, nunca como executora autônoma:
  - sem commits automáticos (commits são só de humanos),
  - sem mudar requisitos/arquitetura/tecnologias por conta própria,
  - sem propor REST ou separação backend/frontend.
