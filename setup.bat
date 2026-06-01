@echo off
setlocal EnableDelayedExpansion

echo ===================================================
echo   Save the Children - Backend Auto-Setup Script
echo   (Windows Only)
echo ===================================================
echo.

REM =====================================================
REM STEP 0: Detect the script's own directory
REM =====================================================
cd /d "%~dp0"

REM =====================================================
REM STEP 1: Check if Java is installed
REM =====================================================
echo [0/3] Checking prerequisites...

where java >nul 2>nul
if !errorlevel! equ 0 goto :java_in_path

REM Java not in PATH, check JAVA_HOME
if not defined JAVA_HOME goto :no_java
if not exist "%JAVA_HOME%\bin\java.exe" goto :java_home_bad
echo Found Java at JAVA_HOME: %JAVA_HOME%
goto :java_ok

:java_in_path
echo Java found!
goto :java_ok

:java_home_bad
echo.
echo [ERROR] JAVA_HOME is set to "%JAVA_HOME%" but java.exe was not found there.
echo.
echo FIX: Install Java 17 or higher from https://adoptium.net/
echo      Then set JAVA_HOME to the installation directory.
echo.
pause
exit /b 1

:no_java
echo.
echo [ERROR] Java is NOT installed or NOT in your PATH.
echo.
echo FIX: Install Java 17 or higher from https://adoptium.net/
echo      Make sure to check "Add to PATH" during installation.
echo.
pause
exit /b 1

:java_ok
REM Print java version OUTSIDE of any if-block to avoid parentheses issues
for /f "tokens=*" %%V in ('java -version 2^>^&1 ^| findstr /i "version"') do echo %%V
echo.

REM =====================================================
REM STEP 2: Find MySQL executable
REM =====================================================
set "MYSQL_EXE="

REM Check if mysql is already in PATH
where mysql >nul 2>nul
if !errorlevel! equ 0 (
    set "MYSQL_EXE=mysql"
    goto :mysql_found
)

REM Search common XAMPP and MySQL installation paths
set "SEARCH_1=C:\xampp\mysql\bin\mysql.exe"
set "SEARCH_2=C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
set "SEARCH_3=C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe"
set "SEARCH_4=C:\Program Files (x86)\MySQL\MySQL Server 8.0\bin\mysql.exe"
set "SEARCH_5=C:\ProgramData\MySQL\MySQL Server 8.0\bin\mysql.exe"
set "SEARCH_6=C:\wamp64\bin\mysql\mysql8.0.31\bin\mysql.exe"
set "SEARCH_7=C:\laragon\bin\mysql\mysql-8.0.30-winx64\bin\mysql.exe"
set "SEARCH_8=C:\Program Files\MySQL\MySQL Server 9.0\bin\mysql.exe"
set "SEARCH_9=C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql.exe"

if exist "!SEARCH_1!" set "MYSQL_EXE=!SEARCH_1!" & goto :mysql_found
if exist "!SEARCH_2!" set "MYSQL_EXE=!SEARCH_2!" & goto :mysql_found
if exist "!SEARCH_3!" set "MYSQL_EXE=!SEARCH_3!" & goto :mysql_found
if exist "!SEARCH_4!" set "MYSQL_EXE=!SEARCH_4!" & goto :mysql_found
if exist "!SEARCH_5!" set "MYSQL_EXE=!SEARCH_5!" & goto :mysql_found
if exist "!SEARCH_6!" set "MYSQL_EXE=!SEARCH_6!" & goto :mysql_found
if exist "!SEARCH_7!" set "MYSQL_EXE=!SEARCH_7!" & goto :mysql_found
if exist "!SEARCH_8!" set "MYSQL_EXE=!SEARCH_8!" & goto :mysql_found
if exist "!SEARCH_9!" set "MYSQL_EXE=!SEARCH_9!" & goto :mysql_found

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

if "!MYSQL_EXE!"=="" goto :skip_db_setup
if not exist "!MYSQL_EXE!" goto :mysql_not_found
goto :mysql_found

:mysql_not_found
echo [ERROR] File not found: !MYSQL_EXE!
pause
exit /b 1

:mysql_found
echo MySQL found at: !MYSQL_EXE!
echo.

REM =====================================================
REM STEP 3: Setup MySQL Database
REM =====================================================
echo [1/3] Setting up the MySQL Database...
echo.

set "DB_USER=root"
set "DB_PASS=root1234"

REM Try default credentials first
echo      Trying default credentials (root / root1234)...
"!MYSQL_EXE!" -u root -proot1234 -e "SELECT 1" >nul 2>nul
if !errorlevel! equ 0 goto :mysql_connected

REM Try root with empty password (common default)
echo      Default password failed. Trying root with no password...
"!MYSQL_EXE!" -u root --skip-password -e "SELECT 1" >nul 2>nul
if !errorlevel! equ 0 (
    set "DB_PASS="
    goto :mysql_connected
)

REM Ask the user for their credentials
echo.
echo [WARNING] Could not connect with default credentials.
echo      Make sure MySQL server is RUNNING first!
echo.
echo      Please enter your MySQL credentials below.
echo.
set /p "DB_USER=     MySQL username (default: root): "
if "!DB_USER!"=="" set "DB_USER=root"
set /p "DB_PASS=     MySQL password (press Enter if no password): "

REM Try user-provided credentials
if "!DB_PASS!"=="" (
    "!MYSQL_EXE!" -u !DB_USER! --skip-password -e "SELECT 1" >nul 2>nul
) else (
    "!MYSQL_EXE!" -u !DB_USER! -p!DB_PASS! -e "SELECT 1" >nul 2>nul
)
if !errorlevel! neq 0 goto :mysql_connect_fail

:mysql_connected
echo      Connected to MySQL successfully!
echo.

REM Update application.properties with the correct credentials
echo      Updating application.properties with your MySQL credentials...
set "PROPS_FILE=%~dp0backend\src\main\resources\application.properties"

> "!PROPS_FILE!" (
    echo spring.application.name=infoman-backend
    echo server.port=8080
    echo spring.datasource.url=jdbc:mysql://localhost:3306/savethechildren_volunteer_db?useSSL=false^&allowPublicKeyRetrieval=true^&serverTimezone=Asia/Manila
    echo spring.datasource.username=!DB_USER!
    echo spring.datasource.password=!DB_PASS!
    echo spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    echo spring.jpa.hibernate.ddl-auto=none
    echo spring.jpa.show-sql=true
    echo spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    echo spring.jpa.properties.hibernate.format_sql=true
)
echo      application.properties updated!
echo.

REM Run schema
echo      Running schema.sql...
if "!DB_PASS!"=="" (
    "!MYSQL_EXE!" -u !DB_USER! --skip-password < db\schema.sql
) else (
    "!MYSQL_EXE!" -u !DB_USER! -p!DB_PASS! < db\schema.sql
)
if !errorlevel! neq 0 goto :schema_fail

REM Run seed data
echo      Running seed.sql...
if "!DB_PASS!"=="" (
    "!MYSQL_EXE!" -u !DB_USER! --skip-password < db\seed.sql
) else (
    "!MYSQL_EXE!" -u !DB_USER! -p!DB_PASS! < db\seed.sql
)
if !errorlevel! neq 0 goto :seed_fail

echo Database setup complete! 8 tables created and 10 mock applicants inserted.
echo.
goto :db_done

:mysql_connect_fail
echo.
echo [ERROR] Cannot connect to MySQL with the provided credentials.
echo.
echo Common fixes:
echo   1. Make sure MySQL server is RUNNING (check XAMPP Control Panel or Services).
echo   2. Double-check your MySQL username and password.
echo   3. Try connecting manually: mysql -u root -p
echo.
pause
exit /b 1

:schema_fail
echo [ERROR] Failed to execute schema.sql
pause
exit /b 1

:seed_fail
echo [ERROR] Failed to execute seed.sql
pause
exit /b 1

:skip_db_setup
echo Skipping database setup. You will need to run schema.sql and seed.sql manually.
echo.

:db_done

REM =====================================================
REM STEP 4: Install Java Dependencies and Compile
REM =====================================================
echo [2/3] Installing Java dependencies (this may take a few minutes on first run)...
echo.

cd /d "%~dp0backend"

call .\mvnw.cmd clean install -DskipTests
if !errorlevel! neq 0 goto :maven_fail

echo.
echo Dependencies installed and project compiled successfully!
echo.
goto :start_server

:maven_fail
echo.
echo [ERROR] Maven build failed. Common fixes:
echo   1. Make sure you have an internet connection (Maven downloads dependencies).
echo   2. Make sure Java 17+ is installed and JAVA_HOME is set correctly.
echo   3. If JAVA_HOME has spaces in the path, try wrapping it in quotes.
echo   4. Try deleting the ".mvn" folder and re-running this script.
echo.
pause
exit /b 1

:start_server
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
