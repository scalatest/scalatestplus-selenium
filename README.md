# ScalaTest + Selenium
ScalaTest + Selenium provides integration support between ScalaTest and Selenium.

**Usage**

To use it for ScalaTest 3.2.18 and Selenium 4.17.x: 

SBT: 

```
libraryDependencies += "org.scalatestplus" %% "selenium-4-17" % "3.2.18.0" % "test"
```

Maven: 

```
<dependency>
  <groupId>org.scalatestplus</groupId>
  <artifactId>selenium-4-17_2.13</artifactId>
  <version>3.2.17.0</version>
  <scope>test</scope>
</dependency>
```

**Publishing**

Please use the following commands to publish to Sonatype: 

```
$ sbt +publishSigned
```
