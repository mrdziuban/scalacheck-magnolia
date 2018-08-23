lazy val `scalacheck-magnolia` = project.in(file("."))
  .settings(
    name := "scalacheck-magnolia",
    organization := "com.mrdziuban",
    version := "0.0.1-LOCAL",
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
    )
  )
