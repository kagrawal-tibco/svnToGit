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

export FUNCTION_NAME=/RuleFunctions/testPrint
export DATA_INPUT=1

export MEMORY_SIZE=32M


${TIB_JAVA_HOME}/bin/java -classpath ${STD_EXT_CP_DIRS} CallRuleFunction ${BE_HOME}/bin/be-engine.tra ${BE_WORKSPACE}/RFAPI/Deployments/RFConfig.cdd default %TIBCO_BE_HOME_ESC%/examples/standard/RuleFunctionAPI/RFAPI/RFAPI.ear ${FUNCTION_NAME} ${DATA_INPUT}
