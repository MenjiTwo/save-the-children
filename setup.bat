@echo off
setlocal EnableDelayedExpansion

echo ===================================================
echo   Save the Children - Backend Auto-Setup Script
echo   (Windows Only)
echo ===================================================
echo.

REM =====================================================
REM STEP 0: Detect the script's own directory
REM This ensures the script works no matter WHERE it is
REM double-clicked from, even on a different drive letter.
REM =====================================================
cd /d "%~dp0"

REM =====================================================
REM STEP 1: Check if Java is installed
REM =====================================================
echo [0/3] Checking prerequisites...

where java >nul 2>nul
if %errorlevel% neq 0 (
    if defined JAVA_HOME (
        if exist "%JAVA_HOME%\bin\java.exe" (
            echo Found Java at JAVA_HOME: %JAVA_HOME%
        ) else (
            echo.
            echo [ERROR] JAVA_HOME is set to "%JAVA_HOME%" but java.exe was not found there.
            echo.
            echo FIX: Install Java 22 or higher from https://adoptium.net/
            echo      Then set JAVA_HOME to the installation directory.
            echo.
            pause
            exit /b 1
        )
    ) else (
        echo.
        echo [ERROR] Java is NOT installed or NOT in your PATH.
        echo.
        echo FIX: Install Java 22 or higher from https://adoptium.net/
        echo      Make sure to check "Add to PATH" during installation.
        echo.
        pause
        exit /b 1
    )
) else (
    echo Java found!
    java -version 2>&1 | findstr /i "version"
)
echo.

REM =====================================================
REM STEP 2: Find MySQL executable
REM Most XAMPP and MySQL installs do NOT add mysql to PATH
REM so we search common locations automatically.
REM =====================================================
set "MYSQL_EXE="

REM Check if mysql is already in PATH
where mysql >nul 2>nul
if %errorlevel% equ 0 (
    set "MYSQL_EXE=mysql"
    goto :mysql_found
)

REM Search common XAMPP and MySQL installation paths
for %%P in (
    "C:\xampp\mysql\bin\mysql.exe"
    "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
    "C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe"
    "C:\Program Files (x86)\MySQL\MySQL Server 8.0\bin\mysql.exe"
    "C:\ProgramData\MySQL\MySQL Server 8.0\bin\mysql.exe"
    "C:\wamp64\bin\mysql\mysql8.0.31\bin\mysql.exe"
    "C:\laragon\bin\mysql\mysql-8.0.30-winx64\bin\mysql.exe"
) do (
    if exist %%P (
        set "MYSQL_EXE=%%~P"
        goto :mysql_found
    )
)

REM If still not found, ask the user
echo.
echo [ERROR] Could not find mysql.exe on your system.
echo.
echo Common fixes:
echo   1. If you use XAMPP, it is usually at: C:\xampp\mysql\bin\mysql.exe
echo   2. If you use MySQL Installer, it is usually at:
echo      C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe
echo.
set /p "MYSQL_EXE=Please paste the FULL path to your mysql.exe (or press Enter to skip DB setup): "

if "!MYSQL_EXE!"=="" (
    echo Skipping database setup. You will need to run schema.sql and seed.sql manually.
    goto :skip_db
)

if not exist "!MYSQL_EXE!" (
    echo [ERROR] File not found: !MYSQL_EXE!
    pause
    exit /b 1
)

:mysql_found
echo MySQL found at: !MYSQL_EXE!
echo.

REM =====================================================
REM STEP 3: Setup MySQL Database
REM =====================================================
echo [1/3] Setting up the MySQL Database...
echo      Connecting as root to localhost:3306...
echo.

"!MYSQL_EXE!" -u root -proot1234 -e "SELECT 1" >nul 2>nul
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Cannot connect to MySQL with user=root, password=root1234
    echo.
    echo Common fixes:
    echo   1. Make sure MySQL server is RUNNING (check XAMPP Control Panel or Services).
    echo   2. The default credentials are: username=root, password=root1234
    echo      If your password is different, edit backend\src\main\resources\application.properties
    echo.
    pause
    exit /b 1
)

"!MYSQL_EXE!" -u root -proot1234 < db\schema.sql
if %errorlevel% neq 0 (
    echo [ERROR] Failed to execute schema.sql
    pause
    exit /b 1
)

"!MYSQL_EXE!" -u root -proot1234 < db\seed.sql
if %errorlevel% neq 0 (
    echo [ERROR] Failed to execute seed.sql
    pause
    exit /b 1
)

echo Database setup complete! 8 tables created and 10 mock applicants inserted.
echo.

:skip_db

REM =====================================================
REM STEP 4: Install Java Dependencies and Compile
REM =====================================================
echo [2/3] Installing Java dependencies (this may take a few minutes on first run)...
echo.

cd /d "%~dp0backend"

call .\mvnw.cmd clean install -DskipTests
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Maven build failed. Common fixes:
    echo   1. Make sure you have an internet connection (Maven downloads dependencies).
    echo   2. Make sure Java 22+ is installed and JAVA_HOME is set correctly.
    echo   3. If JAVA_HOME has spaces in the path (e.g. "Program Files"), try setting
    echo      it to the short path or reinstall Java to a path without spaces.
    echo.
    pause
    exit /b 1
)

echo.
echo Dependencies installed and project compiled successfully!
echo.

REM =====================================================
REM STEP 5: Start the Spring Boot Server
REM =====================================================
echo [3/3] Starting the Spring Boot API Server on http://localhost:8080 ...
echo.
echo    Once you see "Tomcat started on port 8080", the server is ready!
echo    Test it by opening: http://localhost:8080/api/catalog/skills
echo.
echo    Press Ctrl+C to stop the server.
echo ===================================================
echo.

call .\mvnw.cmd spring-boot:run

pause
