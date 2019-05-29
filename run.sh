#!/bin/sh
export CLASSPATH=$CLASSPATH:lib/postgresql-42.2.5.jar

javac -d . AutoNuoma.java Duombaze.java VartotojoSasaja.java
#javac -d out/production/db_auto_nuoma AutoNuoma.java Duombaze.java VartotojoSasaja.java

java db_auto_nuoma/AutoNuoma
#java out/production/db_auto_nuoma/db_auto_nuoma/AutoNuoma


