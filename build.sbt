ThisBuild / libraryDependencies ++=
  Seq(
    "com.lihaoyi" %%% "utest" % "0.7.7" % "test",
  )
ThisBuild / scalaVersion := "2.13.4"
ThisBuild / scalacOptions ++=
  Seq(
    "-deprecation", "-feature", "-unchecked",
    "-language:postfixOps", "-language:implicitConversions", "-language:existentials", "-language:dynamics",
    "-Xasync"
  )

lazy val root = project.in(file(".")).
  aggregate(char_reader.js, char_reader.jvm/*, char_reader.native*/).
  settings(
    publish := {},
    publishLocal := {},
  )

lazy val char_reader = crossProject(JSPlatform, JVMPlatform/*, NativePlatform*/).crossType(CrossType.Pure).in(file(".")).
  settings(
    name := "char-reader",
    version := "0.1.6",
    organization := "xyz.hyperreal",
    mainClass := Some("xyz.hyperreal.char_reader.Main"),
    testFrameworks += new TestFramework("utest.runner.Framework"),
    publishMavenStyle := true,
    publishArtifact in Test := false,
    licenses := Seq("ISC" -> url("https://opensource.org/licenses/ISC"))
  ).
  jvmSettings(
    libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.0.0" % "provided",
  ).
  //  nativeSettings(
  //    libraryDependencies ++=
  //      Seq(
  //        "com.lihaoyi" %%% "utest" % "0.7.7" % "test",
  //      ),
  //  ).
  jsSettings(
    jsEnv := new org.scalajs.jsenv.nodejs.NodeJSEnv(),
    //    Test / scalaJSUseMainModuleInitializer := true,
    //    Test / scalaJSUseTestModuleInitializer := false,
    Test / scalaJSUseMainModuleInitializer := false,
    Test / scalaJSUseTestModuleInitializer := true,
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++=
      Seq(
        "com.lihaoyi" %%% "utest" % "0.7.7" % "test",
      ),
  )
