@echo off
echo ============================================
echo  Iniciando Servicio Web de Autenticacion...
echo ============================================
cd /d "%~dp0"
set JAVA_HOME=C:\Program Files\Java\jdk-21
.\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
pause
