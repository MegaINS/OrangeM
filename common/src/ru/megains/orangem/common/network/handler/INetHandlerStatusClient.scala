package ru.megains.orangem.common.network.handler

import ru.megains.orangem.common.network.packet.status.server.{SPacketPong, SPacketServerInfo}

trait INetHandlerStatusClient extends INetHandler {


    def handleServerInfo(packetIn: SPacketServerInfo):Unit 

    def handlePong(packetIn: SPacketPong):Unit 
}