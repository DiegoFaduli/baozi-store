# Baozi Store - API REST

Trabalho da disciplina de Desenvolvimento Web Back-End (UNINTER).

API simples para controlar clientes, produtos e pedidos da Baozi Store, uma loja que vende pão chinês (baozi).

## Tecnologias

- Java 17
- Spring Boot 3
- Spring Data JPA
- Banco H2 (salvo em arquivo na pasta `data/`)
- Maven

## Como rodar

1. Abrir o projeto na IDE (IntelliJ, Eclipse ou VS Code) como projeto Maven
2. Rodar a classe `BaoziStoreApplication`
3. A API sobe em `http://localhost:8080`

Console do H2: `http://localhost:8080/h2-console`
JDBC URL: `jdbc:h2:file:./data/baozistore` | usuário: `sa` | senha: `senha123`

## Endpoints

### Clientes
| Método | URL | Descrição |
|---|---|---|
| POST | /clientes | cadastra um cliente |
| GET | /clientes | lista todos |
| GET | /clientes/{id} | busca pelo id |
| PUT | /clientes/{id} | atualiza |
| DELETE | /clientes/{id} | apaga |

### Produtos
| Método | URL | Descrição |
|---|---|---|
| POST | /produtos | cadastra um produto |
| GET | /produtos | lista todos |
| GET | /produtos/{id} | busca pelo id |
| PUT | /produtos/{id} | atualiza |
| DELETE | /produtos/{id} | apaga |

### Pedidos
| Método | URL | Descrição |
|---|---|---|
| POST | /pedidos | registra um pedido |
| GET | /pedidos | lista todos |
| GET | /pedidos/{id} | busca pelo id |
| PUT | /pedidos/{id} | atualiza |
| DELETE | /pedidos/{id} | apaga |

## Exemplos de JSON

Cliente:
```json
{
  "nome": "Diego5346425",
  "clienteDesde": "2026-10-03"
}
```

Produto:
```json
{
  "nome": "Baozi de Porco",
  "preco": 8.50,
  "estoque": true
}
```

Pedido:
```json
{
  "clienteId": 1,
  "produtoId": 1,
  "quantidade": 6
}
```
