You need to be in the root directly for both running code and testing code
/finance-app

Dependencies:
Java 17
Maven
(make sure Maven is isntalled, mvn --vesrion if not install it before continue)
JUnit 4 & 5

Get the projected opened
Clone the repository: https://github.com/your-repo/finance-app.git
OR
Click on the .zipped finance-app and Extract All...

If needde, get to the root level. cd finance-app

1) 
clean it: mvn clean install
2) 
run it: java -jar target/finance-app.jar 
(if not working use this)
java -jar target/finance-app-1.0-SNAPSHOT-jar-with-dependencies.jar
  
## if for some reason the above instructions not working run this commands in excact sequence: (this will test the code and run it)
mvn clean                         
mvn package
java -jar target/finance-app-1.0-SNAPSHOT-jar-with-dependencies.jar 

Please clean the credentais.csv file in the root level if any tests failss
If you want to run just tests -> mvn clean test

Import in Eclipse

Click **File** → **Import**.
Select **Existing Maven Projects** and click **Next**.
Browse to the extracted project folder and select it.
Click **Finish**.
Ensure JDK and Maven are configured in the project settings.
*

