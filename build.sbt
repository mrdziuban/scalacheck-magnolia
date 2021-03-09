import sbtcrossproject.crossProject

Global / onChangedBuildSource := ReloadOnSourceChanges
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

lazy val scalaVersions = List("2.13.5", "2.12.13")

lazy val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  gitRelease := {}
)

lazy val commonSettings = Seq(
  organization := "com.mrdziuban",
  version := "0.4.0",
  crossScalaVersions := scalaVersions,
  scalaVersion := crossScalaVersions.value.head,
  skip in publish := true,
  homepage := Some(url("https://github.com/mrdziuban/scalacheck-magnolia")),
  scmInfo := Some(ScmInfo(url("https://github.com/mrdziuban/scalacheck-magnolia"), "git@github.com:mrdziuban/scalacheck-magnolia.git")),
  developers := List(Developer("mrdziuban", "Matt Dziuban", "mrdziuban@gmail.com", url("https://github.com/mrdziuban"))),
  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
  gitPublishDir := file(s"${sys.env("HOME")}/maven-repo")
)

lazy val root = project.in(file("."))
  .settings(commonSettings)
  .settings(noPublishSettings)
  .aggregate(scalacheckMagnolia.jvm, scalacheckMagnolia.js)

lazy val scalacheckMagnolia = crossProject(JVMPlatform, JSPlatform)
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "scalacheck-magnolia",
    libraryDependencies ++= Seq(
      "org.scalacheck" %%% "scalacheck" % "1.15.3",
      "com.propensive" %%% "magnolia" % "0.17.0",
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    ),
    skip in publish := false
  )

lazy val tut = project
  .in(file("tut"))
  .settings(commonSettings)
  .settings(noPublishSettings)
  .settings(
    mdocIn := baseDirectory.value / ".." / "docs",
    mdocOut := baseDirectory.value / "..",
    mdocVariables := Map("VERSION" -> (scalacheckMagnolia.jvm / version).value),
    mdocExtraArguments += "--no-link-hygiene",
    publish := {},
    publishLocal := {}
  )
  .dependsOn(scalacheckMagnolia.jvm)
  .enablePlugins(MdocPlugin)
