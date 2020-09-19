#!/bin/bash

PROJECT_ROOT=`pwd`
echo "Currently in $PROJECT_ROOT.."
mvn clean install -Dskip.integration.tests=true -Dskip.unit.tests=false -f=build_assets/pom.xml

