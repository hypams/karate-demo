@echo off

rem Change to the directory where the batch file is located
cd /d %~dp0

rem Run Maven test command
.\apache-maven-3.9.9\bin\mvn test -Dkarate.env="dev" -Dkarate.driverConfigs="chrome_local,mac_chrome_local" -Dfeature="login" -Dtest=LoginTest
