COMPOSE=docker compose \
	--env-file infra/.env \
	-f infra/docker-compose.yml

APP_CONTAINER=pets_app
DB_CONTAINER=pets_db

build:
	$(COMPOSE) build

up:
	$(COMPOSE) up -d

down:
	$(COMPOSE) down

restart:
	$(COMPOSE) restart

logs:
	$(COMPOSE) logs -f

logs-app:
	$(COMPOSE) logs -f app

logs-db:
	$(COMPOSE) logs -f db

ps:
	$(COMPOSE) ps

exec:
	docker exec -it $(APP_CONTAINER) bash

exec-db:
	docker exec -it $(DB_CONTAINER) bash

psql:
	docker exec -it $(DB_CONTAINER) psql \
	-U postgres \
	-d pets

clean:
	$(COMPOSE) down -v
	rm -rf infra/postgres/data

db-reset:
	$(COMPOSE) down -v
	rm -rf infra/postgres/data
	$(COMPOSE) up -d

mvn-package:
	$(COMPOSE) exec app mvn clean package

mvn-test:
	$(COMPOSE) exec app mvn test

mvn-clean:
	$(COMPOSE) exec app mvn clean

spring-run:
	$(COMPOSE) exec app mvn spring-boot:run

spring-debug:
	$(COMPOSE) exec app bash -c '\
	mvn spring-boot:run \
	-Dspring-boot.run.jvmArguments="\
	-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"'