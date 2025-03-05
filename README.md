# Documentação da API REST - Gerenciamento de Usuários

## Descrição
Esta API foi desenvolvida com Spring Boot e oferece funcionalidades para gerenciamento de usuários, incluindo operações CRUD. A API permite criar, ler, atualizar e excluir usuários, com dados de **nome**, **telefone**, **email** e **senha**. Para garantir a segurança, a autenticação básica (Basic Auth) foi implementada apenas no endpoint de criação de usuários (POST), e as senhas dos usuários são armazenadas de forma segura, utilizando criptografia bcrypt.

Além disso, a API utiliza a validação dos campos de entrada para garantir que os dados estejam no formato correto.
## Endpoints

### 1. **POST /usuario**
Cria um novo usuário.

#### Requisição:
- **URL**: `/usuario`
- **Método**: POST
- **Autenticação**: Basic Auth (requer credenciais válidas: login: `admin`, senha: `admin123`)
- **Cabeçalhos**:
  - `Content-Type: application/json`
- **Corpo da requisição**:
```json
{
  "nome": "João Silva",
  "telefone": "1234567890",
  "email": "joao.silva@email.com",
  "senha": "senhaSegura123"
}
```
#### Resposta
- Código de Status: 201 Created
- Corpo da resposta
```json
{
  "id": 1,
  "nome": "João Silva",
  "telefone": "1234567890",
  "email": "joao.silva@email.com",
  "senha": "senha criptografada"
}
```

### 2. **GET /usuario/{id}**
Obtém os dados de um usuário específico.

#### Requisição:
- **Método**: GET
- **Autenticação**: Não requer autenticação
- **Parâmetros**:
  - `id`: ID do usuário

#### Resposta:
- **Código de Status**: 200 OK
- **Corpo da resposta**:
```json
{
  "id": 1,
  "nome": "João Silva",
  "telefone": "1234567890",
  "email": "joao.silva@email.com",
  "senha": "senha criptografada"
}
```

### 3. **PUT /usuario/{id}**
Atualiza os dados de um usuário específico.

#### Requisição:
- **Método**: PUT
- **Autenticação**: Não requer autenticação
- **Cabeçalhos**:
  - `Content-Type: application/json`
- **Corpo da requisição**:
```json
{
  "nome": "João Silva Atualizado",
  "telefone": "0987654321",
  "email": "joao.silva.novo@email.com",
  "senha": "novaSenhaSegura456"
}
```
#### Resposta
- Código de Status: 200 OK
- Corpo da resposta
```json
{
  "id": 1,
  "nome": "João Silva Atualizado",
  "telefone": "0987654321",
  "email": "joao.silva.novo@email.com",
  "senha": "senha criptografada"
}
```

### 4. **DELETE /usuario/{id}**
Deleta um usuário específico.

#### Requisição:
- **Método**: DELETE
- **Autenticação**: Não requer autenticação
- **Parâmetros**:
  - `id`: ID do usuário

#### Resposta:
- **Código de Status**: 204 No Content
- **Corpo da resposta**: Não há corpo na resposta.

## Autenticação

### Basic Auth
A API utiliza autenticação básica (Basic Auth) apenas no endpoint **POST /usuario** para a criação de novos usuários. As credenciais devem ser enviadas no cabeçalho `Authorization` da requisição, no formato `Basic {base64-encoded-username:password}`.

#### Credenciais:
- **Login**: `admin`
- **Senha**: `admin123`

## Segurança

### Criptografia de Senhas
As senhas dos usuários são armazenadas de forma segura utilizando o algoritmo bcrypt. Durante o processo de registro, a senha informada pelo usuário é criptografada e, ao fazer login ou realizar operações que envolvem a senha, a comparação é feita entre a senha criptografada no banco de dados e a senha fornecida pelo usuário.

**Nota importante**: A senha nunca é retornada nas respostas da API, nem mesmo após uma atualização, garantindo a segurança dos dados.

---

## Validação

A API utiliza a validação dos dados de entrada utilizando anotações do **Spring Validation**. Isso garante que os dados enviados pelo usuário estejam no formato correto antes de serem salvos no banco de dados.

### Exemplo de validação no modelo `Usuario`:
- **nome**: Não pode ser nulo nem vazio.
- **telefone**: Deve ser um número válido.
- **email**: Deve ser um email válido.
- **senha**: Não pode ser nula nem vazia.

```java
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Usuario {

        @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Nome é obrigatório.")
	@Size(min = 3, max = 100, message = "Nome deve conter entre 3 á 100 caracteres")
	private String nome;
	@NotBlank(message = "Telefone é obrigatório.")
	private String telefone;
	@NotBlank(message = "Email é obrigatório.")
	@Email(message = "Email deve ser válido")
	private String email;
	@NotBlank
	@Size(min = 7, message = "A senha deve conter pelo menos 8 caracteres.")
	private String senha;

    // Getters e Setters
}
```

## Dependências

Aqui estão as dependências utilizadas no projeto:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### Descrição das dependências:
- Spring Boot Starter Data JPA: Utilizado para integração com o banco de dados MySQL, fornecendo funcionalidades de persistência de dados.
- Spring Boot Starter Validation: Utilizado para realizar a validação dos dados de entrada.
- Spring Boot Starter Web: Para criar os endpoints RESTful da API.
- MySQL Connector/J: Conector para integração com o banco de dados MySQL.
- Lombok: Biblioteca para reduzir o boilerplate de código, como a geração de getters e setters.
- Spring Boot Starter Test: Para testes automatizados da aplicação.
- Spring Boot Starter Security: Para adicionar funcionalidades de autenticação e segurança à aplicação.
