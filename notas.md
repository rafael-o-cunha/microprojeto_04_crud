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


- [ ] realizar primeira conexão e consulta ao banco de dados para testar
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

- [ ] criar funcionalidade de consulta de lista de dados

---

## criar funcionalidade de consultar unidade (5)

- [ ] criar funcionalidade de consultar uma unidade

---

## criar funcionalidade de edição (6)

- [ ] criar funcionalidde de edição de uma unidade

---

## criar deleção de unidade de registro (7)

- [ ] criar funcionalidade de seleção de unidade a partir da listagem de dados
- [ ] usar soft Delete.

---

## criar cadastro de unidade de registro (9)

- [ ] cadastrar pet

---

### Comandos usados

- limpar projeto e realizar rebuild baixando dependências se necessário.
- ```b
  mvn clean package
  ```

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


[4] https://stackoverflow.com/questions/3835612/remote-debugging-tomcat-with-eclipse 

[4.1] https://medium.com/@maneakanksha772/debugging-java-inside-a-docker-container-a-survival-guide-c2eee1655434
