# AGENTS.md

## Escopo do Projeto
Este projeto consiste em uma aplicação **monolítica** desenvolvida com **Spring Boot** e **Thymeleaf**, voltada à promoção da alimentação saudável.  
Não haverá separação entre backend e frontend.  
O banco de dados será manipulado exclusivamente por meio dos arquivos `src/main/resources/schema.sql` e `src/main/resources/data.sql`.  
Não serão utilizados endpoints REST, nem ferramentas de migrations (Flyway, Liquibase).  

## Tecnologias Utilizadas
- **JDK:** 21  
- **Backend:** Java + Spring Boot  
- **Frontend:** Thymeleaf + HTML5 + CSS3  
- **Banco de Dados:** MySQL (scripts SQL para schema e dados)  
- **IDE Principal:** Eclipse  
- **Versionamento:** Git  
- **Agente de IA:** GitHub Copilot (via VS Code)  
- **Metodologia:** Scrum, com aplicação do ciclo **GVR (Generate-Verify-Refine)**  

## Regras de Arquitetura
1. **Camadas**  
   - Arquitetura básica: **Controlador → Service → Repositório**.  
   - As próprias **entities JPA** trafegarão entre as camadas, para simplificar.  

2. **DTOs**  
   - Criar DTOs somente quando houver necessidade de representar elementos de interface (ex.: sumário de dashboard).  
   - O mapeamento entre DTO e Entity deve ser feito **manualmente**, sem uso de bibliotecas externas.  

3. **Entities**  
   - Sempre que possível, utilizar diretamente as entities JPA como objetos de dados.  
   - Evitar duplicação desnecessária de estruturas.  

## Restrições Normativas
Agentes de IA e desenvolvedores devem seguir as seguintes diretrizes:

1. **Requisitos do Projeto**
   - Não alterar ou propor novos requisitos além dos definidos.  
   - Não incluir funcionalidades fora do escopo estabelecido.  

2. **Dependências**
   - Não adicionar novas dependências ao projeto sem aprovação explícita.  
   - Não utilizar **Lombok**.  

3. **Banco de Dados**
   - Toda manipulação deve ser feita via `schema.sql` e `data.sql`.  
   - Não utilizar migrations automáticas.  

4. **Agentes de IA**
   - Devem atuar apenas como suporte no ciclo **GVR**.  
   - **Não atuar como executores autônomos**: significa que agentes não devem tomar decisões ou realizar ações sem supervisão humana.  
     - Não realizar commits automaticamente.  
     - Não modificar requisitos ou arquitetura por conta própria.  
     - Não incluir novas dependências sem aprovação.  
   - Não propor endpoints REST.  
   - Não sugerir separação entre backend e frontend.  
   - Devem respeitar as tecnologias e restrições descritas neste documento.  

5. **Versionamento**
   - O projeto será versionado com Git.  
   - Commits devem ser feitos apenas por humanos, nunca por agentes de IA.  

## Diretrizes para Humanos
- Usar **Eclipse** como IDE principal para desenvolvimento.  
- Usar **VS Code** apenas como interface para interação com o Copilot.  
- Garantir que todas as contribuições estejam alinhadas ao escopo e restrições deste documento.  

## Diretrizes para Agentes de IA
- Apoiar na geração de código, verificação e refino (ciclo GVR).  
- Seguir estritamente as restrições normativas.  
- Não propor mudanças de arquitetura ou tecnologias fora do escopo.  
- Atuar como **assistente supervisionado**, nunca como executor autônomo.  

