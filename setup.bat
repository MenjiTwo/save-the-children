@echo off
echo ===================================================
echo   Save the Children - Backend Auto-Setup Script
echo ===================================================
echo.

echo [1/3] Setting up the MySQL Database...
echo Make sure MySQL is running on port 3306 with root:root1234
mysql -u root -proot1234 < db\schema.sql
mysql -u root -proot1234 < db\seed.sql
if %errorlevel% neq 0 (
    echo [ERROR] Failed to connect to MySQL. Please check your credentials and make sure MySQL is in your PATH.
    pause
    exit /b %errorlevel%
)
echo Database setup complete!
echo.

echo [2/3] Installing Dependencies (Like requirements.txt for Java)...
cd backend
call .\mvnw.cmd clean install -DskipTests
if %errorlevel% neq 0 (
    echo [ERROR] Failed to download Java dependencies.
    pause
    exit /b %errorlevel%
)
echo Dependencies installed!
echo.

echo [3/3] Starting the Spring Boot API Server...
call .\mvnw.cmd spring-boot:run

pause
