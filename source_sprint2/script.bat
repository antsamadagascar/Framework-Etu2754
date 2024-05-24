@echo off

REM Définir les variables de destination
set "bin=.\bin"
set "web=C:\xampp\tomcat\webapps\sprint2"
set "webinf=%web%\WEB-INF"
set "webxml_source=C:\Users\Ny Antsa\Documents\Fianarana\Semestre4\Mr Naina\sprint2\web.xml"
set "JAR_FILE=Framework"

REM Créer le répertoire bin s'il n'existe pas
if not exist "%bin%" (
    mkdir "%bin%"
)

REM Créer le répertoire de destination s'il n'existe pas
if not exist "%webinf%\lib" (
    mkdir "%webinf%\lib"
)

REM Compiler les fichiers Java dans le répertoire de sortie
javac --release 8 -d "%bin%" -cp ".\lib\*" src\annotation\*.java src\controller\*.java src\other\*.java
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
xcopy /Y ".\%JAR_FILE%.jar" "%webinf%\lib"
if %errorlevel% neq 0 (
    echo Erreur lors de la copie du fichier JAR vers %webinf%\lib.
    exit /b %errorlevel%
)

REM Copier le fichier web.xml vers le répertoire WEB-INF
if exist "%webxml_source%" (
    if not exist "%webinf%" (
        mkdir "%webinf%"
    )
    xcopy /Y "%webxml_source%" "%webinf%"
    if %errorlevel% neq 0 (
        echo Erreur lors de la copie du fichier web.xml vers %webinf%.
        exit /b %errorlevel%
    )
) else (
    echo Le fichier web.xml est introuvable.
    exit /b 1
)

REM Créer le fichier WAR
jar cvf sprint2.war -C "%web%" .
if %errorlevel% neq 0 (
    echo Erreur lors de la création du fichier WAR.
    exit /b %errorlevel%
)

REM Déployer le fichier WAR vers le répertoire webapps de Tomcat
xcopy /Y "sprint2.war" "C:\xampp\tomcat\webapps"
if %errorlevel% neq 0 (
    echo Erreur lors de la copie du fichier WAR vers C:\xampp\tomcat\webapps.
    exit /b %errorlevel%
)

echo Opération terminée avec succès.
