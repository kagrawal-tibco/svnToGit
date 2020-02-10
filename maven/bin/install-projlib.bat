@echo off
setlocal EnableExtensions EnableDelayedExpansion
set argCount=0
for %%x in (%*) do set /A argCount+=1
if %argCount% GTR 8 (
	goto printUsage
)
set /A counter=0
for /l %%x in (1, 1, %argCount%) do (
  set /A counter=!counter!+1
  call set currentArg=%%!counter!
  if !currentArg! EQU -p (
    set /a inCounter=!counter!+1
    call set "PROJLIB=%%!inCounter!" 
  )
  if !currentArg! EQU -g (
    set /a inCounter=!counter!+1
    call set "GROUPID=%%!inCounter!"
  )
  if !currentArg! EQU -a (
    set /a inCounter=!counter!+1
    call set "ARTIFACTID=%%!inCounter!" 
  )
  if !currentArg! EQU -v (
    set /a inCounter=!counter!+1
    call set "VERSION=%%!inCounter!" 
  )
)

if "%PROJLIB%" EQU "" (
	goto printUsage
)
if "%GROUPID%" EQU "" (
	goto printUsage
)
if "%ARTIFACTID%" EQU "" (
	goto printUsage
)
if "%VERSION%" EQU "" (
	goto printUsage
)


mvn install:install-file -Dfile=%PROJLIB% -DgroupId=%GROUPID% -DartifactId=%ARTIFACTID% -Dversion=%VERSION% -Dpackaging=projlib
EXIT /B 0
:printUsage
    echo "Usage: install-projlib -p <projlib> -g <groupid> -a <artifactid> -v <version>"
    EXIT /B 0