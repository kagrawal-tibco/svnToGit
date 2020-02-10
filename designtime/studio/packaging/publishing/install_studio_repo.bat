@echo off

SETLOCAL 

set BEHOME=%TIBCO_BE_HOME_ESC%
set TIBCOHOME=%TIBCO_HOME_ESC%
set argCount=0
set argCountExpected=1
set exitStatus=0
set SUCCESS=0

set PATH=%TIBCOHOME%/tibcojre64/1.8.0/bin;!PATH!
set CLASSPATH=%BEHOME%/eclipse-platform/eclipse/plugins/org.apache.ant_1.9.4.v201504302020/lib/ant.jar;%BEHOME%/eclipse-platform/eclipse/plugins/org.apache.ant_1.9.4.v201504302020/lib/ant-launcher.jar;!CLASSPATH!

if EXIST %TIBCOHOME%/tibcojre64/1.8.0 (
	set JAVA_HOME=%TIBCOHOME%/tibcojre64/1.8.0
)

echo %JAVA_HOME%

@echo:
echo ###############BusinessEvents Studio Repository Installer###############
@echo:
@echo:

for %%i in (%*) do set /A argCount+=1
IF %argCount% NEQ %argCountExpected% (
goto:usage
)

set executableFile=%~1
If  NOT EXIST "%executableFile%" (
	echo File `%executableFile%' does not exist.
	set exitStatus=1
	goto:batchexit
)
echo File `%executableFile%' exists.  
@echo:

set iniFile=%executableFile:~0,-3%ini
If NOT EXIST "%iniFile%" (
	echo INI file missing. Please provide an INI file for the application.
	set exitStatus=1
	goto:batchexit
	
)
echo File `%iniFile%' exists. 
@echo:

echo Installing... This may take few minutes. 
@echo:

REM calling ant targets from install_studio_repo.xml

java org.apache.tools.ant.Main -buildfile install_studio_repo.xml -DappBinary="%executableFile%" -DappIni="%iniFile%"

goto:finalexit

:usage
echo [USAGE]: install_studio_repo.bat application-path
echo Example: install_studio_repo.bat C:\eclipse\eclipse.exe
set exitStatus=1 
goto:batchexit


:batchexit
IF %exitStatus% == %SUCCESS% (
		echo Installation Complete
) ELSE ( 
		echo Installation Failed
		)
		
:finalexit

ENDLOCAL
