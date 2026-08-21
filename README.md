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
- El stack completo (`docker compose up --build` desde `docker/`) fue probado end-to-end: los 6 contenedores (SQL Server, `db-init`, los 3 microservicios y el frontend) levantan correctamente y el flujo de compra completo (Home → Login → Dulcería → Pago → PayU sandbox → `complete`) funciona.

## Compra de entradas

Desde Home, al pulsar "Comprar entradas" en un estreno se abre un selector de
cantidad (precio fijo de S/ 5.00 por entrada). Esa selección se agrega al mismo
carrito de la Dulcería, así que el total mostrado en Dulcería y en Pago combina
**entradas + productos de dulcería** en un solo checkout.

## Decisiones de diseño respecto al documento del reto

- **Layout de Home**: el PDF pide imágenes a la izquierda y texto a la derecha
  en una sola columna. Se optó por una grilla de tarjetas (imagen + texto) más
  un hero superior, siguiendo un mockup de diseño provisto durante el
  desarrollo. Es una decisión de producto consciente, no un descuido.
- **Click en un estreno**: en vez de ir directo a Login, abre primero el
  selector de cantidad de entradas descrito arriba; al confirmar, continúa a
  Login igual que pedía el documento original.
- **Google Sign-In**: no implementado (es opcional según el PDF). Solo existe
  el flujo de invitado.
