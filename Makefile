.PHONY: setup
setup:
	docker pull adoptopenjdk/openjdk11

	docker pull postgres

	docker-compose up

.PHONY: start
start:
	docker stop postgresdb || true && docker rm postgresdb || true

	docker stop drone-app || true && docker rm -f drone-app || true

	docker-compose up

.PHONY: unit-test
unit-test:
	mvn clean compile test

.PHONY: integration-test
integration-test:
	mvn clean verify -P integration-test

.PHONY: stop
stop:
	mvn spring-boot:stop

	docker stop postgresdb || true

	docker stop drone-app || true