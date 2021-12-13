package ru.megains.orangem.client.network.handler

import ru.megains.mge.Scene
import ru.megains.orangem.client.OrangeMClient
import ru.megains.orangem.common.network.{ConnectionState, NetworkManager}
import ru.megains.orangem.common.network.handler.INetHandlerLoginClient
import ru.megains.orangem.common.network.packet.login.server.{SPacketDisconnect, SPacketLoginSuccess}

class NetHandlerLoginClient(networkManager: NetworkManager, gameController: OrangeMClient, previousScene: Scene) extends INetHandlerLoginClient {


    override def handleDisconnect(packetIn: SPacketDisconnect): Unit = {
       // PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, gameController)
    }

    override def onDisconnect(msg: String): Unit = {

    }

    override def handleLoginSuccess(packetIn: SPacketLoginSuccess): Unit = {
        networkManager.setConnectionState(ConnectionState.PLAY)
        val nhpc: NetHandlerPlayClient = new NetHandlerPlayClient(gameController, previousScene, networkManager)
        networkManager.setNetHandler(nhpc)
    }
    override def disconnect(msg: String): Unit = {

}
}
