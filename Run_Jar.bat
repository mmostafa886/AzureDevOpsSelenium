%~d1
cd "%~p1"
call cmd /C

cd target
copy "AutomationChallenge_Maven-0.0.1-SNAPSHOT.jar"  "../AutomationChallenge_Maven-0.0.1-SNAPSHOT.jar"

cd ../src/challenge/
copy "testng.xml"  "../../testng.xml"

cd ../../

Java -jar AutomationChallenge_Maven-0.0.1-SNAPSHOT.jar testng.xml

del /f AutomationChallenge_Maven-0.0.1-SNAPSHOT.jar
del /f testng.xml