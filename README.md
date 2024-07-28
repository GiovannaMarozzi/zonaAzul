# Zona Azul

## Tecnologias Utilizadas
- **DDD (Domain-Driven Design)**
- **Design Patterns**
- **SOLID**
- **API Rest**
- **Spring Boot**
- **Banco de dados MongoDB**
- **Swagger**
- **JPA**
- **Java 17**

## Conexão com o Banco de Dados
- **URL do Banco de Dados**: [http://localhost:8081/db/zonaAzulDB](http://localhost:8081/db/zonaAzulDB)
- **Login**: techChallenge
- **Password**: fiap

## Conexão com o Swagger
- **URL do Swagger**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Passo-a-Passo para Subir o Projeto

1. **Subir Containers com Docker**:
   - Na pasta raiz do projeto, abra um terminal (ou CMD) e execute o comando:
     ```bash
     docker-compose up
     ```
   - Este comando irá iniciar os containers do MongoDB e do Mongo-Express.

2. **Verificar Banco de Dados**:
   - Após a subida do MongoDB, verifique se o banco de dados está ativo acessando o endereço: [http://localhost:8081/db/zonaAzulDB](http://localhost:8081/db/zonaAzulDB).

3. **Subir o Projeto**:
   - Após confirmar que o banco de dados está ativo, você pode iniciar o projeto. O servidor estará disponível no endereço: `http://localhost:8080`.

> **Observação**: As collections no MongoDB serão criadas automaticamente conforme as requisições forem feitas no back-end.

---

Esperamos que este guia ajude a configurar e iniciar o projeto Zona Azul com facilidade. Se encontrar qualquer problema, por favor, consulte a documentação oficial das tecnologias usadas ou entre em contato com o time de desenvolvimento.
