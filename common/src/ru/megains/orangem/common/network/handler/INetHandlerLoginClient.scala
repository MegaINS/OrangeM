package ru.megains.orangem.common.network.handler

import ru.megains.orangem.common.network.packet.login.server.{SPacketDisconnect, SPacketLoginSuccess}

trait INetHandlerLoginClient extends INetHandler {
    // def handleEncryptionRequest(packetIn: SPacketEncryptionRequest)

    def handleLoginSuccess(packetIn: SPacketLoginSuccess):Unit 

    def handleDisconnect(packetIn: SPacketDisconnect):Unit 

    // def handleEnableCompression(packetIn: SPacketEnableCompression)
}
