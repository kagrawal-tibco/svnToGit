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


# ========================================================================
#  Miss Manners guest data.
#  Edit the file name for the number of guests.

export INPUT_DATA_PATH=${MISSMANNERS_HOME}/MissMannersAPI/inputData/mann16.dat

# ========================================================================


export MEMORY_SIZE=32M
# Arguments passed to Java
export JAVA_ARGS=-Xms${MEMORY_SIZE}

# arguments to MannersTest are TRA file,  CDD file, processing unit name, EAR file, input data file
${TIB_JAVA_HOME}/bin/java ${JAVA_ARGS} -classpath ../classes:${STD_EXT_CP_DIRS} manners.MannersTest ${BE_HOME}/bin/be-engine.tra ${MISSMANNERS_HOME}/MissMannersProject/Deployment/MissManners_API.cdd default MissManners_API.ear ${INPUT_DATA_PATH}