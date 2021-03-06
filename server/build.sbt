name := "JWT-Server"
organization := "com.boloutaredoubeni.loginauth"
version := "0.1"

scalaVersion := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
  "Sonatype OSS Releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
)

libraryDependencies ++= {
  val sprayVersion = "1.3.3"
  val akkaVersion = "2.4.1"
  Seq(
    "io.spray"            %%  "spray-can"         % sprayVersion,
    "io.spray"            %%  "spray-routing"     % sprayVersion,
    "io.spray"            %%  "spray-testkit"     % sprayVersion  % "test",
    "io.spray"            %%  "spray-json"        % "1.3.2",
    "com.typesafe.akka"   %%  "akka-slf4j"        % akkaVersion,
    "com.typesafe.akka"   %%  "akka-actor"        % akkaVersion,
    "com.typesafe.akka"   %%  "akka-testkit"      % akkaVersion   % "test",
    "com.pauldijou"       %%  "jwt-core"          % "0.5.0",
    "org.specs2"          %%  "specs2"            % "2.3.11"         % "test"
  )
}