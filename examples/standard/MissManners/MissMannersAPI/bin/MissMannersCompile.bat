rem Copyright (c) 2004-2013 TIBCO Software Inc.
rem All Rights Reserved.
rem
rem This software is the confidential and proprietary information of
rem TIBCO Software Inc.
rem
rem========================================================== 
rem *** Common variables. Modify these only. ***
rem========================================================== 


call .\setenv

rem Other arguments to application, JVM etc.
SET APP_ARGS=-source 1.6
SET MEMORY_SIZE=32M

%TIB_JAVA_HOME%\bin\javac %APP_ARGS% -d ..\classes -J-Xms%MEMORY_SIZE% -classpath ..\src;%STD_EXT_CP_DIRS% ..\src\manners\MannersTest.java