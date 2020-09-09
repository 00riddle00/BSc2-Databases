#!/bin/sh
export CLASSPATH=$CLASSPATH:lib/postgresql-42.2.5.jar

javac -d . AutoNuoma.java Duombaze.java VartotojoSasaja.java
java db_car_rental/AutoNuoma


# Siaip tai norejau sitaip pasileisti, kad butu same kaip intellij, bet kazkodel pyksta kad neranda Main klases (AutoNuoma)

#javac -d out/production/db_car_rental AutoNuoma.java Duombaze.java VartotojoSasaja.java
#java out/production/db_car_rental/db_car_rental/AutoNuoma


