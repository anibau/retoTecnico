#!/usr/bin/env bash
set -e

SQLCMD=/opt/mssql-tools/bin/sqlcmd
DB_HOST="${DB_HOST:-sqlserver}"
DB_PORT="${DB_PORT:-1433}"
SA_PASSWORD="${SA_PASSWORD:?SA_PASSWORD is required}"
DB_NAME="${DB_NAME:-CinesDb}"

echo "Esperando a que SQL Server esté disponible en ${DB_HOST},${DB_PORT}..."
ATTEMPTS=0
MAX_ATTEMPTS=30
until $SQLCMD -S "${DB_HOST},${DB_PORT}" -U sa -P "${SA_PASSWORD}" -Q "SELECT 1" > /dev/null 2>&1; do
  ATTEMPTS=$((ATTEMPTS + 1))
  if [ "$ATTEMPTS" -ge "$MAX_ATTEMPTS" ]; then
    echo "SQL Server no respondió luego de ${MAX_ATTEMPTS} intentos. Abortando."
    exit 1
  fi
  echo "SQL Server no está listo aún (intento ${ATTEMPTS}/${MAX_ATTEMPTS})..."
  sleep 3
done

echo "SQL Server disponible. Creando base de datos ${DB_NAME} si no existe..."
$SQLCMD -S "${DB_HOST},${DB_PORT}" -U sa -P "${SA_PASSWORD}" -Q "IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = '${DB_NAME}') CREATE DATABASE ${DB_NAME};"

run_sql_file() {
  local file="$1"
  echo "Ejecutando ${file}..."
  $SQLCMD -S "${DB_HOST},${DB_PORT}" -U sa -P "${SA_PASSWORD}" -d "${DB_NAME}" -i "${file}"
}

for f in ./schema/*.sql; do
  [ -e "$f" ] || continue
  run_sql_file "$f"
done

for f in ./procedures/*.sql; do
  [ -e "$f" ] || continue
  run_sql_file "$f"
done

for f in ./seed/*.sql; do
  [ -e "$f" ] || continue
  run_sql_file "$f"
done

echo "Inicialización de base de datos completada."
