**HOW TO:**
<br/><br/>
**- Run the application:**
1. Download JAVA JDK <br/>(for instance Amazon Corretto - instructions
   here: https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/windows-info.html) <br/>
2. Download the application uber JAR <br/>
3. Execute command: <br/>
   `[path-to-your-jdk-location]\java.exe -jar [uber-jar-name].jar` <br/>
   (ex. java.exe -jar nasa.jar)

<br/>**- Test the application:**
1. Execute cURL: <br/>
   `curl -X GET 'http://localhost:8080/nasa/api/rovers/curiosity/photos/recent?camera=navcam&days=5'`
   
<br/>**- Build uber jar:** <br/>
1. Execute command: <br/>
   `java -jar payara-micro.jar --deploy nasa.war --outputUberJar nasa.jar`
