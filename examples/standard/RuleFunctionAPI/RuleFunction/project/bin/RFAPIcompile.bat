rem Copyright (c) 2004-2019 TIBCO Software Inc.
rem All Rights Reserved.
rem
rem This software is the confidential and proprietary information of
rem TIBCO Software Inc.
rem
rem========================================================== 
rem *** Common variables. Modify these only. ***
rem========================================================== 

call ".\setenv.bat"

SET MEMORY_SIZE=32M

%TIB_JAVA_HOME%\bin\javac -d ..\classes -J-Xms%MEMORY_SIZE% -classpath %STD_EXT_CP_DIRS% ..\src\CallRuleFunction.java
