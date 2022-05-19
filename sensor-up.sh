#!/bin/bash
mvn exec:java -Dexec.mainClass="com.distribuidos.system.SensorLoad" -Dexec.args="$1 $2 $3"