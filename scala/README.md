# Scala

### 0. create project structure
```bash
sbt new sbt/scala-seed.g8
name [My Something Project]: my-app
....
$ cd hello
$ sbt
...
> run
```
you can see the following project structure
```bash
- my-app
    - project
        - build.properties
    - src
        - main
            - scala
                - Main.scala
    - build.sbt
```

### 1. update built.sbt

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
```bash
sbt package
```

### 3. run the project

```bash
sbt
run 
```
