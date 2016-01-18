name := "JWT-Server"
organization := "com.boloutaredoubeni.loginauth"
version := "0.1"

scalaVersion := "2.10.5"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
)

libraryDependencies ++= {
  val sprayVersion = "1.2.0"
  val akkaVersion = "2.2.3"
  Seq(
    "io.spray"            %   "spray-servlet"     % sprayVersion,
    "io.spray"            %   "spray-routing"     % sprayVersion,
    "io.spray"            %   "spray-testkit"     % sprayVersion,
    "io.spray"            %   "spray-client"      % sprayVersion,
    "io.spray"            %   "spray-util"        % sprayVersion,
    "io.spray"            %   "spray-caching"     % sprayVersion,
    "io.spray"            %   "spray-can"         % sprayVersion,
    "io.spray"            %%  "spray-json"        % "1.3.2",
    "com.typesafe.akka"   %%  "akka-slf4j"        % akkaVersion,
    "ch.qos.logback"      %   "logback-classic"   % "1.0.13",
    "com.typesafe.akka"   %%  "akka-actor"        % akkaVersion,
    "com.typesafe.akka"   %%  "akka-testkit"      % akkaVersion
  )
}