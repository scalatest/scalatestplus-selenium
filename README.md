# ScalaTest + Selenium
ScalaTest + Selenium provides integration support between ScalaTest and Selenium.

**Usage**

To use it for ScalaTest 3.2.0 and Selenium 3.141.x: 

SBT: 

```
libraryDependencies += "org.scalatestplus" %% "selenium-3-141" % "3.2.0.0" % "test"
```

Maven: 

```
<dependency>
  <groupId>org.scalatestplus</groupId>
  <artifactId>selenium-3-141</artifactId>
  <version>3.2.0.0</version>
  <scope>test</scope>
</dependency>
```

**Publishing**

Please use the following commands to publish to Sonatype: 

```
$ sbt +publishSigned
```
