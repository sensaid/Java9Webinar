**Java 9 Webinar**
	
The examples demonstrate migrating a Java multi module project to Java 9 and beyond. 

1. Building a multi-module Java 9 project by hand 
2. Building a multi-module Java 9 project using the maven jar and maven dependencies plugin
3. Building a multi-module Java 9 project with the jmod plugin (intermodular dependecies not supported yet)
4. Building a multi-module Java 9 project with the jmod and jlink plugin (intermodular dependencies not supported yet)

It is advised to take the 2nd project as a basis since the first option is too much typing (not DRY) and the third and fourth option 
are still using the experimental JMod and JLink plugin who don't support inter-module dependencies. Their use is decribed in https://dzone.com/articles/jdk9-howto-create-a-java-run-time-image-with-maven
	

**1. Building a multi module project by hand**
	
**1.1. From the project root Java_Module_Demo**
	
  	javac -d mods --module-source-path src 
		src/com.vijfhart.cursus.demo/module-info.java
		src/com.vijfhart.cursus.demo/com/vijfhart/cursus/demo/Demo.java
		src/com.vijfhart.cursus.democlient/module-info.java
		src/com.vijfhart.cursus.democlient/com/vijfhart/cursus/democlient/Main.java
	
**1.2. Create JAR files**
	
  	jar -cf target\demo.jar -C mods\com.vijfhart.cursus.demo .
  	jar -cfe target\democlient.jar com.vijfhart.cursus.democlient.Main -C mods\com.vijfhart.cursus.democlient .
	
**1.3. Execute**
	
  	java --module-path target\demo.jar;target\democlient.jar --module com.vijfhart.cursus.democlient
	
**1.4. Create jmod files**
	
  	jmod create --class-path target\demo.jar jmods\demo.jmod
  	jmod create --class-path target\democlient.jar jmods\democlient.jmod
	
**1.5. Check jmods**

  	jmod describe jmods\demo.jmod
  	jmod describe jmods\democlient.jmod
	
**1.6. Use jlink to link a custom JRE**
	
	jlink
		--module-path "%JAVA_HOME%\jmods;jmods" 
		--add-modules com.vijfhart.cursus.demo,com.vijfhart.cursus.democlient 
		--launcher demo=com.vijfhart.cursus.democlient 
		--output democlient
	
**1.7. Execute**
	
  	democlient\bin\demo
	  

**2. Building a multi-module Java 9 project using the maven - jar and - dependency plugin** 

**2.1. Create a toolchains.xml in user_home/.m2 for example:**

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
	
	
**2.2 Add profiles to invoke the JDK from command line with resp.**

  	mvn clean package -P java9
  	mvn clean package -P java10
  	mvn clean package -P java11
	  
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
	
 
**2.3 The Maven compiler plugin uses an old version of asm.jar incompatible with Java10 or 11. To force a newer version, add this 	 
  dependency to the compiler plugin.

	<plugin>
  	  <groupId>org.apache.maven.plugins</groupId>
  	  <artifactId>maven-compiler-plugin</artifactId>
  	  <dependencies>
    	    <dependency>
      	      <groupId>org.ow2.asm</groupId>
      	      <artifactId>asm</artifactId>
      	      <version>6.2</version>
    	    </dependency>
  	  </dependencies>
	</plugin>


**2.4. From the project root Java_Module_Maven_Demo**

  	mvn clean install package

**2.5. Execute**

  	java -p target\jars -m com.vijfhart.cursus.democlient/com.vijfhart.cursus.democlient.Main
	 
	 
	

**Experimental. Only to produce jmods and jlink image. Intermodular dependecies not supported yet.**
	

**3. Building a multi-module Java 9 project with the jmod plugin**


**3.1 From the project root Java_Module_Demo_JMod**

  	mvn clean install package


**3.2 Check**

  	dir com.vijfhart.cursus.demo/target/jmods
  	dir com.vijfhart.cursus.democlient/target/jmods
	
	
	 
**4. Building a multi-module Java 9 project with the jmod and jlink plugin (intermodular dependencies not supported yet)**
	
**4.1 From the project root Java_Module_Demo_JLink**

  	mvn clean install package
	
**4.2 Check**

  	dir com.vijfhart.cursus.jlink\target
	
**4.3 View disk size**
  
  	04-07-2018  22:39    <DIR>          .
	04-07-2018  22:39    <DIR>          ..
	04-07-2018  22:39    	13.385.137 com.vijfhart.cursus.jlink-1.0-SNAPSHOT.zip
	04-07-2018  22:39    <DIR>          maven-jlink
               1 File(s)     13.385.137 bytes
               3 Dir(s)  103.889.059.840 bytes free
	
**4.4 Execute**

  	com.vijfhart.cursus.jlink\target\maven-jlink\bin\java com.vijfhart.cursus.democlient.Main


