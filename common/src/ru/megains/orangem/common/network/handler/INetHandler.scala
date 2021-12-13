package ru.megains.orangem.common.network.handler

import ru.megains.orangem.common.network.packet.Packet


trait INetHandler {


    def onDisconnect(msg: String):Unit 

    def disconnect(msg: String):Unit 

    def sendPacket(packetIn: Packet[_ <: INetHandler]):Unit ={}

}
