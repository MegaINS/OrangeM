package ru.megains.orangem.common.network.packet.login.server

import java.io.IOException

import ru.megains.orangem.common.network.handler.INetHandlerLoginClient
import ru.megains.orangem.common.network.packet.{Packet, PacketBuffer}

class SPacketDisconnect() extends Packet[INetHandlerLoginClient] {

    private var reason: String = _

    def this(text: String) ={
        this()
        this.reason = text
    }

    /**
      * Reads the raw packet data from the data stream.
      */
    @throws[IOException]
    def readPacketData(buf: PacketBuffer) :Unit ={
        this.reason = buf.readStringFromBuffer(32767)
    }

    /**
      * Writes the raw packet data to the data stream.
      */
    @throws[IOException]
    def writePacketData(buf: PacketBuffer) :Unit ={
        buf.writeStringToBuffer(reason)
    }

    /**
      * Passes this Packet on to the NetHandler for processing.
      */
    def processPacket(handler: INetHandlerLoginClient):Unit = {
        handler.handleDisconnect(this)
    }


}
