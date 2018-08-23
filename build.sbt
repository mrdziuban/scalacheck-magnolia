import sbtcrossproject.crossProject

sonatypeProfileName := "com.mrdziuban"

lazy val publishSettings = Seq(
  homepage := Some(url("https://github.com/mrdziuban/scalacheck-magnolia")),
  scmInfo := Some(ScmInfo(url("https://github.com/mrdziuban/scalacheck-magnolia"), "git@github.com:mrdziuban/scalacheck-magnolia.git")),
  developers := List(Developer("mrdziuban", "Matt Dziuban", "mrdziuban@gmail.com", url("https://github.com/mrdziuban"))),
  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
  publishMavenStyle := true,
  publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)
)

lazy val scalaVersions = List("2.12.6", "2.11.12")

lazy val `scalacheck-magnolia` = crossProject(JVMPlatform, JSPlatform)
  .in(file("."))
  .settings(publishSettings:_*)
  .settings(
    name := "scalacheck-magnolia",
    organization := "com.mrdziuban",
    version := "0.0.2",
    addCompilerPlugin("io.tryp" % "splain" % "0.3.1" cross CrossVersion.patch),
    scalaVersion := crossScalaVersions.value.head,
    scalacOptions := Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-unchecked",
      "-Xfatal-warnings",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-infer-any",
      "-Ywarn-nullary-override",
      "-Ywarn-nullary-unit",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Xfuture",
      "-P:splain:all"
    ) ++ (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, v)) if v == 12 =>
        Seq(
          "-Xlint:-unused,_",
          "-Ywarn-unused:imports,privates,locals",
          "-Ycache-plugin-class-loader:last-modified",
          "-Ycache-macro-class-loader:last-modified"
        )
      case _ => Seq("-Ywarn-unused-import")
    }),
    libraryDependencies ++= Seq(
      "org.scalacheck" %%% "scalacheck" % "1.14.0",
      "com.propensive" %%% "magnolia" % "0.10.0"
    ),
  )
  .jvmSettings(crossScalaVersions := scalaVersions)
  .jsSettings(crossScalaVersions := scalaVersions)

lazy val jvm = `scalacheck-magnolia`.jvm
lazy val js = `scalacheck-magnolia`.js

lazy val root = project
  .in(file("."))
  .settings(publish := {}, publishLocal := {}, PgpKeys.publishSigned := {})
  .aggregate(jvm, js)
  .dependsOn(jvm, js)
