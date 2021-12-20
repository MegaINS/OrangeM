package ru.megains.orangem.client.render.gui.menu

import ru.megains.mge.Scene
import ru.megains.mge.render.MContainer
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.network.handler.NetHandlerLoginClient
import ru.megains.orangem.client.network.{NetworkManagerClient, ServerAddress, ServerData}
import ru.megains.orangem.client.render.gui.base.GuiScreen
import ru.megains.orangem.client.render.gui.element.MButton
import ru.megains.orangem.client.utils.Logger
import ru.megains.orangem.common.network.packet.handshake.client.CHandshake
import ru.megains.orangem.common.network.packet.login.client.CPacketLoginStart
import ru.megains.orangem.common.network.{ConnectionState, NetworkManager}

import java.net.{InetAddress, UnknownHostException}

class GuiConnecting(previousScreen: GuiScreen, orangeM: OrangeM, serverDataIn: ServerData) extends GuiScreen with Logger[GuiConnecting] {

    val serveraddress: ServerAddress = new ServerAddress(serverDataIn.serverIP, 20000)
    var networkManager: NetworkManager = _
    var cancel = false
    var error: GuiScreen = _
    val buttonCancel: MButton = new MButton("Cancel", 300, 40, _ => {
        //orangeM.displayGuiScreen(previousScreen)
    })

    connect(serveraddress.getIP, serveraddress.getPort)

    private def connect(ip: String, port: Int): Unit = {
        log.info("Connecting to {}, {}", Array[AnyRef](ip, Integer.valueOf(port)))
        new Thread("Server Connector #" /* + CONNECTION_ID.incrementAndGet*/) {
            override def run(): Unit = {
                var inetaddress: InetAddress = null
                try {
                    if (cancel) {
                        return
                    }

                    inetaddress = InetAddress.getByName(ip)
                    networkManager = NetworkManagerClient.createNetworkManagerAndConnect(inetaddress, port, orangeM)
                    networkManager.setNetHandler(new NetHandlerLoginClient(networkManager, orangeM, previousScreen))
                    networkManager.sendPacket(new CHandshake(210, ip, port, ConnectionState.LOGIN))
                    networkManager.sendPacket(new CPacketLoginStart(orangeM.playerName))
                    orangeM.networkManager = networkManager
                }
                catch {
                    case unknownhostexception: UnknownHostException =>
                        if (cancel) {
                            return
                        }
                        log.error("Couldn\'t connect to server", unknownhostexception.asInstanceOf[Throwable])

                        error = new GuiDisconnected(previousScreen, orangeM, "connect.failed", "Unknown host")

                    case exception: Exception =>
                        if (cancel) {
                            return
                        }
                        log.error("Couldn\'t connect to server", exception.asInstanceOf[Throwable])
                        var s: String = exception.toString
                        if (inetaddress != null) {
                            val s1: String = inetaddress + ":" + port
                            s = s.replaceAll(s1, "")
                        }
                        error = new GuiDisconnected(previousScreen, orangeM, "connect.failed", s)

                }
            }
        }.start()
    }


    override def update(): Unit = {
        super.update()
        if (error != null) {
           // orangeM.displayGuiScreen(error)
        }
    }

    override def resize(width: Int, height: Int): Unit = {
        buttonCancel.posX = 500
        buttonCancel.posY = height - 70
    }

    override def init(): Unit = {

    }
}
