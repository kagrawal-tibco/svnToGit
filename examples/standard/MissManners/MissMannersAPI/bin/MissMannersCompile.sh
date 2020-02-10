#!/bin/sh
# Copyright (c) 2004-2013 TIBCO Software Inc.
# All Rights Reserved.
#
# This software is the confidential and proprietary information of
# TIBCO Software Inc.
#
#========================================================== 
# *** Common variables. Modify these only. ***
#========================================================== 

export PSP=%PS%
export BE_HOME=%TIBCO_BE_HOME_ESC%
export TIB_JAVA_HOME=%TIBCO_JAVA_HOME_ESC%

export MISSMANNERS_HOME=${BE_HOME}/examples/standard/MissManners
export BE_ECLIPSE_HOME=${BE_HOME}/eclipse-platform/eclipse

export CUSTOM_EXT_PREPEND_CP_DIRS=
export CUSTOM_EXT_APPEND_CP_DIRS=

export STD_EXT_CP_DIRS=${CUSTOM_EXT_PREPEND_CP_DIRS}/*${PSP}${BE_HOME}/hotfix/studio/eclipse/plugins/*${PSP}${BE_HOME}/hotfix/lib/*${PSP}${BE_HOME}/lib/*${PSP}${BE_HOME}/hotfix/lib/palettes/*${PSP}${BE_HOME}/lib/palettes/*${PSP}${BE_HOME}/hotfix/lib/ext/tpcl/*${PSP}${BE_HOME}/lib/ext/tpcl/*${PSP}${BE_HOME}/hotfix/lib/ext/tpcl/emf/*${PSP}${BE_HOME}/lib/ext/tpcl/emf/*${PSP}${BE_HOME}/hotfix/lib/ext/tpcl/apache/*${PSP}${BE_HOME}/lib/ext/tpcl/apache/*${PSP}${BE_HOME}/hotfiib/ext/tibco/*${PSP}${BE_HOME}/lib/ext/tibco/*${PSP}${BE_HOME}/hotfix/lib/ext/*${PSP}${BE_HOME}/lib/ext/*${PSP}${BE_ECLIPSE_HOME}/plugins/*${PSP}${BE_HOME}/studio/eclipse/plugins/*${PSP}${CUSTOM_EXT_APPEND_CP_DIRS}/*


export APP_ARGS="-source 1.6"
export MEMORY_SIZE=32M


${TIB_JAVA_HOME}/bin/javac ${APP_ARGS} -d ../classes -J-Xms${MEMORY_SIZE} -classpath ../src:${STD_EXT_CP_DIRS} ../src/manners/MannersTest.java
