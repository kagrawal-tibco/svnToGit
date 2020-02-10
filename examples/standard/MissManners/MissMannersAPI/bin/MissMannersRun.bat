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


rem ========================================================================
rem  Miss Manners guest data.
rem  Edit the file name for the number of guests.

SET INPUT_DATA_PATH=%MISSMANNERS_HOME%\MissMannersAPI\inputData\mann16.dat

rem ========================================================================


SET MEMORY_SIZE=32M
rem Arguments passed to Java
SET JAVA_ARGS=-Xms%MEMORY_SIZE%

rem arguments to MannersTest are TRA file,  CDD file, processing unit name, EAR file, input data file
%TIB_JAVA_HOME%\bin\java %JAVA_ARGS% -classpath ..\classes;%STD_EXT_CP_DIRS% manners.MannersTest %BE_HOME%\bin\be-engine.tra %MISSMANNERS_HOME%\MissMannersProject\Deployment\MissManners_API.cdd default MissManners_API.ear %INPUT_DATA_PATH%
