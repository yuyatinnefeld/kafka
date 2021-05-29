# Java Env Setup

###1. maven project start

###2. add dependency & plugin

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


        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.3</version>
          <configuration>
            <source>1.8</source>
            <target>1.8</target>
          </configuration>
        </plugin>


###3. add kafka programs

###4. project structure > product language level = 8

###5. mvn clean compile