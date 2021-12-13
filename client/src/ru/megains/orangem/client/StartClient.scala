package ru.megains.orangem.client

import ru.megains.mge.File

object StartClient extends App {

  val config = new Configuration() {
    filePath = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/client/resources/"
    println(filePath.toString)
  }
  File.gamePath = config.filePath
  val game = new OrangeMClient(config)
  game.start()

}
