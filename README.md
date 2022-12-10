# ScalaTest + Selenium
ScalaTest + Selenium provides integration support between ScalaTest and Selenium.

**Usage**

To use it for ScalaTest 3.2.14 and Selenium 4.6.x: 

SBT: 

```
libraryDependencies += "org.scalatestplus" %% "selenium-4-6" % "3.2.14.0" % "test"
```

Maven: 

```
<dependency>
  <groupId>org.scalatestplus</groupId>
  <artifactId>selenium-4-6_2.13</artifactId>
  <version>3.2.14.0</version>
  <scope>test</scope>
</dependency>
```

**Publishing**

Please use the following commands to publish to Sonatype: 

```
$ sbt +publishSigned
```
