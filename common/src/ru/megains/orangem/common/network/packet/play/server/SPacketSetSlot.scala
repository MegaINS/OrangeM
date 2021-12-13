package ru.megains.orangem.common.network.packet.play.server

import ru.megains.orangem.common.item.ItemPack
import ru.megains.orangem.common.network.handler.INetHandlerPlayClient
import ru.megains.orangem.common.network.packet.{Packet, PacketBuffer}


class SPacketSetSlot extends Packet[INetHandlerPlayClient] {

    var windowId: Int = 0
    var slot: Int = 0
    var item: ItemPack = _


    def this(windowIdIn: Int, slotIn: Int, itemIn: ItemPack) ={
        this()
        windowId = windowIdIn
        slot = slotIn
        item = if (itemIn == null) null else itemIn
    }


    def processPacket(handler: INetHandlerPlayClient) :Unit ={
        handler.handleSetSlot(this)
    }


    def readPacketData(buf: PacketBuffer) :Unit ={
        windowId = buf.readByte
        slot = buf.readShort
        //item = buf.readItemPackFromBuffer
    }


    def writePacketData(buf: PacketBuffer) :Unit ={
        buf.writeByte(windowId)
        buf.writeShort(slot)
      //  buf.writeItemPackToBuffer(item)
    }
}
