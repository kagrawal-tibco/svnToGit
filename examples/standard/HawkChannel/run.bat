@ECHO OFF
set BE_HOME=%BE_HOME%
%BE_HOME%\bin\be-engine --propFile %BE_HOME%\bin\be-engine.tra -u default -c HawkChannel/hawk.cdd HawkChannel.ear
@PAUSE

