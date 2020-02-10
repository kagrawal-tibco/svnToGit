# Copyright (c) 2004-2019 TIBCO Software Inc.
# All Rights Reserved.
#
# This software is the confidential and proprietary information of
# TIBCO Software Inc.
#
#==========================================================
# *** Common variables. Modify these only. ***
#==========================================================

source ./setenv.sh

export MEMORY_SIZE=32M

${TIB_JAVA_HOME}/bin/javac -d ../classes -classpath ${STD_EXT_CP_DIRS} -J-Xms${MEMORY_SIZE} ../src/CallRuleFunction.java
