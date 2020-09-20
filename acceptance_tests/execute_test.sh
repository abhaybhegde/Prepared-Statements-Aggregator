#!/bin/bash
echo "JAVA_HOME:$JAVA_HOME"
JAR_PATH=../build_assets/target

java -jar $JAR_PATH/aggregator.statements.prepared-jar-with-dependencies.jar 



