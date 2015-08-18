@echo off
title Launching Skyolauncher
@echo Setting the APPDATA Path....
set APPDATA=%CD%\data
@echo Running...
java -jar bin\Skyolauncher.jar
@echo Finish!
exit
