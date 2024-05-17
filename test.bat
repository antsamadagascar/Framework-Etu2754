@echo off 
javac --release 8 -d ".\bin" -cp ".\lib\*" Compile\*.java
cd bin
java other.Main
cd .. 