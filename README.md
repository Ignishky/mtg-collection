# MTG-COLLECTION

## Prerequisite

- Java 17
- Docker 20.10

## Build

All the generation is done with the wrapped gradle.

```shell
./gradlew build
```

## Run

### Start a mongo container
```shell
docker run -p 27017:27017 mongo:5.0.3
```
TODO: Use a volume to persist data between run

### Start the server
```shell
java -jar build/libs/mtg-collection-0.0.1-SNAPSHOT.jar 
```

## Init

1. Loading of cards blocks from Scryfall into the mongo container
```shell
curl -X PUT 'http://localhost:8080/sets'
```

2. Get all blocks from Mongo
```shell
curl -X GET 'http://localhost:8080/sets'
```