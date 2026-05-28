@REM Maven Wrapper script for Windows
@echo off
setlocal EnableDelayedExpansion

@REM Get project base directory and REMOVE trailing backslash
@REM (trailing \ before a quote escapes it, breaking argument parsing)
set "MAVEN_PROJECTBASEDIR=%~dp0"
if "!MAVEN_PROJECTBASEDIR:~-1!"=="\" set "MAVEN_PROJECTBASEDIR=!MAVEN_PROJECTBASEDIR:~0,-1!"

set "WRAPPER_JAR=!MAVEN_PROJECTBASEDIR!\.mvn\wrapper\maven-wrapper.jar"

@REM =====================================================
@REM Find java.exe
@REM =====================================================
set "JAVA_EXE="

@REM Check JAVA_HOME first
if not defined JAVA_HOME goto :try_path_java
if not exist "%JAVA_HOME%\bin\java.exe" goto :java_home_invalid
set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
goto :java_found

:java_home_invalid
echo ERROR: JAVA_HOME is set to "%JAVA_HOME%" but java.exe was not found there.
echo Please set JAVA_HOME correctly or install Java 17+.
exit /b 1

:try_path_java
where java >nul 2>nul
if !errorlevel! neq 0 goto :no_java
set "JAVA_EXE=java.exe"
goto :java_found

:no_java
echo ERROR: Java is not installed or not in your PATH.
echo Please install Java 17+ and set JAVA_HOME, or add java to your PATH.
exit /b 1

:java_found

@REM =====================================================
@REM Download maven-wrapper.jar if needed
@REM =====================================================
if exist "!WRAPPER_JAR!" goto :wrapper_ready

echo Downloading Maven Wrapper...
powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $wrapperDir = Split-Path -Parent '!WRAPPER_JAR!'; if (-not (Test-Path $wrapperDir)) { New-Item -ItemType Directory -Path $wrapperDir -Force | Out-Null }; Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar' -OutFile '!WRAPPER_JAR!'"

if not exist "!WRAPPER_JAR!" goto :download_fail
goto :wrapper_ready

:download_fail
echo ERROR: Failed to download maven-wrapper.jar. Check your internet connection.
exit /b 1

:wrapper_ready
@REM =====================================================
@REM Run Maven
@REM =====================================================
"!JAVA_EXE!" -Dmaven.multiModuleProjectDirectory="!MAVEN_PROJECTBASEDIR!" -classpath "!WRAPPER_JAR!" org.apache.maven.wrapper.MavenWrapperMain %*
exit /b !errorlevel!
