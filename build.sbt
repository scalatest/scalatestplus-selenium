name := "selenium-3.141"

organization := "org.scalatestplus"

version := "3.2.1.0"

homepage := Some(url("https://github.com/scalatest/scalatestplus-selenium"))

licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

developers := List(
  Developer(
    "bvenners",
    "Bill Venners",
    "bill@artima.com",
    url("https://github.com/bvenners")
  ),
  Developer(
    "cheeseng",
    "Chua Chee Seng",
    "cheeseng@amaseng.com",
    url("https://github.com/cheeseng")
  )
)

scalaVersion := "2.13.3"
crossScalaVersions := List("2.10.7", "2.11.12", "2.12.12", "2.13.3", "0.26.0-RC1")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-core" % "3.2.1",
  "org.seleniumhq.selenium" % "selenium-java" % "3.141.59",
  "org.seleniumhq.selenium" % "htmlunit-driver" % "2.39.0",
  "org.eclipse.jetty" % "jetty-server" % "9.4.12.v20180830" % Test,
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.12.v20180830" % Test, 
  "org.scalatest" %% "scalatest-funspec" % "3.2.1" % Test, 
  "org.scalatest" %% "scalatest-shouldmatchers" % "3.2.1" % Test
)

Test / scalacOptions ++= (if (isDotty.value) Seq("-language:implicitConversions") else Nil)

import scala.xml.{Node => XmlNode, NodeSeq => XmlNodeSeq, _}
import scala.xml.transform.{RewriteRule, RuleTransformer}

// skip dependency elements with a scope
pomPostProcess := { (node: XmlNode) =>
  new RuleTransformer(new RewriteRule {
    override def transform(node: XmlNode): XmlNodeSeq = node match {
      case e: Elem if e.label == "dependency"
          && e.child.exists(child => child.label == "scope") =>
        def txt(label: String): String = "\"" + e.child.filter(_.label == label).flatMap(_.text).mkString + "\""
        Comment(s""" scoped dependency ${txt("groupId")} % ${txt("artifactId")} % ${txt("version")} % ${txt("scope")} has been omitted """)
      case _ => node
    }
  }).transform(node).head
}

testOptions in Test :=
  Seq(
    Tests.Argument(TestFrameworks.ScalaTest,
    "-l", "org.scalatest.tags.Slow"
  ))

enablePlugins(SbtOsgi)

osgiSettings

OsgiKeys.exportPackage := Seq(
  "org.scalatestplus.selenium.*"
)

OsgiKeys.importPackage := Seq(
  "org.scalatest.*",
  "org.scalactic.*",
  "scala.*;version=\"$<range;[==,=+);$<replace;"+scalaBinaryVersion.value+";-;.>>\"",
  "*;resolution:=optional"
)

OsgiKeys.additionalHeaders:= Map(
  "Bundle-Name" -> "ScalaTestPlusSelenium",
  "Bundle-Description" -> "ScalaTest+Selenium is an open-source integration library between ScalaTest and Selenium for Scala projects.",
  "Bundle-DocURL" -> "http://www.scalatest.org/",
  "Bundle-Vendor" -> "Artima, Inc."
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  Some("publish-releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <scm>
    <url>https://github.com/scalatest/scalatestplus-selenium</url>
    <connection>scm:git:git@github.com:scalatest/scalatestplus-selenium.git</connection>
    <developerConnection>
      scm:git:git@github.com:scalatest/scalatestplus-selenium.git
    </developerConnection>
  </scm>
)

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "utf-8",
  "-explaintypes",
  "-feature",
  "-unchecked",
  "-Ywarn-dead-code",
) ++ (if (scalaBinaryVersion.value == "2.10") Seq.empty else Seq("-Ywarn-unused"))
