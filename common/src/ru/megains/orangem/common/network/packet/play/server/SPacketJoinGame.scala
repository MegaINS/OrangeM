package ru.megains.orangem.common.network.packet.play.server

import ru.megains.orangem.common.network.handler.INetHandlerPlayClient
import ru.megains.orangem.common.network.packet.{Packet, PacketBuffer}

class SPacketJoinGame extends Packet[INetHandlerPlayClient] {


    override def readPacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleJoinGame(this)
    }
}
