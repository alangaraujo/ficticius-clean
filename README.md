# Aplicação Ficticius Clean

API para ranking de consumo de combustível de veículos.

Utiliza Java 11, Swagger, Flyway e Banco de Dados H2 (para utilização em memória).

## Para Baixar:

```
git clone https://github.com/alangaraujo/ficticius-clean.git
```

## Para Executar:

Para gerar o arquivo executável, é necessário acessar a pasta do projeto e executar:

```
Para Linux:
./mvnw package
(caso não seja executado, defina as permissões com o comando 'chmod 755 mvnw')

Para Windows
mvnw package
```

Após criação do arquivo executável (extensão JAR), ele estará na pasta /target, acessando o diretório do projeto, executar a aplicação via linha de comando:

```
java -jar ficticius-clean-0.0.1-SNAPSHOT.jar
```

## Para Testar

O projeto já possui a ferramenta Swagger, na qual não será necessário utilizar ferramentas de testes de API's (e.g. Postman/Insomnia), para isso, acessar através do seu navegador a URL http://localhost:8080/swagger-ui.html


## Outras Informações

Como utiliza banco de dados em memória, não será necessário qualquer configuração, alguns registros serão populados para consulta imediata, enquanto a aplicação estiver em execução.

Assim que aplicação for fechada, os dados, assim como o acesso ao banco de dados, não estarão mais disponíveis.
