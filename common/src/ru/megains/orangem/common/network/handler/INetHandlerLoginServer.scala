package ru.megains.orangem.common.network.handler

import ru.megains.orangem.common.network.packet.login.client.CPacketLoginStart

trait INetHandlerLoginServer extends INetHandler {

    def processLoginStart(packetIn: CPacketLoginStart):Unit 

    //  def processEncryptionResponse(packetIn: CPacketEncryptionResponse)
}
