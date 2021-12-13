package ru.megains.orangem.client.network.handler

import ru.megains.orangem.client.OrangeMClient
import ru.megains.orangem.client.network.ServerData
import ru.megains.orangem.common.network.NetworkManager
import ru.megains.orangem.common.network.handler.INetHandlerStatusClient
import ru.megains.orangem.common.network.packet.status.client.CPacketPing
import ru.megains.orangem.common.network.packet.status.server.{SPacketPong, SPacketServerInfo}
import ru.megains.orangem.common.utils.Logger

class NetHandlerStatusClient(server:ServerData,networkManager: NetworkManager) extends INetHandlerStatusClient with Logger[NetHandlerStatusClient]{

    var successful: Boolean = false
    var receivedStatus: Boolean = false
    var pingSentAt: Long = 0L

    override def handleServerInfo(packetIn: SPacketServerInfo):Unit = {
        if (receivedStatus) networkManager.closeChannel("ServerPinger" + "receivedStatus")
        else {
            receivedStatus = true

            this.pingSentAt = OrangeMClient.getSystemTime
            networkManager.sendPacket(new CPacketPing(pingSentAt))
            this.successful = true
        }
    }

    override def handlePong(packetIn: SPacketPong) :Unit ={
        val i: Long = pingSentAt
        val j: Long = OrangeMClient.getSystemTime
        server.pingToServer = j - i
        networkManager.closeChannel("ServerPinger" + "handlePong")
    }

    override def onDisconnect(msg: String):Unit = {
        if (!successful) {
            log.error("Can\'t ping {}: {}", Array[AnyRef](server.serverIP, msg))
            server.serverMOTD = "Can\'t connect to server."
            server.populationInfo = ""
                    }
    }

    override def disconnect(msg: String): Unit = {

    }
}
