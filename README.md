This project contains all the work requested for the "Automation Challenge" Task
==================================================================================
The difference between this project & the first one is that, here we are using "Maven"
- All the jars that were present in the "Libs" Folder are removed
- We have created a normal java class with main method
	- In order to act the application starting point for the Jar files (as the xml file is not executable)
- The created Fat Jar can be executed with the normal jar command with passing the testng.xml full path parameter(java -jar [FatJar_Name] [path to the "testng.xml" file]) 
- We are now having 2 batch files:
	- Automation Challenge >> to open the cmd at the current working folder
	- Run_Jar >> to run the created maven Fat Jar
