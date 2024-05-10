@echo off
setlocal

@REM 
set "LOCAL_NAME=%cd%"
cd ..
set "ACTUAL=%cd%\"
call set "LOCAL_NAME=%%LOCAL_NAME:%ACTUAL%=%%"
cd "%cd%\%LOCAL_NAME%"
set "TEMP=%cd%\%LOCAL_NAME%"
if exist "%TEMP%" (
  rmdir /s /q "%TEMP%"
)
mkdir "%TEMP%"
cd %TEMP%

@REM MAKE WEB-INF DIR
set "WEB-INF=WEB-INF"
mkdir %WEB-INF%
cd %WEB-INF%
set "LIB_DIR=lib"
mkdir %LIB_DIR%
set "CLASSES_DIR=classes"
mkdir %CLASSES_DIR%
cd ..
cd ..

@REM COMPLILE JAVA SOURCES
@REM set "WORKING_DIRECTORY=%cd%\Working directory"
cd "%ACTUAL%%LOCAL_NAME%\src"
echo %cd%
javac -cp .;"C:\xampp\tomcat\lib\servlet-api.jar" -d "%TEMP%\%WEB-INF%\%LIB_DIR%" *.java

@REM @REM MAKE JAR FILE TO SEND TO TOMCAT
cd "%TEMP%\%WEB-INF%\%LIB_DIR%"
jar -cvf "%LOCAL_NAME%.jar" *

@REM @REM COPY WEB.XML FILE TO TEMP DIRECTORY
copy "%ACTUAL%%LOCAL_NAME%\web.xml" "%TEMP%\%WEB-INF%"

@REM COPY .JSP FILES TO TEMP DIRECTORY
copy "%ACTUAL%%LOCAL_NAME%\*.jsp" "%TEMP%"

for /d %%i in (*) do rmdir /s /q "%%i"

@REM SEND TEMP DIRECTORY TO TOMCAT
set "WEBAPPS_DIR=C:\xampp\tomcat\webapps"
cd "%ACTUAL%%LOCAL_NAME%\%LOCAL_NAME%"
jar -cvf "%LOCAL_NAME%.war" *
move "%LOCAL_NAME%.war" "%WEBAPPS_DIR%"