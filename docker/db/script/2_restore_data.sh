#!/bin/bash
pg_restore -p 5432 -U postgres -d dvdrental /docker-entrypoint-initdb.d/dvdrental.tar