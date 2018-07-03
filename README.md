Migration Migraine

The examples demonstrate migrating a Java 8 project to Java 9 and beyond. 

1. Building a multi-module Java 9 project by hand 
2. Building a multi-module Java 9 project using the maven jar and maven dependencies plugin
3. Building a multi-module Java 9 project with the jmod plugin (intermodular dependecies not supported yet)
4. Building a multi-module Java 9 project with the jmod and jlink plugin (intermodular dependencies not supported yet)

It is advised to take the 2nd project as a basis since the first option is too much typing (not DRY) and third third and fourth option 
are still using the experimental JMod and JLink plugin who don't support inter-module dependencies. Their use is decribed in https://dzone.com/articles/jdk9-howto-create-a-java-run-time-image-with-maven

1. Building a multi module project by hand

1.1 From the project root Java_Module_Demo

  javac -d mods --module-source-path src 
    src/com.vijfhart.cursus.demo/module-info.java 
    src/com.vijfhart.cursus.demo/com/vijfhart/cursus/demo/Demo.java 
    src/com.vijfhart.cursus.democlient/module-info.java 
    src/com.vijfhart.cursus.democlient/com/vijfhart/cursus/democlient/Main.java

1.2 Create JAR files

  jar -cf target\demo.jar -C mods\com.vijfhart.cursus.demo .
  jar -cfe target\democlient.jar com.vijfhart.cursus.democlient.Main -C mods\com.vijfhart.cursus.democlient .

1.3 Execute

  java --module-path target\demo.jar;target\democlient.jar --module com.vijfhart.cursus.democlient

1.4 Linking to custom JRE

  jmod create --class-path target\demo.jar jmods\demo.jmod
  jmod create --class-path target\democlient.jar jmods\democlient.jmod

1.5 Check jmods
  jmod describe jmods\demo.jmod
  jmod describe jmods\democlient.jmod

1.7 Use jlink

  jlink --module-path "%JAVA_HOME%\jmods;jmods" 
    --add-modules com.vijfhart.cursus.demo,com.vijfhart.cursus.democlient 
    --launcher demo=com.vijfhart.cursus.democlient --output democlient

1.8 Execute

  democlient\bin\demo
  
2 Building a multi-module Java 9 project using the maven - jar and - dependency plugin 

Create a toolchains.xml in user_home/.m2 for example:

<toolchains>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>9</version>
      <vendor>oracle</vendor>
    </provides>
    <configuration>
	    <jdkHome>C:\Program Files\Java\jdk-9.0.4</jdkHome>
    </configuration>
  </toolchain>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>10</version>
      <vendor>oracle</vendor>
    </provides>
    <configuration>
      <jdkHome>C:\Program Files\Java\jdk-10.0.1</jdkHome>
    </configuration>
  </toolchain>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>11</version>
      <vendor>openjdk</vendor>
    </provides>
    <configuration>
      <jdkHome>C:\Program Files\Java\jdk-11</jdkHome>
    </configuration>
  </toolchain>
</toolchains>

Use profiles to invoke the JDK from command line with resp.

  mvn clean install -P java9
  mvn clean install -P java10
  mvn clean install -P java11
  
<profiles>
  <profile>
    <id>java9</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <properties>
      <maven.compiler.source>1.9</maven.compiler.source>
      <maven.compiler.target>1.9</maven.compiler.target>
      <maven.compiler.release>9</maven.compiler.release>
      <jdk.vendor>oracle</jdk.vendor>
      <jdk.version>9</jdk.version>
    </properties>
  </profile>
  <profile>
    <id>java10</id>
    <activation>
      <activeByDefault>false</activeByDefault>
    </activation>
    <properties>
      <maven.compiler.source>1.10</maven.compiler.source>
      <maven.compiler.target>10</maven.compiler.target>
      <maven.compiler.release>10</maven.compiler.release>
      <jdk.vendor>oracle</jdk.vendor>
      <jdk.version>10</jdk.version>
    </properties>
  </profile>
  <profile>
    <id>java11</id>
    <activation>
      <activeByDefault>false</activeByDefault>
    </activation>
    <properties>
      <maven.compiler.source>1.11</maven.compiler.source>
	    <maven.compiler.target>11</maven.compiler.target>
	    <maven.compiler.release>11</maven.compiler.release>
	    <jdk.vendor>openjdk</jdk.vendor>
	    <jdk.version>11</jdk.version>
    </properties>
  </profile>
</profiles>

1. From the project root Java_Module_Maven_Demo
  mvn clean install package
2. Execute
  java -p target\jars -m com.vijfhart.cursus.democlient/com.vijfhart.cursus.democlient.Main
  

Experimental. Only to produce jmods and jlink image. Intermodular dependecies not supported yet.

3 Building a multi-module Java 9 project with the jmod plugin 

3.1 From the project root Java_Module_Demo_JMod
  mvn clean install package

3.2 Check
  dir com.vijfhart.cursus.demo/target/jmods
  dir com.vijfhart.cursus.democlient/target/jmods
 
 
4 Building a multi-module Java 9 project with the jmod and jlink plugin (intermodular dependencies not supported yet)

4.1 From the project root Java_Module_Demo_JLink
  mvn clean install package

4.2 Check
  dir com.vijfhart.cursus.jlink\target

4.3 Unzip
  jar -xvf com.vijfhart.cursus.jlink\target\com.vijfhart.cursus.jlink-1.0-SNAPSHOT.zip

4.4 Execute
  
  
  

  
  


  
  
  
  
  
  


