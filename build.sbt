lazy val char_reader = crossProject(JSPlatform, JVMPlatform/*, NativePlatform*/).in(file(".")).
  settings(
    name := "char-reader",
    version := "0.1.9",
    scalaVersion := "2.13.4",
    scalacOptions ++=
      Seq(
        "-deprecation", "-feature", "-unchecked",
        "-language:postfixOps", "-language:implicitConversions", "-language:existentials", "-language:dynamics",
        "-Xasync"
      ),
    organization := "xyz.hyperreal",
    mainClass := Some("xyz.hyperreal.char_reader.Main"),
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.3" % "test",
    publishMavenStyle := true,
    publishArtifact in Test := false,
    licenses += "ISC" -> url("https://opensource.org/licenses/ISC")
  ).
  jvmSettings(
    libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.0.0" % "provided",
  ).
//  nativeSettings(
//    nativeLinkStubs := true
//  ).
  jsSettings(
    jsEnv := new org.scalajs.jsenv.nodejs.NodeJSEnv(),
    Test / scalaJSUseMainModuleInitializer := true,
    Test / scalaJSUseTestModuleInitializer := false,
//    Test / scalaJSUseMainModuleInitializer := false,
//    Test / scalaJSUseTestModuleInitializer := true,
    scalaJSUseMainModuleInitializer := true,
  )
