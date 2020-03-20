import sbtcrossproject.crossProject

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val scalaVersions = List("2.13.1", "2.12.11")

lazy val commonSettings = Seq(
  organization := "com.mrdziuban",
  version := "0.1.0",
  crossScalaVersions := scalaVersions,
  scalaVersion := crossScalaVersions.value.head,
  skip in publish := true
)

lazy val `scalacheck-magnolia` = crossProject(JVMPlatform, JSPlatform)
  .in(file("."))
  .settings(commonSettings:_*)
  .settings(
    name := "scalacheck-magnolia",
    libraryDependencies ++= Seq(
      "org.scalacheck" %%% "scalacheck" % "1.14.3",
      "com.propensive" %%% "magnolia" % "0.12.8"
    ),
    // Publish settings
    homepage := Some(url("https://github.com/mrdziuban/scalacheck-magnolia")),
    scmInfo := Some(ScmInfo(url("https://github.com/mrdziuban/scalacheck-magnolia"), "git@github.com:mrdziuban/scalacheck-magnolia.git")),
    developers := List(Developer("mrdziuban", "Matt Dziuban", "mrdziuban@gmail.com", url("https://github.com/mrdziuban"))),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    skip in publish := false,
    bintrayOrganization := Some("mrdziuban"),
    bintrayRepository := "scalacheck-magnolia",
    bintrayReleaseOnPublish in ThisBuild := false
  )

lazy val tut = project
  .in(file("tut"))
  .settings(commonSettings:_*)
  .settings(
    scalacOptions := scalacOptions.value.filterNot(_.contains("warn-unused")),
    tutTargetDirectory := baseDirectory.value / "..",
    crossScalaVersions := Seq(),
    scalaVersion := scalaVersions.find(_.startsWith("2.12")).get
  )
  .dependsOn(`scalacheck-magnolia`.jvm)
  .enablePlugins(TutPlugin)
