package ru.megains.orangem.client.scene

import ru.megains.mge.render.MContainer
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.mge.render.text.Label
import ru.megains.orangem.client.network.ServerPinger
import org.lwjgl.opengl.GL11._
import ru.megains.orangem.client.OrangeMClient
import ru.megains.orangem.client.network.{ServerData, ServerPinger}
import ru.megains.orangem.client.render.gui.element.MButton
import ru.megains.orangem.client.render.shader.GuiShader

class MultiPlayerScene(orangeM: OrangeMClient) extends BaseScene {

    val server: ServerData = new ServerData("localhost", "localhost", true)
    val pinger = new ServerPinger(orangeM)
    var pingVal: Long = -1

    val pingText: Label = new Label(pingVal.toString)
    val buttonPing: MButton = new MButton("Ping", 300, 50, () => {
        ping()
    })
    val buttonCancel: MButton = new MButton("Cancel", 300, 50, () => {
        orangeM.setScene(new MainMenuScene(orangeM))
    })
    val buttonConnect: MButton = new MButton("Connect", 300, 50, () => {
        connectToServer(server)
    })
    addChildren(buttonPing, buttonCancel, buttonConnect, pingText)

    def connectToServer(server: ServerData): Unit = {
        orangeM.setScene(new ConnectingScene(this, orangeM, server))
    }

    def ping(): Unit = {
        try {
            pinger.ping(server)
        } catch {
            case e: Throwable =>
            //e.printStackTrace()

        }
    }

    override def update(): Unit = {
        super.update()
        if (pingVal != server.pingToServer) {
            pingVal = server.pingToServer
            pingText.text = pingVal.toString
        }
    }

    override def resize(width: Int, height: Int): Unit = {
        pingText.posX = 100
        pingText.posY = 100

        buttonPing.posX = width / 2 + 50
        buttonPing.posY = height - 150

        buttonCancel.posX = width / 2 + 50
        buttonCancel.posY = height - 70

        buttonConnect.posX = width / 2 - 350
        buttonConnect.posY = height - 70

    }
}
