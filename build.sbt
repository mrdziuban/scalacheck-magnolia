import sbtcrossproject.crossProject

Global / onChangedBuildSource := ReloadOnSourceChanges
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

lazy val scalaVersions = List("2.13.3", "2.12.12")

lazy val commonSettings = Seq(
  organization := "com.mrdziuban",
  version := "0.2.1",
  crossScalaVersions := scalaVersions,
  scalaVersion := crossScalaVersions.value.head,
  skip in publish := true,
  homepage := Some(url("https://github.com/mrdziuban/scalacheck-magnolia")),
  scmInfo := Some(ScmInfo(url("https://github.com/mrdziuban/scalacheck-magnolia"), "git@github.com:mrdziuban/scalacheck-magnolia.git")),
  developers := List(Developer("mrdziuban", "Matt Dziuban", "mrdziuban@gmail.com", url("https://github.com/mrdziuban"))),
  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
  bintrayOrganization := Some("mrdziuban"),
  bintrayRepository := "scalacheck-magnolia",
  bintrayReleaseOnPublish in ThisBuild := false
)

lazy val `scalacheck-magnolia` = crossProject(JVMPlatform, JSPlatform)
  .in(file("."))
  .settings(commonSettings:_*)
  .settings(
    name := "scalacheck-magnolia",
    libraryDependencies ++= Seq(
      "org.scalacheck" %%% "scalacheck" % "1.14.3",
      "com.propensive" %%% "magnolia" % "0.16.0",
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    ),
    skip in publish := false
  )

lazy val tut = project
  .in(file("tut"))
  .settings(commonSettings:_*)
  .settings(
    mdocIn := baseDirectory.value / ".." / "docs",
    mdocOut := baseDirectory.value / "..",
    mdocVariables := Map("VERSION" -> (`scalacheck-magnolia`.jvm / version).value),
    mdocExtraArguments += "--no-link-hygiene",
    publish := {},
    publishLocal := {}
  )
  .dependsOn(`scalacheck-magnolia`.jvm)
  .enablePlugins(MdocPlugin)
  .disablePlugins(BintrayPlugin)
