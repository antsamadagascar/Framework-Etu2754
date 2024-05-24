@echo off

REM Compilation 
javac --release 8 -d ".\bin" -cp ".\lib\*" src\annotation\*.java src\controller\*.java src\other\*.java
if %errorlevel% neq 0 (
    echo Erreur de compilation des fichiers Java.
    exit /b %errorlevel%
)

REM Ajout de la classe à tester dans "classes"
xcopy /E /I /Y bin\controller "..\sprint-2\WEB-INF\classes"
if %errorlevel% neq 0 (
    echo Erreur lors de la copie des fichiers de classe.
    exit /b %errorlevel%
)

REM Exécution du test
cd bin
java other.Main
cd ..
