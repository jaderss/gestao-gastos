# gestao-gastos

[![CI](https://github.com/jaderss/gestao-gastos/actions/workflows/ci.yml/badge.svg)](https://github.com/jaderss/gestao-gastos/actions/workflows/ci.yml)

An expense tracking service built on Spring Boot 4.1 and Java 25. It looks up a holder's cards by CPF and records, categorizes and searches their card transactions. Cards live in a relational database through JPA and Flyway, and transactions in MongoDB.

## What it does

- `POST /cards/search` returns the cards of a holder, found by a validated CPF in the request body.
- `POST /transactions` records a transaction for a user, with an amount in a given currency.
- `PUT /transactions/{id}/category` sets the category of a transaction.
- `GET /transactions` lists a user's transactions on a given day, and `GET /transactions/recent` those of the last N days.
- `GET /transactions/categories` suggests categories the user has already used, by prefix.
- Rejects invalid input as RFC 9457 Problem Details that name the offending fields or parameters.

## Card data

The service never holds what it does not need.

- A card is stored as its brand, its last four digits and its expiry. The full number and the CVC are never stored, so they cannot leak.
- Card numbers come back masked, as `**** **** **** 4242`.
- The CPF travels in a POST body rather than a query string, so it stays out of access logs and browser history, and it is checked with the CPF check digits before any lookup.
- The seed data is fictional: invented names and the example CPFs found in documentation of the CPF algorithm.

There is no authentication, so the service belongs behind a gateway that authenticates callers and decides whose cards and transactions each may see.

## Time and money

- Amounts are `BigDecimal` with an ISO 4217 currency, stored in MongoDB as Decimal128 rather than as strings or doubles. An amount with more decimals than its currency allows is rejected.
- The record time comes from an injected `Clock`, so the tests pin it.
- A day is the half-open range from midnight to the next midnight in `expenses.time-zone`, `America/Sao_Paulo` by default.
- The last N days means from exactly N times 24 hours ago up to now, for N from 1 to 366. Lists are not paged, which suits a single user's history but not a bulk export.

## Run it

Java 25 and Docker are required. Spring Boot starts MongoDB from `compose.yaml` and stops it on exit; the card database is an in-memory H2 that Flyway creates and seeds.

```bash
./mvnw spring-boot:run
```

```bash
curl -s localhost:8080/cards/search -H 'Content-Type: application/json' -d '{"cpf": "123.456.789-09"}'
```

```json
[
  {"id": 1, "userId": 1, "holderName": "Ada Example", "brand": "VISA", "number": "**** **** **** 4242", "expiry": "2029-07"},
  {"id": 2, "userId": 1, "holderName": "Ada Example", "brand": "MASTERCARD", "number": "**** **** **** 4444", "expiry": "2028-02"}
]
```

```bash
curl -s localhost:8080/transactions -H 'Content-Type: application/json' \
  -d '{"userId": 1, "description": "Groceries", "amount": 152.37, "currency": "BRL"}'
curl -s -X PUT localhost:8080/transactions/<id>/category -H 'Content-Type: application/json' -d '{"category": "Food"}'
curl -s 'localhost:8080/transactions?userId=1&date=2026-10-21'
curl -s 'localhost:8080/transactions/recent?userId=1&days=7'
curl -s 'localhost:8080/transactions/categories?userId=1&prefix=fo'
```

## API

| Endpoint | Input | Rules |
|---|---|---|
| `POST /cards/search` | `cpf` | valid CPF, with or without punctuation |
| `POST /transactions` | `userId`, `description`, `amount`, `currency` | positive user id, description up to 200 characters, positive amount of up to 15 integer digits in the currency's scale |
| `GET /transactions/{id}` | | 404 if unknown |
| `PUT /transactions/{id}/category` | `category` | not blank, up to 50 characters; 404 if unknown |
| `GET /transactions` | `userId`, `date` | ISO date such as `2026-10-21` |
| `GET /transactions/recent` | `userId`, `days` | 1 to 366 |
| `GET /transactions/categories` | `userId`, `prefix` | not blank, up to 50 characters, matched case-insensitively and literally |

## Tests

```bash
./mvnw verify
```

Docker must be running. The build runs the tests and checks formatting with Spotless and Palantir Java Format. Repository and service tests run against H2 with the Flyway migration and against MongoDB 7.0 in Testcontainers, so the queries, the day boundaries and the Decimal128 mapping are checked against the real databases. MongoDB stays on 7.0 because every 8.x and 9.0 release refuses to start on Linux kernels 6.19 to 7.0.13, which includes the Ubuntu 26.04 GitHub runners (SERVER-121912). Controller tests cover every validation rule and error response.

## Stack

Java 25, Spring Boot 4.1, Spring Data JPA, Spring Data MongoDB, Flyway, H2, MongoDB 7.0, Bean Validation, Testcontainers, JUnit 6, AssertJ.
