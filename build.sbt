lazy val `scalacheck-magnolia` = project.in(file("."))
  .settings(
    name := "scalacheck-magnolia",
    organization := "com.mrdziuban",
    version := "0.0.1-SNAPSHOT",
    addCompilerPlugin("io.tryp" % "splain" % "0.3.1" cross CrossVersion.patch),
    scalaVersion := "2.12.6",
    scalacOptions := Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-unchecked",
      "-Xfatal-warnings",
      "-Xlint:-unused,_",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-infer-any",
      "-Ywarn-nullary-override",
      "-Ywarn-nullary-unit",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused:imports,privates,locals",
      "-Ywarn-value-discard",
      "-Xfuture",
      "-P:splain:all",
      "-Ycache-plugin-class-loader:last-modified",
      "-Ycache-macro-class-loader:last-modified",
    ),
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % "1.14.0",
      "com.propensive" %% "magnolia" % "0.10.0"
    ),
    // Sonatype settings
    homepage := Some(url("https://github.com/mrdziuban/scalacheck-magnolia")),
    scmInfo := Some(ScmInfo(url("https://github.com/mrdziuban/scalacheck-magnolia"), "git@github.com:mrdziuban/scalacheck-magnolia.git")),
    developers := List(Developer("mrdziuban", "Matt Dziuban", "mrdziuban@gmail.com", url("https://github.com/mrdziuban"))),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    publishMavenStyle := true,
    publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)
  )
