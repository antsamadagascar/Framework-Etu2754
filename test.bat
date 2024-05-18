@echo off 
javac --release 8 -d ".\bin" -cp ".\lib\*" src\*.java
cd bin
java other.Main
cd .. 