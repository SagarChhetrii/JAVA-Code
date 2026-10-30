# Product Catalog API

A small Spring Boot REST API for managing products in memory. Data resets when
the application stops.

## Requirements

- Java 17+
- Maven 3.9+

## Run

```bash
mvn spring-boot:run
```

The API starts at `http://localhost:8080`.

## Endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/products` | List all products |
| GET | `/products/{id}` | Find one product |
| POST | `/products` | Add a product |
| PUT | `/products/{id}` | Update a product |
| DELETE | `/products/{id}` | Delete a product |

Example request:

```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{"id":3,"name":"Monitor","price":8500}'
```

## Test the API

Start the application in one terminal, then run:

```bash
./test.sh
```
