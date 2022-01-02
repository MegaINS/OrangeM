package ru.megains.orangem.server

import scala.reflect.io.Path

object StartServer extends App {

    try {
        val path = Path("W:/OrangeM/Server").toDirectory
        Thread.currentThread.setName("Server")
        val server = new OrangeMGServer(path)
        val serverCommand = new ServerCommand(server)
        serverCommand.setName("serverControl")
        serverCommand.setDaemon(true)
        serverCommand.start()

        server.run()
    } catch {
        case e:Exception => e.printStackTrace()
    }
}
