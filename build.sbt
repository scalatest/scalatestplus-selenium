import java.io.PrintWriter
import scala.io.Source

name := "selenium-3.141"

organization := "org.scalatestplus"

version := "3.2.8.0"

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

scalaVersion := "2.13.5"
crossScalaVersions := List("2.10.7", "2.11.12", "2.12.13", "2.13.5", "3.0.0-RC3")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-core" % "3.2.8",
  "org.seleniumhq.selenium" % "selenium-java" % "3.141.59",
  "org.seleniumhq.selenium" % "htmlunit-driver" % "2.39.0",
  "org.eclipse.jetty" % "jetty-server" % "9.4.12.v20180830" % Test,
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.12.v20180830" % Test, 
  "org.scalatest" %% "scalatest-funspec" % "3.2.8" % Test, 
  "org.scalatest" %% "scalatest-shouldmatchers" % "3.2.8" % Test
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

scalacOptions ++= {
  (if (scalaBinaryVersion.value.startsWith("3."))
    Seq(
      "-deprecation",
      "-encoding", "utf-8",
      "-feature",
      "-unchecked",
    )
  else  
    Seq(
    "-deprecation",
    "-encoding", "utf-8",
    "-explaintypes",
    "-feature",
    "-unchecked",
    "-Ywarn-dead-code",
    )
  ) ++ (if (scalaBinaryVersion.value == "2.10" || scalaBinaryVersion.value.startsWith("3.")) Seq.empty else Seq("-Ywarn-unused"))
}

// Temporary disable publishing of doc in dotty, can't get it to build.
publishArtifact in (Compile, packageDoc) := !scalaBinaryVersion.value.startsWith("3.")

def docTask(docDir: File, resDir: File, projectName: String): File = {
  val docLibDir = docDir / "lib"
  val htmlSrcDir = resDir / "html"
  val cssFile = docLibDir / "template.css"
  val addlCssFile = htmlSrcDir / "addl.css"

  val css = Source.fromFile(cssFile).mkString
  val addlCss = Source.fromFile(addlCssFile).mkString

  if (!css.contains("pre.stHighlighted")) {
    val writer = new PrintWriter(cssFile)

    try {
      writer.println(css)
      writer.println(addlCss)
    }
    finally { writer.close }
  }

  if (projectName.contains("scalatest")) {
    (htmlSrcDir * "*.gif").get.foreach { gif =>
      IO.copyFile(gif, docLibDir / gif.name)
    }
  }
  docDir
}

doc in Compile := docTask((doc in Compile).value,
                          (sourceDirectory in Compile).value,
                          name.value)

scalacOptions in (Compile, doc) := Seq("-doc-title", s"ScalaTest + Selenium ${version.value}", 
                                       "-sourcepath", baseDirectory.value.getAbsolutePath(), 
                                       "-doc-source-url", s"https://github.com/scalatest/releases-source/blob/main/scalatestplus-selenium/${version.value}€{FILE_PATH}.scala")