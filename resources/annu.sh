#!/bin/bash
# Script Shell pour créer dynamiquement une base de données PostgreSQL sous Ubuntu

# Création d'un utilisateur PostgreSQL
echo "Création d'un utilisateur PostgreSQL :"
echo "Nom :"
read user
echo "Mot de passe :"
read passwd

dbname=annu

# Création de la BDD 'annu' et en attributer les droits à l'utilisateur
sudo su postgres -c psql << EOF
CREATE USER $user;
ALTER ROLE $user WITH CREATEDB;
ALTER USER $user WITH ENCRYPTED PASSWORD '$passwd';
CREATE DATABASE $dbname;
ALTER DATABASE $dbname OWNER TO $user;
EOF

# Exécute des requêtes SQL contenues dans un fichier .sql
# Création des tables de la BDD
psql -h localhost -U $user -d $dbname < annu-create.sql
# Remplissage de la BDD
psql -h localhost -U $user -d $dbname < annu-populate.sql
