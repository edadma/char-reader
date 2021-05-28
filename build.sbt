ThisBuild / versionScheme := Some("semver-spec")

lazy val char_reader = crossProject(JSPlatform, JVMPlatform, NativePlatform).in(file(".")).
  settings(
    name := "char-reader",
    version := "0.1.0",
    scalaVersion := "2.13.6",
    scalacOptions ++=
      Seq(
        "-deprecation", "-feature", "-unchecked",
        "-language:postfixOps", "-language:implicitConversions", "-language:existentials", "-language:dynamics",
        "-Xasync"
      ),
    organization := "xyz.hyperreal",
//    publishTo := Some(
//      "Artifactory Realm" at "https://hyperreal.jfrog.io/artifactory/default-maven-virtual"
//    ),
//    credentials += Credentials(
//      "Artifactory Realm",
//      "hyperreal.jfrog.io",
//      "edadma@gmail.com",
//      "fW6N-hDhW*XPXhMt"
//    ),
    githubOwner := "edadma",
    githubRepository := "char-reader",
    mainClass := Some("xyz.hyperreal.char_reader.Main"),
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.5" % "test",
    libraryDependencies += "xyz.hyperreal" %%% "cross-platform" % "0.1.0",
    publishMavenStyle := true,
    Test / publishArtifact := false,
    licenses += "ISC" -> url("https://opensource.org/licenses/ISC")
  ).
  jvmSettings(
    libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.0.0" % "provided",
  ).
  nativeSettings(
    nativeLinkStubs := true
  ).
  jsSettings(
    jsEnv := new org.scalajs.jsenv.nodejs.NodeJSEnv(),
//    Test / scalaJSUseMainModuleInitializer := true,
//    Test / scalaJSUseTestModuleInitializer := false,
    Test / scalaJSUseMainModuleInitializer := false,
    Test / scalaJSUseTestModuleInitializer := true,
    scalaJSUseMainModuleInitializer := true,
  )
