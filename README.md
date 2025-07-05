# xVidros Backend

API REST desenvolvida em Java com Spring Boot para gerenciamento do sistema xVidros.

## Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- MySQL 8+ (ou altere para seu banco preferido)

## Configuração

1. **Clone o projeto:**

    ```bash
    git clone https://github.com/SEU-USUARIO/xvidros-backend.git
    cd xvidros-backend
    ```

2. **Configure o banco de dados:**
   
   - Crie um banco chamado `xvidros`.
   - No arquivo `src/main/resources/application.properties` edite:
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/xvidros
     spring.datasource.username=SEU_USUARIO
     spring.datasource.password=SUA_SENHA
     ```

3. **Instale as dependências e rode a aplicação:**

    ```bash
    ./mvnw spring-boot:run
    # ou
    mvnw spring-boot:run
    # ou (Windows)
    mvnw.cmd spring-boot:run
    ```

- A API ficará disponível em `http://localhost:8080`

## Principais Endpoints

- `/api/products` - Gerenciar produtos
- `/api/cart` - Carrinho de compras
- `/api/auth` - Autenticação (login, registro)

---

