@echo off
echo ===================================================
echo   Starting Save the Children Backend
echo ===================================================
echo.
cd /d "%~dp0backend"

REM Launch a background process to wait 8 seconds (allowing Spring Boot to start), then open the frontend automatically
start /b cmd /c "timeout /t 8 /nobreak >nul & start """" ""%~dp0frontend\index.html"""

call .\mvnw.cmd spring-boot:run
