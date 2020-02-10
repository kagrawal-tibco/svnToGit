#!/bin/sh
BE_HOME=%TIBCO_BE_HOME_ESC%
mvn install:install-file -Dfile=${BE_HOME}/maven/bin/be-maven-plugin-5.6.1.jar -DpomFile=${BE_HOME}/maven/bin/pom.xml 
