# echo Copyright (c) 2004-2019 TIBCO Software Inc.
# echo All Rights Reserved.
# echo This software is the confidential and proprietary information of TIBCO Software Inc.
# echo ==========================================================
# echo \*\*\* Common variables. Modify these only.\*\*\*
# echo ==========================================================

export PSP=:
export BE_HOME=%TIBCO_BE_HOME_ESC%
export TIB_JAVA_HOME=%TIBCO_JAVA_HOME_ESC%
export BE_WORKSPACE=%TIBCO_BE_HOME_ESC%/examples/standard/RuleFunctionAPI
export BE_ECLIPSE_HOME=${BE_HOME}/eclipse-platform/eclipse

export CUSTOM_EXT_PREPEND_CP_DIRS=../classes
export CUSTOM_EXT_APPEND_CP_DIRS=.

export STD_EXT_CP_DIRS=${CUSTOM_EXT_PREPEND_CP_DIRS}${PSP}${BE_HOME}/lib/*${PSP}${BE_HOME}/lib/ext/tpcl/*${PSP}${BE_HOME}/lib/ext/tpcl/apache/*${PSP}${BE_HOME}/lib/ext/tibco/*${PSP}${BE_HOME}/lib/ext/tpcl/emf/*${PSP}${CUSTOM_EXT_APPEND_CP_DIRS}
