
## Scala
### 0. create project structure
```bash
- myapp
    - project
        - build.properties
    - src
        - main
            - scala
                - Main.scala
    - build.sbt
```

### 1. add env setup in built.sbt

```scala
name := "kafka-sbt"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "2.7.0",
  "org.apache.spark" %% "spark-core" % "3.0.0"
)

name := "kafka-scala"
organization := "yuyatinnefeld-scala"
version := "1.0"
```

### 2. build the project

### 3. run the project

```bash
sbt
run 
```
