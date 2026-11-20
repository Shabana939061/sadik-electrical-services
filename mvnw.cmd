@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Maven Start Up Batch script
@REM ----------------------------------------------------------------------------

@IF "%DEBUG%"=="" @ECHO OFF
@SETLOCAL EnableExtensions EnableDelayedExpansion

SET "EXEC_DIR=%~dp0"
SET "MAVEN_PROJECTBASEDIR=%EXEC_DIR%"
IF NOT "%JAVA_HOME%"=="" (
    SET "JAVACMD=%JAVA_HOME%\bin\java.exe"
) ELSE (
    SET "JAVACMD=java.exe"
)

IF NOT EXIST "%JAVACMD%" (
    IF EXIST "C:\Program Files\Java\jdk-21.0.12\bin\java.exe" (
        SET "JAVACMD=C:\Program Files\Java\jdk-21.0.12\bin\java.exe"
    )
)

SET "WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
SET "WRAPPER_PROPERTIES=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties"

IF NOT EXIST "%WRAPPER_JAR%" (
    powershell -NoProfile -ExecutionPolicy Bypass -Command "& { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar', '%WRAPPER_JAR%') }"
)

"%JAVACMD%" -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -jar "%WRAPPER_JAR%" %*
IF ERRORLEVEL 1 EXIT /B %ERRORLEVEL%
