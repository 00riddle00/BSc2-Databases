#!/bin/sh
export CLASSPATH=$CLASSPATH:lib/postgresql-42.2.5.jar
javac src/db_auto_nuoma/AutoNuoma.java
java src/db_auto_nuoma/AutoNuoma

