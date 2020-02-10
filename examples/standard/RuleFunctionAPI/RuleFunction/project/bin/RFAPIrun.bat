rem Copyright (c) 2004-2019 TIBCO Software Inc.
rem All Rights Reserved.
rem
rem This software is the confidential and proprietary information of
rem TIBCO Software Inc.
rem========================================================== 
rem *** Common variables. Modify these only. ***
rem========================================================== 

call ".\setenv.bat"

rem========================================================== 
rem  Edit the rule function name and data input.
rem========================================================== 
SET FUNCTION_NAME=/RuleFunctions/testPrint
SET DATA_INPUT=1

SET MEMORY_SIZE=32M

rem Arguments passed to Java
SET JAVA_ARGS=-Xms%MEMORY_SIZE%

rem arguments are TRA file,  CDD file, processing unit name, EAR file, function name and data input
%TIB_JAVA_HOME%\bin\java %JAVA_ARGS% -classpath %STD_EXT_CP_DIRS% CallRuleFunction %BE_HOME%\bin\be-engine.tra %RFAPI_HOME%\RFAPI\Deployments\RFConfig.cdd default %RFAPI_HOME%\RFAPI\RFAPI.ear %FUNCTION_NAME% %DATA_INPUT%
