@ECHO OFF
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

SET BASEDIR=%TIBDG_ROOT%
SET DATA_DIR=activespacesdata
SET HOSTPORT_RAW=localhost:5055
SET TIBDG_CMD="%TIBDG_ROOT%\bin\tibdg.exe"
SET FTL_CMD="%TIBFTL_ROOT%\bin\tibftlserver.exe"
SET URLY_CMD="%TIBDG_ROOT%\bin\urly.exe"
SET GRIDNAME=_default

IF "%1"=="-r" (
    SET HOSTPORT_RAW=%2
) ELSE (
    IF "%1"=="-d" SET DATA_DIR=%2
)

IF "%3"=="-r" (
    SET HOSTPORT_RAW=%4
) ELSE (
    IF "%3"=="-d" SET DATA_DIR=%4
)

SET HOSTPORT=%HOSTPORT_RAW:http://=%

IF "%HOSTPORT%"=="" GOTO :usage
IF "%DATA_DIR%"=="" GOTO :usage
IF "%GRIDNAME%"=="" GOTO :usage

SET FTLCONFIG=%DATA_DIR%\ftl.yaml
SET RSDIR=%DATA_DIR%\realm_data
SET LOGDIR=%DATA_DIR%\logs

SET URL=http://%HOSTPORT%
ECHO Using %URL% for FTL server with grid data at %DATA_DIR% and grid name %GRIDNAME%.

GOTO :main

REM Fall into usage - parameters were invalid.
:usage
ECHO Usage: as-start [-r host:port] [-d data_directory] [-g grid name]
ECHO Default: -r localhost:5055 -d %BASEDIR% -g _default
goto eof

:: function to configure the ftlserver
:start_ftlserver

CALL %URLY_CMD% -f -p "\"server_status\": \"online\"" GET %URL%/api/v1/server >NUL 2>&1
IF %ERRORLEVEL% EQU 0 (
    ECHO FTL server already running
) else (
    ECHO Starting FTL server

    IF NOT EXIST "%RSDIR%" mkdir "%RSDIR%"

    IF NOT EXIST "%FTLCONFIG%" (
       ECHO globals: >> "%FTLCONFIG%"
       ECHO   core.servers: >> "%FTLCONFIG%"
       ECHO     SRV1: %HOSTPORT% >> "%FTLCONFIG%"
       ECHO servers: >> "%FTLCONFIG%"
       ECHO   SRV1: >> "%FTLCONFIG%"
       ECHO   - realm: >> "%FTLCONFIG%"
       ECHO       data: %RSDIR% >> "%FTLCONFIG%"
       ECHO       logfile: %LOGDIR%\rs-log.txt >> "%FTLCONFIG%"
       ECHO services: >> "%FTLCONFIG%"
       ECHO   realm: {} >> "%FTLCONFIG%"

       ECHO Generated FTL configuration file '%FTLCONFIG%' containing:
       ECHO:
       TYPE %FTLCONFIG%
       ECHO:
    ) ELSE (
       REM NOTE: When using a preexisting FTL config file you must ensure the host:port for SRV1
       REM matches the host:port used in this script.
       ECHO Using preexisting FTL configuration file '%FTLCONFIG%' containing:
       ECHO:
       TYPE %FTLCONFIG%
       ECHO:
    )

    IF EXIST "%LOGDIR%\rs-log.txt" DEL /Q "%LOGDIR%\rs-log.txt"
    START "FTL Server" CALL %FTL_CMD% -c "%FTLCONFIG%" -n SRV1 1> "%LOGDIR%\ftl-log.txt"
    TIMEOUT /T 1 > NUL
    :ftl_wait_loop
    CALL %URLY_CMD% -f -p "\"server_status\": \"online\"" GET %URL%/api/v1/server >NUL 2>&1
    IF %ERRORLEVEL% EQU 0 (
        ECHO FTL server started
    ) ELSE (
        ECHO Waiting for FTL server start
        TIMEOUT /T 1 >NUL
        GOTO :ftl_wait_loop
    )
)

CALL %TIBDG_CMD% -g %GRIDNAME% -r %URL% status -j >NUL 2>&1
IF %ERRORLEVEL% NEQ 0 (
    ECHO Configuring grid
    CALL :config_grid
) ELSE (
    ECHO Grid already configured
)
GOTO eof

:config_grid
IF NOT EXIST "%DATA_DIR%\k1_data" MKDIR "%DATA_DIR%\k1_data"
IF NOT EXIST "%DATA_DIR%\n1_data" MKDIR "%DATA_DIR%\n1_data"

set CONFIG_SCRIPT=%DATA_DIR%\config.tibdg
IF NOT EXIST "%CONFIG_SCRIPT%" (
    ECHO grid create copyset_size=1 statekeeper_count=1 >> "%CONFIG_SCRIPT%"
    ECHO keeper create -d %DATA_DIR%\k1_data k1 >> "%CONFIG_SCRIPT%"
    ECHO copyset create cset1 >> "%CONFIG_SCRIPT%"
    ECHO node create -cs cset1 -d %DATA_DIR%\n1_data n1 >> "%CONFIG_SCRIPT%"
    ECHO proxy create p1 >> "%CONFIG_SCRIPT%"

    ECHO table create Employee empId long >> "%CONFIG_SCRIPT%"
    ECHO column create Employee name string >> "%CONFIG_SCRIPT%"
    ECHO column create Employee age long >> "%CONFIG_SCRIPT%"
    ECHO column create Employee joiningDate datetime >> "%CONFIG_SCRIPT%"
    ECHO column create Employee isPermanant long >> "%CONFIG_SCRIPT%"
    ECHO column create Employee salary double >> "%CONFIG_SCRIPT%"
    ECHO column create Employee departments opaque >> "%CONFIG_SCRIPT%"
    ECHO index create Employee index_name name >> "%CONFIG_SCRIPT%"

    ECHO table create Address addressId long >> "%CONFIG_SCRIPT%"
    ECHO column create Address address string >> "%CONFIG_SCRIPT%"
    ECHO index create Address index_address address >> "%CONFIG_SCRIPT%"

    ECHO table create Phone phoneId long >> "%CONFIG_SCRIPT%"
    ECHO column create Phone home string >> "%CONFIG_SCRIPT%"
    ECHO column create Phone mobile string >> "%CONFIG_SCRIPT%"
    ECHO index create Phone index_mobile mobile >> "%CONFIG_SCRIPT%"
)
CALL %TIBDG_CMD% -g %GRIDNAME% -r %URL% -s "%CONFIG_SCRIPT%"
GOTO eof

:start_proc
ECHO starting %1 %2
START "tibdg%1" CALL "%TIBDG_ROOT%\bin\tibdg%1.exe" -g %GRIDNAME% -r %URL% -n %2 --logfile %LOGDIR%\%GRIDNAME%-%2-log.txt
GOTO eof

:wait_for_grid
FOR /L %%i IN (1,1,30) DO (
    CALL %TIBDG_CMD% -g %GRIDNAME% -r %URL% status -j | FINDSTR /C:"online" | FINDSTR /C:"true" 1>NUL
    if ERRORLEVEL 0 (
        ECHO Grid is online
        GOTO eof
    ) else (
        ECHO Waiting for grid to be online (%%i/30)
        TIMEOUT /T 1 > NUL
    )
)
ECHO Grid failed to go online
GOTO eof

:main
IF NOT EXIST "%LOGDIR%" mkdir "%LOGDIR%"

CALL :start_ftlserver

CALL :start_proc keeper k1

CALL :start_proc proxy p1

CALL :start_proc node n1

CALL :wait_for_grid

GOTO eof

:eof
endlocal
