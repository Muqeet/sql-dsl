organization          := "laz"

name                  := "sql-dsl"

version               := "1"

scalaVersion          := "2.9.2"

scalacOptions         := Seq("-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Scala-Tools Maven2 Repository" at "http://scala-tools.org/repo-releases"
)
