# Ecommerce Cadena de Cines

Prueba técnica de Desarrollador FullStack: ecommerce de una cadena de cines (ver `retoCP - Fullstack - 2026.pdf`).

## Arquitectura

Monorepo con:

- `backend/` — Maven multi-módulo (Java 11, Spring Boot 2.7.x):
  - `common` — JWT, manejo global de excepciones, logging y configuración de Swagger compartidos por los 3 servicios.
  - `premieres-service` (puerto 8081) — estrenos + emisión de la sesión (JWT de invitado).
  - `candystore-service` (puerto 8082) — catálogo de dulcería.
  - `complete-service` (puerto 8083) — orquesta el pago con PayU sandbox y finaliza la transacción.
- `frontend/` — React (Vite): Home → Login (Invitado) → Dulcería → Pago.
- `db/` — esquema, procedimientos almacenados y datos semilla para SQL Server.
- `docker/` — `docker-compose.yml` para levantar todo el stack.

## Cómo levantarlo

```bash
cd docker
cp .env.example .env
docker compose up --build
```

Servicios resultantes:

- Frontend: http://localhost:3000
- Swagger `premieres-service`: http://localhost:8081/swagger-ui.html
- Swagger `candystore-service`: http://localhost:8082/swagger-ui.html
- Swagger `complete-service`: http://localhost:8083/swagger-ui.html

## Probar un pago (PayU sandbox)

En la pantalla de Pago, usa la tarjeta de prueba de PayU:

- Número: `4097440000000004`
- Fecha de expiración: cualquier fecha futura
- CVV: `321`
- Nombre en la tarjeta: `APPROVED` (fuerza una respuesta aprobada en el sandbox)

## Notas de build

- El contexto de build de cada Dockerfile de servicio Java es `backend/` (no la carpeta del propio servicio), porque cada imagen compila también el módulo `common` vía Maven multi-módulo (`mvn -pl <módulo>,common -am package -DskipTests`).
- Este entorno de desarrollo no tiene Docker instalado; `docker-compose.yml` y los Dockerfiles no se probaron end-to-end aquí. Antes de la entrega, valida con `docker compose config` (sintaxis) y `docker compose up --build` (build real) desde `docker/`.
