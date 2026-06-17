@echo off
echo ===================================================
echo   Starting Save the Children Backend
echo ===================================================
echo.
cd /d "%~dp0backend"

REM Open the frontend immediately so the user doesn't have to wait
start "" "%~dp0frontend\index.html"

call .\mvnw.cmd spring-boot:run
