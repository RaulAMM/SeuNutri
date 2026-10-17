# AGENTS.md

## Projeto
SeuNutri — aplicação monolítica **Spring Boot + Thymeleaf + JPA/Hibernate**, JDK 21 (toolchain), banco **MySQL**. Sem REST, sem separação backend/frontend, sem migrations (Flyway/Liquibase). O banco é definido em `src/main/resources/schema.sql` e `data.sql`. build.gradle declara Spring Boot `4.1.1` e starters `webmvc`, `thymeleaf`, `data-jpa` (não o antigo `web`).

## Entrypoint e pacotes
- Classe principal: `edu.ifsp.seunutri.SeunutriApplication` (`@SpringBootApplication` na raiz `edu.ifsp.seunutri`).
- O component scan cobre `edu.ifsp.seunutri.**`: controllers, serviços e repositórios **devem ficar em** `edu.ifsp.seunutri.controller`, `.service`, `.repository` para serem registrados como beans.
- Camadas: Controlador → Service → Repositório; entidades JPA trafegam entre as camadas (sem DTOs, sem REST).

## Comandos (Windows, via Gradle wrapper)
- Rodar app: `.\gradlew.bat bootRun`
- Testes: `.\gradlew.bat test` (JUnit Platform)
- Build: `.\gradlew.bat build`

## Banco de dados — pré-requisitos de execução
- MySQL em `localhost:3307`, usuário `root`, senha `root`, schema `SeuNutri` (`application.properties`).
- MySQL **não** é embutido: `schema.sql`/`data.sql` **não** são executados automaticamente pelo Spring — crie o schema e as tabelas manualmente antes de subir (`schema.sql` tem `CREATE SCHEMA IF NOT EXISTS SeuNutri` + `USE SeuNutri`).
- `spring.jpa.hibernate.ddl-auto=validate`: divergência entidade ↔ schema derruba a app no boot. Mapeie nomes diferentes com `@Column(name = ...)` (ex.: `valorNutricional`↔`valor_nutricional`, `usuarioCPF`↔`usuario_cpf`, `idRanque`↔`id_ranque`, `idPrato`↔`id_prato`).
- O teste `SeunutriApplicationTests.contextLoads` (`@SpringBootTest`) **exige** MySQL acessível em 3307; sem ele, falha.

## Tecnologias e dependências
- Lombok está declarado (`compileOnly` + `annotationProcessor`) e já usado em `Prato` (`@NoArgsConstructor @AllArgsConstructor`). Não remova nem adicione sem confirmação do time.
- Views Thymeleaf em `src/main/resources/templates/<entidade>/` (ex.: `pratos/listar.html`, `pratos/formulario.html`).

## Regras normativas
- Não alterar `build.gradle`, `settings.gradle`, `application.properties`, `schema.sql` nem `data.sql`.
- Não adicionar dependências nem funcionalidades fora do escopo sem aprovação.
- Commits só de humanos; a IA atua como suporte, não como executora autônoma (sem commits, sem mudar requisitos/arquitetura/tecnologias por conta própria).
