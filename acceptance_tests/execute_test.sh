#!/bin/bash
echo "JAVA_HOME:$JAVA_HOME"
JAR_PATH=build_assets/target
echo "PWD:$PWD"



java -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=y -jar $JAR_PATH/aggregator.statements.prepared-jar-with-dependencies.jar -input=acceptance_tests/resources/simple_test.log -output=acceptance_tests/output/simple_test.log -package=jboss.jdbc.spy





