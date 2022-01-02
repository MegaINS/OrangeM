import sbt.Keys.libraryDependencies

name := "OrangeM"

version := "0.1"

scalaVersion := "2.13.6"


val lwjglVersion = "3.3.0"


lazy val mge = project
        .settings(

            name := "mge",
            scalaVersion := "2.13.6",
            version := "0.1",
            Compile / scalaSource := baseDirectory.value / "src",
            libraryDependencies ++= Seq(
                "org.joml" % "joml" % "1.9.0",
                "org.lwjgl" % "lwjgl" % lwjglVersion,
                "org.lwjgl" % "lwjgl-glfw" % lwjglVersion,
                "org.lwjgl" % "lwjgl-opengl" % lwjglVersion,
                "org.lwjgl" % "lwjgl-stb" % lwjglVersion,


                "org.lwjgl" % "lwjgl" % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
                "org.lwjgl" % "lwjgl-glfw" % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
                "org.lwjgl" % "lwjgl-opengl" % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
                "org.lwjgl" % "lwjgl-stb" % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
            )
        )

lazy val common = project
        .settings(
            Compile / scalaSource := baseDirectory.value / "src",
            scalaVersion := "2.13.6",
            libraryDependencies ++= Seq(
                "org.joml" % "joml" % "1.9.0",
                "org.slf4j" % "slf4j-api" % "1.7.25",
                "ch.qos.logback" % "logback-classic" % "1.2.3",
                "org.scala-lang" % "scala-reflect" % "2.13.1",
                "io.netty" % "netty-all" % "4.1.64.Final"
            )
        )


lazy val client = project.settings(
    Compile / scalaSource := baseDirectory.value / "src",
    Compile / resourceDirectory := baseDirectory.value / "resources",
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
       // "org.slf4j" % "slf4j-api" % "1.7.25",
      //  "ch.qos.logback" % "logback-classic" % "1.2.3",
      //  "org.scala-lang" % "scala-reflect" % "2.13.1",

      //  "io.netty" % "netty-all" % "4.1.64.Final",
        "io.github.spair" % "imgui-java-app" % "1.84.1.3",
        "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.0"
    )
).aggregate(common)
        .dependsOn(common)
        .aggregate(mge)
        .dependsOn(mge)


lazy val server = project.settings(
    Compile / scalaSource := baseDirectory.value / "src",
    Compile / resourceDirectory := baseDirectory.value / "resources",
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
//        "org.slf4j" % "slf4j-api" % "1.7.25",
//        "ch.qos.logback" % "logback-classic" % "1.2.3",
//        "org.scala-lang" % "scala-reflect" % "2.13.1",
//
//        "io.netty" % "netty-all" % "4.1.64.Final",
//        "io.github.spair" % "imgui-java-app" % "1.84.1.3",
//        "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.0"
    )
).aggregate(common)
        .dependsOn(common)
