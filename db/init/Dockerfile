FROM mcr.microsoft.com/mssql-tools:latest

WORKDIR /app

COPY schema ./schema
COPY procedures ./procedures
COPY seed ./seed
COPY init/entrypoint.sh ./entrypoint.sh

RUN chmod +x ./entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]
