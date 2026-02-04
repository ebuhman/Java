@echo off
javac -cp "lib/*" -d bin src/rpg/*.java src/rpg/characters/*.java src/rpg/rpgtests/*.java
java -cp "lib/*;bin" org.junit.runner.JUnitCore rpg.GameTest
pause