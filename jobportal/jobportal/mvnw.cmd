@echo off
SET MAVEN_VERSION=3.9.6
SET MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%

IF NOT EXIST "%MAVEN_HOME%\bin\mvn.cmd" (
    echo Downloading Apache Maven %MAVEN_VERSION%...
    mkdir "%MAVEN_HOME%" 2>nul
    powershell -Command "Invoke-WebRequest -Uri 'https://downloads.apache.org/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile '%TEMP%\maven.zip'; Expand-Archive '%TEMP%\maven.zip' -DestinationPath '%USERPROFILE%\.m2\wrapper\dists\'; Rename-Item '%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%' '%MAVEN_HOME%'"
)

"%MAVEN_HOME%\bin\mvn.cmd" %*
