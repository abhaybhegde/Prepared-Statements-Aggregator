#!/bin/bash

PROJECT_ROOT=`pwd`
echo "Currently in $PROJECT_ROOT.."
mvn clean install -Dbuild.profile.id=dev -f=build_assets/pom.xml

