@echo off

set destination="..\Test\WEB-INF\lib\"
set bin=".\bin"
set "JAR_FILE=framework-mvc-2754-v1"

REM Copier les fichiers Java vers un dossier temporaire
xcopy /E /I /Y src\mg\itu\nyantsa\other\*.java "temp"
xcopy /E /I /Y src\mg\itu\nyantsa\annotation\*.java "temp"
xcopy /E /I /Y src\mg\itu\nyantsa\annotation\auth\*.java "temp"
xcopy /E /I /Y src\mg\itu\nyantsa\annotation\methods\*.java "temp"
xcopy /E /I /Y src\mg\itu\nyantsa\annotation\field\*.java "temp"
xcopy /E /I /Y src\mg\itu\nyantsa\auth\*.java "temp"
xcopy /E /I /Y src\mg\itu\nyantsa\exception\*.java "temp"
xcopy /E /I /Y src\mg\itu\nyantsa\controller\*.java "temp"
xcopy /E /I /Y src\mg\itu\nyantsa\servlet\*.java "temp"

REM Compiler les fichiers Java
javac -parameters --release 19 -d "bin" -cp ".\lib\*" -Xlint:unchecked temp\*.java

REM Créer le fichier JAR
jar cvf ".\%JAR_FILE%.jar" -C "%bin%" .

REM Copier le JAR vers le dossier cible
xcopy /Y ".\%JAR_FILE%.jar" %destination%

REM Nettoyer : supprimer le dossier temporaire
rmdir /S /Q temp
