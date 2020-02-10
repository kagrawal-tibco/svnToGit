@echo off
REM Please adapt this script to your environment.

@REM Set JAVANCSS_HOME to the directory this batch file is in.
set JAVANCSS_HOME=C:\Programs1\javancss-32.53

set _JAVA_HOME_ORIG=%JAVA_HOME%
set _CLASSPATH_ORIG=%CLASSPATH%

if NOT "%JAVA_HOME%"=="" goto endif1
	REM #################### EDIT THIS ENVIRONMENT VARIABLE IF NOT ALREADY SET #################
	set JAVA_HOME=c:\jdk1.2.2
:endif1

REM #################### EDIT THIS ENVIRONMENT VARIABLE IF NOT ALREADY SET #################
set CLASSPATH=%JAVANCSS_HOME%\lib\javancss.jar;%JAVANCSS_HOME%\lib\ccl.jar;%JAVANCSS_HOME%\lib\jhbasic.jar;%CLASSPATH%

%JAVA_HOME%\bin\java -classpath %CLASSPATH% javancss.Main -gui -recursive %1 %2 %3 %4 %5 %6 %7 %8 %9

set JAVA_HOME=%_JAVA_HOME_ORIG%
set CLASSPATH=%_CLASSPATH_ORIG%
set _JAVA_HOME_ORIG=
set _CLASSPATH_ORIG=
