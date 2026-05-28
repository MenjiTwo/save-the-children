@REM Maven Wrapper script for Windows
@echo off
setlocal

set "MAVEN_PROJECTBASEDIR=%~dp0"
set "WRAPPER_JAR=%~dp0.mvn\wrapper\maven-wrapper.jar"

@REM Find java.exe (handles spaces in JAVA_HOME path)
if defined JAVA_HOME (
    set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
    if not exist "%JAVA_EXE%" (
        echo ERROR: JAVA_HOME is set to "%JAVA_HOME%" but java.exe was not found there.
        echo Please set JAVA_HOME correctly or install Java 22+.
        exit /b 1
    )
) else (
    set "JAVA_EXE=java.exe"
    where java >nul 2>nul
    if %errorlevel% neq 0 (
        echo ERROR: Java is not installed or not in your PATH.
        echo Please install Java 22+ and set JAVA_HOME, or add java to your PATH.
        exit /b 1
    )
)

@REM Download maven-wrapper.jar if needed
if not exist "%WRAPPER_JAR%" (
    echo Downloading Maven Wrapper...
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar' -OutFile '%WRAPPER_JAR%'"
    if not exist "%WRAPPER_JAR%" (
        echo ERROR: Failed to download maven-wrapper.jar. Check your internet connection.
        exit /b 1
    )
)

"%JAVA_EXE%" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
