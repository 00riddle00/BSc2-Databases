#!/bin/sh
export CLASSPATH=$CLASSPATH:lib/postgresql-42.2.5.jar
javac src/AutoNuoma.java
java src/AutoNuoma

