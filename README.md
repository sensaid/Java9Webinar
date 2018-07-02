The repo contains four projects:

1. Building a multi-module Java 9 project by hand 
2. Building a multi-module Java 9 project using the maven jar and maven dependencies plugin
3. Building a multi-module Java 9 project with the jmod plugin (intermodular dependecies not supported yet)
4. Building a multi-module Java 9 project with the jmod and jlink plugin (intermodular dependencies not supported yet)

Its best to take the 2nd project as a basis since the first option is too much typing and third third and fourth option 
are still using the experimental JMod and JLink plugin whic doesn't support inter-module dependencies.

Building a multi module project by hand

1. From the project root Java_Module_Demo
  javac -d mods --module-source-path src 
    src/com.vijfhart.cursus.demo/module-info.java 
    src/com.vijfhart.cursus.demo/com/vijfhart/cursus/demo/Demo.java 
    src/com.vijfhart.cursus.democlient/module-info.java 
    src/com.vijfhart.cursus.democlient/com/vijfhart/cursus/democlient/Main.java
2. Create JAR files
  jar -cf target\demo.jar -C mods\com.vijfhart.cursus.demo .
  jar -cfe target\democlient.jar com.vijfhart.cursus.democlient.Main -C mods\com.vijfhart.cursus.democlient .
3. Execute
  java --module-path target\demo.jar;target\democlient.jar --module com.vijfhart.cursus.democlient
4. Linking to cusom JRE
  jmod create --class-path target\demo.jar jmods\demo.jmod
  jmod create --class-path target\democlient.jar jmods\democlient.jmod
5. Check jmods
  jmod describe jmods\demo.jmod
  jmod describe jmods\democlient.jmod
6. Use the linker
  jlink --module-path "%JAVA_HOME%\jmods;jmods" 
    --add-modules com.vijfhart.cursus.demo,com.vijfhart.cursus.democlient 
    --launcher demo=com.vijfhart.cursus.democlient --output democlient
7. Execute
  democlient\bin\demo
  
Building a multi-module Java 9 project using the maven jar and maven dependencies plugin

1. From the project root Java_Module_Maven_Demo
  mvn clean install package
2. Execute
  java -p target\modules -m com.vijfhart.cursus.democlient/com.vijfhart.cursus.democlient.Main
  
Building a multi-module Java 9 project with the jmod plugin (intermodular dependecies not supported yet)
1. From the project root Java_Module_Demo_JMod
  mvn clean install package
2. Check
  dir com.vijfhart.cursus.demo/target/jmods
  dir com.vijfhart.cursus.democlient/target/jmods
  
Building a multi-module Java 9 project with the jmod and jlink plugin (intermodular dependencies not supported yet)
1. From the project root Java_Module_Demo_JLink
  mvn clean install package
2. Check
  dir com.vijfhart.cursus.jlink\target
3. Unzip
  jar -xvf com.vijfhart.cursus.jlink\target\com.vijfhart.cursus.jlink-1.0-SNAPSHOT.zip
4. Execute
  
  
  

  
  


  
  
  
  
  
  


