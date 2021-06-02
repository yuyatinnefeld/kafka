# Java Env Setup

###1. maven project start
```bash
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false

cd my-app

mvn package

java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App

```

or 

just initial create by IntelliJ

###2. update dependency
```xml

    <dependency>
    <groupId>io.netty</groupId>
    <artifactId>netty-common</artifactId>
    <version>4.1.65.Final</version>
    </dependency>
    
    <dependency>
      <groupId>org.apache.kafka</groupId>
      <artifactId>kafka_2.13</artifactId>
      <version>2.7.0</version>
    </dependency>

```

or


```xml
        <!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients -->
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>2.8.0</version>
</dependency>

        <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.25</version>
</dependency>

```

###3. update plugin
```xml

        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.3</version>
          <configuration>
            <source>1.8</source>
            <target>1.8</target>
          </configuration>
        </plugin>

```

###4. add kafka programs
```bash
mkdir /kafka/java/my-app/src/main/java/com/simple.kafka
cd /kafka/java/my-app/src/main/java/com/simple.kafka
touch KafkaSimpleConsumer.java
touch KafkaSimpleProducer.java
```

###5. setup project structure of your IntelliJ
- project SDK -> java
- product language level = 8

###6. mvn clean compile
```bash
mvn clean compile
```

###7. test run!
