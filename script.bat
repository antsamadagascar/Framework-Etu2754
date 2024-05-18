@echo off

REM Définir les variables de destination
set "destination=..\sprint-1\WEB-INF\lib\"
set "bin=.\bin"
set "webinf=..\sprint-1\WEB-INF\"
set "JAR_FILE=Framework"

REM Créer le répertoire bin s'il n'existe pas
if not exist "%bin%" (
    mkdir "%bin%"
)

REM Créer le répertoire de destination s'il n'existe pas
if not exist "%destination%" (
    mkdir "%destination%"
)

REM Compiler les fichiers Java dans le répertoire de sortie
javac --release 8 -d "%bin%" -cp ".\lib\*" src\*.java
if %errorlevel% neq 0 (
    echo Erreur de compilation des fichiers Java.
    exit /b %errorlevel%
)

REM Créer un fichier JAR contenant les classes compilées 
jar cvf ".\%JAR_FILE%.jar" -C "%bin%" .
if %errorlevel% neq 0 (
    echo Erreur lors de la création du fichier JAR.
    exit /b %errorlevel%
)

REM Copier le JAR vers la destination 
xcopy /Y ".\%JAR_FILE%.jar" "%destination%"
if %errorlevel% neq 0 (
    echo Erreur lors de la copie du fichier JAR vers %destination%.
    exit /b %errorlevel%
)

REM Copier le fichier web.xml vers le répertoire WEB-INF
xcopy /Y "web.xml" "%webinf%"
if %errorlevel% neq 0 (
    echo Erreur lors de la copie du fichier web.xml vers %webinf%.
    exit /b %errorlevel%
)

echo Opération terminée avec succès.
