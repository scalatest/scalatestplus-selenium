name := "scalatestplus-selenium"

organization := "org.scalatestplus"

version := "1.0.0-SNAP4"

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

crossScalaVersions := List("2.10.7", "2.11.12", "2.12.8", "2.13.0-RC3")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.0-SNAP12",
  "org.seleniumhq.selenium" % "selenium-java" % "2.45.0",
  "org.eclipse.jetty" % "jetty-server" % "9.4.12.v20180830" % "test",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.12.v20180830" % "test"
)

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

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

pgpSecretRing := file((Path.userHome / ".gnupg" / "secring.gpg").getAbsolutePath)

pgpPassphrase := None
