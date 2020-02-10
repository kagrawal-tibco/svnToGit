@ECHO OFF
set project="ftlChannel"
if not "%1"=="" (
    set project=%1
)
%BE_HOME%\bin\be-engine --propFile %BE_HOME%\bin\be-engine.tra -u default -c %project%/ftl.cdd %project%.ear
@PAUSE

