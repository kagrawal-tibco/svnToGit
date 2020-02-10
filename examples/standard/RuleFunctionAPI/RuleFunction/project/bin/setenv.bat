rem Copyright (c) 2004-2019 TIBCO Software Inc.
rem All Rights Reserved.
rem
rem This software is the confidential and proprietary information of rem TIBCO Software Inc.
rem
rem==========================================================
rem *** Common variables. Modify these only.***
rem==========================================================

SET PSP=;
SET BE_HOME=%TIBCO_BE_HOME_ESC%
SET TIB_JAVA_HOME=%TIBCO_JAVA_HOME_ESC%
SET BE_WORKSPACE=%TIBCO_BE_HOME_ESC%\workspace
SET RFAPI_HOME=%BE_HOME%\examples\standard\RuleFunctionAPI
SET BE_ECLIPSE_HOME=%BE_HOME%\eclipse-platform\eclipse

SET CUSTOM_EXT_PREPEND_CP_DIRS=..\classes
SET CUSTOM_EXT_APPEND_CP_DIRS=.

SET STD_EXT_CP_DIRS=%CUSTOM_EXT_PREPEND_CP_DIRS%%PSP%%BE_HOME%\lib\*%PSP%%BE_HOME%\lib\ext\tpcl\*%PSP%%BE_HOME%\lib\ext\tpcl\apache\*%PSP%%BE_HOME%\lib\ext\tibco\*%PSP%%BE_HOME%\lib\ext\tpcl\emf\*%PSP%%CUSTOM_EXT_APPEND_CP_DIRS%
