addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.1.16")
addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.2.3")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.5.0")
addSbtPlugin("org.portable-scala" % "sbt-crossproject" % "1.0.0")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.0.0")

resolvers += "bondlink-maven-repo" at "https://raw.githubusercontent.com/mblink/maven-repo/main"
addSbtPlugin("bondlink" % "sbt-git-publish" % "0.0.4")
