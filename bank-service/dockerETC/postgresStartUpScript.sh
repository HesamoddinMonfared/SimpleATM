#!/bin/bash
su -l postgres -c /usr/pgsql-11/bin/initdb
su -l postgres -c "/usr/pgsql-11/bin/pg_ctl -D /var/lib/pgsql/11/data -l /tmp/pg_logfile start"
psql -U postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'bankdb'" | grep -q 1 || psql -U postgres -c "CREATE DATABASE bankdb"
