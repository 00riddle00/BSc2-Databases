#!/bin/sh
export CLASSPATH=$CLASSPATH:lib/postgresql-42.2.5.jar

javac -d . AutoNuoma.java Duombaze.java VartotojoSasaja.java
java db_auto_nuoma/AutoNuoma


# Siaip tai norejau sitaip pasileisti, kad butu same kaip intellij, bet kazkodel pyksta kad neranda Main klases (AutoNuoma)

#javac -d out/production/db_auto_nuoma AutoNuoma.java Duombaze.java VartotojoSasaja.java
#java out/production/db_auto_nuoma/db_auto_nuoma/AutoNuoma


