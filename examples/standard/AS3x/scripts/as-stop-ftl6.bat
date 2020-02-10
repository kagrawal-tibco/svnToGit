@ECHO OFF
::
:: Copyright (c) 2016-$Date: 2017-06-08 12:49:17 -0700 (Thu, 08 Jun 2017) $ TIBCO Software Inc.
:: All Rights Reserved. Confidential & Proprietary.
:: For more information, please contact:
:: TIBCO Software Inc., Palo Alto, California, USA

setlocal EnableDelayedExpansion

IF "%TIBDG_ROOT%"=="" (
    IF EXIST "setup.bat" (
       call setup.bat
    )
)

IF "!TIBDG_ROOT!"=="" (
    echo "Please set TIBDG_ROOT to the ActiveSpaces installation directory."
    GOTO eof
)

SET TIBDG_CMD="%TIBDG_ROOT%\bin\tibdg.exe"
SET FTL_CMD="%TIBFTL_ROOT%\bin\tibftladmin.bat"
SET URLY_CMD="%TIBDG_ROOT%\bin\urly.exe"
SET GRIDNAME=_default

IF "%1"=="" set HOSTPORT_RAW=localhost:5055
IF "%1"=="-r" set HOSTPORT_RAW=%2

set HOSTPORT=%HOSTPORT_RAW:http://=%

IF "%HOSTPORT%"=="" (
    ECHO Syntax: as-stop [-r host:port]
    GOTO eof
)

set URL=http://%HOSTPORT%
ECHO Using %URL% for FTL server and grid name %GRIDNAME%.

ECHO Stopping proxy p1
CALL %TIBDG_CMD% -g %GRIDNAME% -r %URL% proxy stop p1

ECHO Stopping node n1
CALL %TIBDG_CMD% -g %GRIDNAME% -r %URL% node stop n1

ECHO Stopping keeper k1
CALL %TIBDG_CMD% -g %GRIDNAME% -r %URL% keeper stop k1

CALL %URLY_CMD% -f GET %URL%/api/v1/server >NUL 2>&1
IF %ERRORLEVEL% EQU 0 (
    ECHO Stopping FTL server
    CALL %FTL_CMD% -rs %HOSTPORT% --shutdown
)

:eof
endlocal
