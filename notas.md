## ***Um Aviso Importante***

> O Código apresentado aqui está longe de ser perfeito. Meu foco será exclusivamente na prática de funcionalidades CRUD realizando um sobrevoo na estrutura de projeto e recursos que o framework Spring Boot oferece, que é o tema central deste artigo. Então, caro programador experiente que está lendo isso, peço que não se preocupe demais com outras questões como arquitetura ou boas práticas. foco no essencial!

## Criar, configurar e iniciar o projeto (1)

### Setup base

- [X] criar projeto

Criei o projeto com: [1]

- group: praticas
- artifact: microprojeto-04
- package name: praticas.microprojeto-42

- [X] configurar containers (aplicação e banco de dados)
- [X] iniciar projeto springboot
- [X] configurar pom.xml [1]
- [X] rodar projeto para validar
- [X] iniciar projeto pelo mvn

```bash
mvn archetype:generate \
  -DgroupId=praticas \
  -DartifactId=microprojeto-04 \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
```

- [X] configurar pom.xml

- Alterei o pom.xml adicionando o lombok e configurando a parte de build [2]

```bash


[...]

<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.0.6</version>
</parent>

[...]

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>

[...]

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc</artifactId>
		</dependency>

		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
	</dependencies>

[...]

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<executions>
					<execution>
						<id>default-compile</id>
						<phase>compile</phase>
						<goals>
							<goal>compile</goal>
						</goals>
						<configuration>
							<annotationProcessorPaths>
								<path>
									<groupId>org.projectlombok</groupId>
									<artifactId>lombok</artifactId>
								</path>
							</annotationProcessorPaths>
						</configuration>
					</execution>
					<execution>
						<id>default-testCompile</id>
						<phase>test-compile</phase>
						<goals>
							<goal>testCompile</goal>
						</goals>
						<configuration>
							<annotationProcessorPaths>
								<path>
									<groupId>org.projectlombok</groupId>
									<artifactId>lombok</artifactId>
								</path>
							</annotationProcessorPaths>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>

[...]
```

---

## Criar estrutura em camadas "MVC" e configurar Eclipse (2)

- [X] criar estrutura "MVC" [3]

- O que chamo de MVC é apenas ref ao padrão de camadas, não indica igualdade, mas semelhança. [5]

- [X] criar primeira rota
- [X] ajustar eclipse para debug da aplicação,

- criado debug configuration no Eclipse para poder debugar remotamente
- usei o javaRemoteApplication
- passei a url do container e a porta (5005)
- liberei a porta 5005 no container
- subi com o comando abaixo:
- ```bash
  mvn spring-boot:run \
  	-Dspring-boot.run.jvmArguments="\
  	-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"'
  ```

---

## Configurar e testar acesso pela aplicação ao banco de dados (3)

- [X] criar banco de dados no postgres (usar dbeaver para gerenciar o postgres)

- criado quando o ambiente subiu...

- [X] criar estrutura de tabelas necessárias (CRUD de Pets simples)

```bash
tables.sql
```

- [X] configurar aplicação para se conectar com o banco de dados

- o spring-boot já traz o HikariCP como base de configuração  de Pool de conexão
- coloquei no pom.xml a dependência do postgree
- o driver de conexão está no projeto pois peguei pelo springInitializer ao adicionar o starter
- precisei alterar o `application.properties` adicionando as propriedades de conexão

```plaintext
spring.datasource.url=jdbc:postgresql://localhost:5432/petsdb
spring.datasource.username=postgres
spring.datasource.password=SENHA_MISTERIOSA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

- [X] realizar primeira conexão e consulta ao banco de dados para testar

- Criando Entidade e anotando para refletir a representação que está no banco de dados, farei uso de estrutura pré-existente no banco (criado em etapas anteriores)
- anotações usadas no mapeamento da entidade:
- ```java
  //anotações de classe

  //Anotações do Javax/Jakarta persistence para mapeamento da classe via ORM
  @Entity		//Informa ao JPA que esta classe representa uma entidade persistida
  @Table		//Informa qual tabela será utilizada

  //Anotações do LomBok para acelerar o desenvolvimento, ele irá gerar o boilerplate que cada annotation representa.
  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor


  //anotações de atributos
  //anotações utilizadas para identificar qual atributo será o ID da classe mapeado para o ID da entidade persistida no banco e qual estratégia é utilizada na geração deste valor pois será gerado automaticamente.
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  //anotação que identifica cada coluna ta tabela no banco mapeando de acordo com cada atributo da classe, abaixo listo as variações utilizadas: 
  @Column(name = "name", nullable = false, length = 120) 	//usado em String
  @Column(name = "weight", precision = 10, scale = 2)	//usado em BiDecimal
  @Column(name = "nomes", columnDefinition = "TEXT")	//usado em String que conterá texto longo.
  @Column(name = "created_at", updatable = false)		//usado em LocalDateTime deixando claro que o dado não poderá ser alterado após a gravação no banco.
  ```

- [X] criar carga de dados no banco de dados

```bash
init.sql
```

- conectando e interagindo com o DB via terminal:
- ```bash
  make exec-db

  psql -u $POSTGRES_USER -d $POSTGRES_DB
  ```

---

## Criar funcionalidade de consulta de massa de dados (4)

- [X] criar funcionalidade de consulta de lista de dados [5]

- para esta estapa eu já começo considerando  o "soft delete" onde a consulta deverá trazer sempre os dados ativos, ou deleted = false;
- a requisição passa por todas as camadas até chegar ao repository que com JPA resolve a consulta já no nome do método. (**Derived Query Methods  ou **Query Methods****)
  - neste caso foi criado o `findByDeletedFalse()` na interface do repository que extende JpaRepository
- também farei uso de DTO mantendo o padrão da Stack e como boa prática.
  - Durante pesquisas vi o uso de configuração e classe de mapeamento assim como uso de MapStruct como dependência para conversão de DTO em Entity  ou Entity para DTO, porém como este é um micro projeto fiz de forma simplificada com builder em um método retornando a outra entidade. [7]
  - Neste caso foi criado um DTO simples de Response chamado de `PetResponseDTO` e utilizado anotação do LomBok `@Builder` [8]
- para injeção de dependência geralmente usa-se `@Autowired` em cada dependência no Spring Boot, porém ao usar `@RequiredArgsConstructor` do LomBok na classe é realizado a criação automática do construtor da classe realizando a injeção das dependências pelo construtor, tornando desnecessário o uso da anotação do Spring Boot [9]

```Java
//anotações de classe

//Anotação do Spring Boot
@RestController					  //Anotação para referenciar ao Spring Boot que esta classe será um controlador REST de forma explícita
@RequestMapping(value = "/pets")  //Anotação para mapear Web Request para os métodos que receberão requisições (pode ser usada a nível de classe ou de método)
@Service                          //Anotação para que o container do Spring gerencie a classe de serviço (encapsula a @Component)

//Anotações do LomBok para acelerar o desenvolvimento, ele irá gerar o boilerplate que cada annotation representa.
@RequiredArgsConstructor //gera construtor padrão e co DI para as dependências(ajuda a evitar @Autowired do Spring Boot))
@Builder                 //gera automaticamente APIs fluentes para instanciar objetos, eliminando o código boilerplate do construtor


//anotações de atributos
@GetMapping			//anotação usada no método para indicar que ele será usado para Web Request via método Get neste controlador(pode conter query params e value com rota)
```

- o uso do lombok elimina muito código boilerplate e ajuda em outras atividades e padrões de projeto durante o desenvolvimento, o código gerado por ser consultado na pasta target, no caso deste projeto pode ser visto da seguinte forma:

```Shell
javap -p target/classes/praticas/microprojeto_04/entity/Pet.class
```

- a estrutura atual do projeto com a requisição que lista todos os Pets fica da forma abaixo:

```Shell
[...]
├── src
│   ├── main
│   │   ├── java
│   │   │   └── praticas
│   │   │       └── microprojeto_04
│   │   │           ├── controller
│   │   │           │   └── PetController.java
│   │   │           ├── dto
│   │   │           │   └── PetResponseDTO.java
│   │   │           ├── entity
│   │   │           │   └── Pet.java
│   │   │           ├── Microprojeto04Application.java
│   │   │           ├── repository
│   │   │           │   └── PetRepository.java
│   │   │           └── service
│   │   │               └── PetService.java
[...]
```

- No Controller que servirá os endpoints REST o retorno será do tipo `ResponseEntity` pois ele **representa a resposta HTTP completa** , não apenas o objeto que será serializado em JSON. [10]

  - A ideia de utilizá-lo é para evitar de controlar apenas o corpo da resposta, com isso será possível ter de uma vez o Status HTTP, Cabeçalhos e Corpo representados no objeto e tratados pelo Spring Boot na resposta.
- a saída para a requisição do Cliente foi um JSON com a lista de PETs cadastrados no pets_db (Postgres)

---

## criar funcionalidade de consultar unidade (5)

- [X] criar funcionalidade de consultar uma unidade

- Nesse caso cai bem o uso de `Optional` para tratar a possibilidade de não ter a informação solicitada e já ficar claro que além de não ter terá um possível NPE tratado. [11]
- O Uso do Optional neste retorno facilitará o lançamento de exceção caso não exista registro, e a captura para tratamento/interpretação dessa exceção na chamada do endpoint.
- Inicialmente o endpoint irá retornar status 500 com a exception não tratada adequadamente(como no exemplo abaixo), porém o correto é que a APi tenha tratamento de exceção criado com exceptions personalizadas quando necessário assim como o retorno ideal do status code que pode variar dependendo do contexto.

```JSON
{
    "timestamp": "2026-06-28T00:46:35.841Z",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/pets/1000"
}
```

- no caso desse microprojeto, para retornar o status code ideal realizei realizei a criação de uma exception personalizada com `ExceptionHandler` para captura, tratamento do retorno para entregar o `Http Status Code` ideal [12]
- a saída ficou assim:

```
Pet com o id 1000 não foi encontrado.
```

- visualizando pelo `Postman` é possível ver que foi emitido o status 404 para a resposta assim como a string de que o registro não foi encontrado, mas como não fica legal só a string mas sim  usar o padrão de Json, decidi dar mais um passo no tratamento da resposta quando essa exceção ocorre para emitir um Json.
- Criei um `ErrorResponseDTO` pra transportar a resposta entre as camadas até a saída da API de forma limpa e padronizada.
- alterei o `ExceptionHandler`para montar a resposta dentro do DTO criado com as informações corretas. [13]
- com ResponseEntity bastou substituir a string de saída pelo DTO criado, a saída agora foi:

```JSON
{
    "timestamp": "2026-06-28T01:35:19.393841742",
    "status": 404,
    "error": "Not Found",
    "message": "Pet com o id 1000 não foi encontrado.",
    "path": "/pets/1000"
}
```

- Pra alcançar esse ponto, que é mais satisfatório precisei usar algumas annotations e percebi que o Spring Boot disponibiliza argumentos injetáveis e os resolve quando solicitados [14]

```Java
//anotações de classe

//Anotação do Spring Boot
@RestControllerAdvice   //Faz parte do tratamento de exceções do Spring MVC, indica que a classe contém regras globais para tratar exceções lançadas pelos Controllers REST

//anotações de atributos
@ExceptionHandler(PetNotFoundException.class)  //diz que o método trata exceções do tipo especificado
```

---

## criar funcionalidade de edição (6)

- [X] criar funcionalidde de edição de uma unidade

- neste ponto a quantidade de código criada começou a ficar menor, dado que já existem recursos para retornar DTO de response, assim como tratamento de exceção, além disso o JPA já oferece métodos CRUD criados e implementados para evitar a criação de código boilerplate, com isso basta chamar o método `save(T)` do repository que será executado um save para criação ou neste caso save para edição pois o JPA está gerenciando a entidade, inclusive pelo fato da entidade ter ID presente.
- Como neste caso está sendo realizado `PUT`será realizado atribuição de possível substituição de todos os dados do registro [15].
- foi enviado um `JSON` completo como o exemplo abaixo:

```JSON
    {
        "name": "Mia",
        "species": "CAT",
        "breed": "Tiger",
        "color":"Black",
        "weight":9.5,
        "vaccinated":true,
        "birthDate":"2020-03-10",
        "notes":"Healthy - updated"
    }
```

- a resposta retorno corretamente com status 200 e em caso de passar um ID que não existe  a resposta retorna corretamente o `JSON`com status 404.
- Obs.: para evitar problemas o interessante é **validar os dados recebidos** para evitar que dados sensíveis sejam salvos em branco, então a stack junto com o framework facilita esse tipo de validação, e caso não passe o 404 poderá retornar uma mensagem mais rica em informações mostrando quais dados faltaram, porém **este tema ficará para o próximo microprojeto**, onde o foco será em cadastro e update de registros e partes de registros com dados validados.


---

## criar deleção de unidade de registro (7)

- [X] usar soft Delete. [16]

- para o soft delete foi implementado fazendo reúso dos recursos já criados além de implementar alguns itens a mais para dar formalidade e prática ao projeto, como uso de status code http diferente entre chamadas do delete e exception personalizada para esse caso.
- o método de soft delete usado foi atualizar o valor de 2 atributos, o `deleted` e o `updated_at` em conjunto e realizar uma operação de save no banco, porém o `Spring/JPA` oferencem um recurso mais semântico para implementação do soft delete onde a operação completa de delete é realizada pela aplicação, porém no momento de executar será identificado o uso da annotation ``@SQLDelete`` na entidade realizando um "Override" da operação, nesta anotação pode-se criar o sql necessário para atualização conforme exemplo abaixo e simplificando mais a escrita de código em diversas operações de delete. [16 ]
- eu implelentei o modo mais simples, alterando os dois atributos como mencionado e coloquei um método `maskAsDeleted` com validação+exceptiion na entidade, assim o service não precisa manipular 2 atributos de forma independente, apenas chamar o método do domínio.

```Java
@SQLDelete(sql = "UPDATE tbl_products SET deleted = true WHERE id=?")
public class Product {}
```

- existem várias estratégias: como implementar anotação  `@Where` na classe, implementar anotação de alterar a operação SQL realizada ``@SQLDelete``(como no exemplo acima), realizar a operação manualmente a cada delete, assim como a implementação de interfaces para que cada entidade possa implementar o soft ou hard delete via contrato. ou seja, o sprin-boot permite o uso de diversas estratégias e auxilia o uso de várias, o uso então se torna uma questão de governança e estratégia do time técnico [16]
- Uma observação a ser testada é a identificação de Log da aplicação, pois enquanto uma operação pode ser escrita como `delete` na conversão para o SQL final outra será um `update`, algo que vou criar um experimento em outro momento para ver as possíveis formas de tratar isso.
- Outro ponto interessante é a decisão de qual Status HTTP emitir na resposta para o cliente, pois APIs REST procuram ter Indepotência, e múltiplas chamadas no mesmo endpoint com mesmo ID deveriam entregar a mesma resposta, porém decidi entregar respostas diferentes, que a nível de neǵocio pode ser entendida como igual, porém a nível técnico é diferente. [17]

  - explicando o ponto acima: na primeira chamda a API irá retornar `204` e se a chamada fosse repetida  muitas APIs retornariam novamente `204`.
  - eu decidi implementar uma saída informando que o conteúdo já havia sido excluído, isso muda a perspectiva do client, ele entende que a operação que realizou na requisição não teve efeito prático, pois o recurso já havia sido excluído, então como resultado ele entende "está deletado" porém sabendo que "não fui eu, mas está deletado",  então temos a igualdade de que "está deletado" porém contextualizando que a operação atual realizou ou não esse delete.

  ```Shell
  //primeira requisição 

  * Host localhost:8080 was resolved.
  * IPv6: ::1
  * IPv4: 127.0.0.1
    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                   Dload  Upload   Total   Spent    Left  Speed
    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0*   Trying [::1]:8080...
  * Connected to localhost (::1) port 8080
  > DELETE /pets/5 HTTP/1.1
  > Host: localhost:8080
  > User-Agent: curl/8.5.0
  > Accept: */*
  > 
  < HTTP/1.1 204 
  < 
    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
  * Connection #0 to host localhost left intact


  //segunda requisição 
  * Host localhost:8080 was resolved.
  * IPv6: ::1
  * IPv4: 127.0.0.1
    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                   Dload  Upload   Total   Spent    Left  Speed
    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0*   Trying [::1]:8080...
  * Connected to localhost (::1) port 8080
  > DELETE /pets/5 HTTP/1.1
  > Host: localhost:8080
  > User-Agent: curl/8.5.0
  > Accept: */*
  > 
  < HTTP/1.1 409 
  < Content-Type: application/json
  < Transfer-Encoding: chunked
  < 
  { [143 bytes data]
  100   143    0   143    0     0  21426      0 --:--:-- --:--:-- --:--:-- 23833
  * Connection #0 to host localhost left intact

  //a segunda requisição direcionada para um arquivo:
  {
  	"timestamp":"...",
  	"status":409,
      	"error":"Conflict",
      	"message":"O pet com id 5 já está excluído.",
      	"path":"/pets/5"
  }
  ```



---

## criar cadastro de unidade de registro (9)

- [X] cadastrar pet

- ter deixado a criação de recurso por último foi interessante, eu havia criado uma massa de dados e injetado no banco então não precisava começar por este endpoint, e digo que foi interessante pois pude fazer reúso do que já havia sido construído para outros endpoints além é claro do quão simples foi realizá-lo com a facilidade que o framework entrega.
- conforme MDN/RFC explica fiz com recurso sendo criado e sendo retornado na resposta com status `201` [18]
- mantive o mesmo padrão do endpoint de update, não fiz validação de entrada, apenas enviei um json válido, pois validação ficará para um próximo microprojeto.
- json enviado:

```JSON
    {
        "name": "Floky",
        "species": "CAT",
        "breed": "Crazy",
        "color":"Orange",
        "weight":9.0,
        "vaccinated":false,
        "birthDate":"2020-03-10",
        "notes":"Healthy - created"
    }
```



---

## Fechando o microprojeto

- encerrando o micro projeto, acredito que atendeu ao propósito, pude ver ainda que a sequência que usei pra criar os endpoints foi boa.
- para um projeto pedagógico foi essencial pra aplicar conceitos de desenvolvimento de forma a interagir com o framework depois de tanto tempo e vê-lo assumindo diversas responsabilidades que antes eram resolvidas pelo desenvolvedor, o que otimiza bastante o desenvolvimento e evita bugs/brechas no codigo.
- a estrutura do projeto ficou da seguinte forma:

```Shell
[...]
├── src
│   ├── main
│   │   ├── java
│   │   │   └── praticas
│   │   │       └── microprojeto_04
│   │   │           ├── controller
│   │   │           │   └── PetController.java
│   │   │           ├── dto
│   │   │           │   ├── CreatePetRequestDTO.java
│   │   │           │   ├── ErrorResponseDTO.java
│   │   │           │   ├── PetResponseDTO.java
│   │   │           │   └── UpdatePetRequestDTO.java
│   │   │           ├── entity
│   │   │           │   └── Pet.java
│   │   │           ├── exception
│   │   │           │   ├── ApiExceptionHandler.java
│   │   │           │   ├── BusinessException.java
│   │   │           │   ├── PetAlreadyDeletedException.java
│   │   │           │   └── PetNotFoundException.java
│   │   │           ├── Microprojeto04Application.java
│   │   │           ├── repository
│   │   │           │   └── PetRepository.java
│   │   │           └── service
│   │   │               └── PetService.java
[...]
```

---

### Comandos usados

- limpar projeto e realizar rebuild baixando dependências se necessário.

```Shell
mvn clean package
```



- rodar a aplicação via terminal

```Shell
mvn spring-boot:run
```


- Usando CURL para teste

```Shell
curl -v -X DELETE http://localhost:8080/pets/5 -o resposta.txt
```



- Houve ainda comandos Docker que usei via Makefile e podem ser encontrados no próprio Makefile disponível no repositório.

---

## Pesquisas

[1] https://start.spring.io/

[1.1] https://www.devmedia.com.br/primeiros-passos-com-o-spring-boot/33654

[1.2] https://medium.com/@felipeacelinoo/guia-pr%C3%A1tico-para-construir-uma-api-rest-com-spring-boot-e-java-99fa79f62c7

[2] https://medium.com/devdomain/using-lombok-in-spring-boot-simplifying-your-code-c38057894cb8

[2.1] https://mmarcosab.medium.com/usando-anota%C3%A7%C3%B5es-do-lombok-em-java-com-springboot-ecbec69234a9

[2.2] https://dicasdeprogramacao.com.br/como-configurar-o-lombok-no-eclipse/

[3] https://www.youtube.com/watch?v=ZaNVBhZUFIg

[3.1] https://medium.com/@anandjeyaseelan10/spring-boot-project-structure-explained-best-practices-c2ba46ea57eb

[3.2]https://guilherme-manzano.medium.com/anota%C3%A7%C3%B5es-do-curso-spring-boot-jpa-e-hibernate-4be7e6a827c6

[4] https://stackoverflow.com/questions/3835612/remote-debugging-tomcat-with-eclipse

[4.1] https://medium.com/@maneakanksha772/debugging-java-inside-a-docker-container-a-survival-guide-c2eee1655434

[5] [www.geeksforgeeks.org/java/spring-boot-crud-operations](https://www.geeksforgeeks.org/java/spring-boot-crud-operations/)

[5.1] [dev.to/akash_vadakkeveetil/basic-crud-using-java-spring-boot-2l07](https://dev.to/akash_vadakkeveetil/basic-crud-using-java-spring-boot-2l07)

[6] [medium.com/@zambuzesilva/dominando-o-spring-data-jpa-t%C3%A9cnicas-avan%C3%A7adas-para-consultas-eficientes-39e8c51c0235](https://medium.com/@zambuzesilva/dominando-o-spring-data-jpa-t%C3%A9cnicas-avan%C3%A7adas-para-consultas-eficientes-39e8c51c0235)

[6.1] [www.baeldung.com/spring-data-derived-queries](https://www.baeldung.com/spring-data-derived-queries)

[7] [www.freecodecamp.org/news/what-are-dtos-java](https://www.freecodecamp.org/news/what-are-dtos-java/)

[7.1] [www-baeldung-com.translate.goog/java-dto-pattern?_x_tr_sl=en&amp;_x_tr_tl=pt&amp;_x_tr_hl=pt&amp;_x_tr_pto=tc](https://www-baeldung-com.translate.goog/java-dto-pattern?_x_tr_sl=en&_x_tr_tl=pt&_x_tr_hl=pt&_x_tr_pto=tc)

[7.2] [www.geeksforgeeks.org/java/spring-boot-map-entity-to-dto-using-modelmapper](https://www.geeksforgeeks.org/java/spring-boot-map-entity-to-dto-using-modelmapper/)

[7.3] [medium.com/mobicareofficial/mapstruct-simplificando-mapeamento-de-dtos-em-java-c29135835c68](https://medium.com/mobicareofficial/mapstruct-simplificando-mapeamento-de-dtos-em-java-c29135835c68)

[7.4] [medium.com/thefreshwrites/mapping-entities-to-dtos-and-vice-versa-in-java-fe126f6bb6b2](https://medium.com/thefreshwrites/mapping-entities-to-dtos-and-vice-versa-in-java-fe126f6bb6b2)

[8] [www.baeldung.com/lombok-builder](https://www.baeldung.com/lombok-builder)

[8.1] [www.baeldung.com/creational-design-patterns#builder](https://www.baeldung.com/creational-design-patterns#builder)

[9] [www.linkedin.com/pulse/comparing-autowired-requiredargsconstructor-software-narang-arora-oygbc](https://www.linkedin.com/pulse/comparing-autowired-requiredargsconstructor-software-narang-arora-oygbc/)

[9.1] [www.baeldung.com/spring-injection-lombok](https://www.baeldung.com/spring-injection-lombok)

[9.2] [www.geeksforgeeks.org/springboot/spring-dependency-injection-autowired-vs-constructor-injection](https://www.geeksforgeeks.org/springboot/spring-dependency-injection-autowired-vs-constructor-injection/)

[10] [medium.com/@mvinodnayak46/introduction-to-responseentity-in-spring-boot-b51cf9eba597](https://medium.com/@mvinodnayak46/introduction-to-responseentity-in-spring-boot-b51cf9eba597)

[10.1] [dev.to/oigorrudel/quando-usar-responseentity-36b6](https://dev.to/oigorrudel/quando-usar-responseentity-36b6)

[10.2] [www.baeldung.com/spring-response-entity](https://www.baeldung.com/spring-response-entity)

[11] [www-baeldung-com.translate.goog/java-optional?_x_tr_sl=en&amp;_x_tr_tl=pt&amp;_x_tr_hl=pt&amp;_x_tr_pto=tc](https://www-baeldung-com.translate.goog/java-optional?_x_tr_sl=en&_x_tr_tl=pt&_x_tr_hl=pt&_x_tr_pto=tc)

[11.1] [medium.com/collabcode/como-usar-o-optional-do-java-8-com-a-jpa-hibernate-c1e48a4aa546](https://medium.com/collabcode/como-usar-o-optional-do-java-8-com-a-jpa-hibernate-c1e48a4aa546)

[11.2] [cr.openjdk.org/~dlsmith/jsr335/jsr335-0.6.2/index.html](https://cr.openjdk.org/~dlsmith/jsr335/jsr335-0.6.2/index.html)

[12] [medium.com/localizalabs/404-204-200-qual-status-a-api-deve-retornar-quando-a-resposta-for-vazia-e4b153936398](https://medium.com/localizalabs/404-204-200-qual-status-a-api-deve-retornar-quando-a-resposta-for-vazia-e4b153936398)

[13] [medium.com/@demisgomes/controle-de-exce%C3%A7%C3%B5es-exception-handler-global-no-spring-boot-d780a19996b2](https://medium.com/@demisgomes/controle-de-exce%C3%A7%C3%B5es-exception-handler-global-no-spring-boot-d780a19996b2)

[13.1]  [www.baeldung.com/exception-handling-for-rest-with-spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)

[13.2]  [www.geeksforgeeks.org/springboot/exception-handling-in-spring-boot](https://www.geeksforgeeks.org/springboot/exception-handling-in-spring-boot/)

[13.3] [medium.com/@felipeacelinoo/como-tratar-exce%C3%A7%C3%B5es-em-uma-api-rest-com-spring-boot-utilizando-restcontrolleradvice-e-af6732559d59](https://medium.com/@felipeacelinoo/como-tratar-exce%C3%A7%C3%B5es-em-uma-api-rest-com-spring-boot-utilizando-restcontrolleradvice-e-af6732559d59)

[13.4] [docs.spring.io/spring-framework/reference/web/webflux/controller/ann-advice.html](https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-advice.html)

[14] [medium.com/@AlexanderObregon/how-spring-boot-configures-custom-argument-resolvers-ed4833420549](https://medium.com/@AlexanderObregon/how-spring-boot-configures-custom-argument-resolvers-ed4833420549)

[15]  [medium.com/@gabrielrufino.js/put-vs-patch-pare-de-agora-escolher-errado-533b8c6058d9](https://medium.com/@gabrielrufino.js/put-vs-patch-pare-de-agora-escolher-errado-533b8c6058d9)

[15.1] la.org/pt-BR/docs/Web/HTTP/Reference/Methods/PUT

[15.2]  [developer.mozilla.org/pt-BR/docs/Web/HTTP/Reference/Methods/PATCH](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Reference/Methods/PATCH)

[16] [medium.com/@AlexanderObregon/hard-delete-vs-soft-delete-logic-in-spring-boot-services-747798a601f9](https://medium.com/@AlexanderObregon/hard-delete-vs-soft-delete-logic-in-spring-boot-services-747798a601f9)

[16.1] [www.baeldung.com/spring-jpa-soft-delete](https://www.baeldung.com/spring-jpa-soft-delete)

[16.2] [medium.com/@samrat.alam/soft-delete-in-spring-boot-jpa-best-practices-real-world-implementation-2d831e60bb3e](https://medium.com/@samrat.alam/soft-delete-in-spring-boot-jpa-best-practices-real-world-implementation-2d831e60bb3e)

[17] [developer.mozilla.org/pt-BR/docs/Web/HTTP/Reference/Status/409](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Reference/Status/409)

[17.1] [developer.mozilla.org/pt-BR/docs/Web/HTTP/Reference/Status/204](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Reference/Status/204)

[17.2] [apichangelog-substack-com.translate.goog/p/http-204-is-the-best-delete-response?_x_tr_sl=en&amp;_x_tr_tl=pt&amp;_x_tr_hl=pt&amp;_x_tr_pto=tc&amp;_x_tr_hist=true](https://apichangelog-substack-com.translate.goog/p/http-204-is-the-best-delete-response?_x_tr_sl=en&_x_tr_tl=pt&_x_tr_hl=pt&_x_tr_pto=tc&_x_tr_hist=true)

[17.3] [developer.mozilla.org/pt-BR/docs/Web/HTTP/Reference/Methods/DELETE](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Reference/Methods/DELETE)

[17.4] [datatracker.ietf.org/doc/html/rfc7231#section-6.5.8](https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.8)

[18] [developer.mozilla.org/pt-BR/docs/Web/HTTP/Reference/Status/201](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Reference/Status/201)
