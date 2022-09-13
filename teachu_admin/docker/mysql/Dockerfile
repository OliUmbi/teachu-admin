FROM mysql:8.0.29
COPY data/teachu.sql /docker-entrypoint-initdb.d/a_schema.sql
COPY data/teachu-data.sql /docker-entrypoint-initdb.d/b_data.sql

EXPOSE 3306
