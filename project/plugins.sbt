resolvers ++= Seq(
  "Scala Tools" at "https://oss.sonatype.org/content/groups/scala-tools",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
  "spray repo" at "http://repo.spray.cc"
)

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.0")
