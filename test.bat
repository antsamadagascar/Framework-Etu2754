@echo off 
rem Compilation 
javac --release 8 -d ".\bin" -cp ".\lib\*" Compile\*.java

REM Ajout de la classe a tester dans "classes"
xcopy /E /I /Y bin\controller "..\Test\WEB-INF\classes\controller"

REM Execution de test
cd bin
java other.Main
cd .. 