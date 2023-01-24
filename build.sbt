ThisBuild / organization    := "com.dagdelenmustafa"
ThisBuild / scalaVersion    := "2.13.10"
ThisBuild / nativeLinkStubs := true

ThisBuild / testFrameworks += new TestFramework("munit.Framework")

ThisBuild / startYear := Some(2023)
ThisBuild / licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))

ThisBuild / libraryDependencies := Seq(
  "org.typelevel" %%% "cats-core" % "2.9.0",
  "com.monovore"  %%% "decline"   % "2.4.1",
  "com.lihaoyi"   %%% "os-lib"    % "0.9.0",
  // Test Dependencies
  "org.scala-native" %%% "junit-runtime" % "0.4.9",
  "org.scalameta"    %%% "munit"         % "0.7.29" % Test
)
addCompilerPlugin("org.scala-native" % "junit-plugin" % "0.4.9" cross CrossVersion.full)

enablePlugins(ScalaNativePlugin)
