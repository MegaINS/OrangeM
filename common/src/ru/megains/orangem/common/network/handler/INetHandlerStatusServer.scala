package ru.megains.orangem.common.network.handler

import ru.megains.orangem.common.network.packet.status.client.{CPacketPing, CPacketServerQuery}

trait INetHandlerStatusServer extends INetHandler {
    def processPing(packetIn: CPacketPing):Unit 

    def processServerQuery(packetIn: CPacketServerQuery):Unit 
}
