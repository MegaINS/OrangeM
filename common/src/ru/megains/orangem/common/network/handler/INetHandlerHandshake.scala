package ru.megains.orangem.common.network.handler

import ru.megains.orangem.common.network.packet.handshake.client.CHandshake


trait INetHandlerHandshake extends INetHandler{

    def processHandshake(handshake: CHandshake): Unit

}
