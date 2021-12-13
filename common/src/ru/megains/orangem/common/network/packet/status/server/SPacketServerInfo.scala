package ru.megains.orangem.common.network.packet.status.server

import ru.megains.orangem.common.ServerStatusResponse
import ru.megains.orangem.common.network.handler.INetHandlerStatusClient
import ru.megains.orangem.common.network.packet.{Packet, PacketBuffer}



class SPacketServerInfo() extends Packet[INetHandlerStatusClient] {
    private var response: ServerStatusResponse = _

    def this(responseIn: ServerStatusResponse) ={
        this()
        this.response = responseIn
    }


    def readPacketData(buf: PacketBuffer) :Unit ={

    }


    def writePacketData(buf: PacketBuffer) :Unit ={

    }


    def processPacket(handler: INetHandlerStatusClient) :Unit ={
        handler.handleServerInfo(this)
    }


}

