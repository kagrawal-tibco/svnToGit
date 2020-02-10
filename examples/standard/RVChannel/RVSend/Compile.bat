set RVHOME=C:\tibco\tibrv\8.1
set JRE_HOME=C:\tibco\jre\1.5.0
set Path=%JRE_HOME%\bin;%RVHOME%\bin;
set classpath=%RVHOME%\lib\rvconfig.jar;%RVHOME%\lib\tibrvj.jar;%RVHOME%\lib\tibrvjweb.jar;%RVHOME%\lib\tibrvnative.jar;%RVHOME%\lib\tibrvnativesd.jar;%RVHOME%\lib\tibrvjsd.jar;
javac tibrvmultisend.java
set classpath=.;%classpath%