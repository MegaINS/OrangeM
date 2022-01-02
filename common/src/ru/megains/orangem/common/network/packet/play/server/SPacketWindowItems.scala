package ru.megains.orangem.common.network.packet.play.server

import ru.megains.orangem.common.item.ItemPack
import ru.megains.orangem.common.item.itemstack.ItemStack
import ru.megains.orangem.common.network.handler.INetHandlerPlayClient
import ru.megains.orangem.common.network.packet.{Packet, PacketBuffer}

class SPacketWindowItems extends Packet[INetHandlerPlayClient] {


    var windowId: Int = 0
    var itemStacks: Array[ItemStack] = _


    def this(windowIdIn: Int, stacks: Array[ItemStack]) ={
        this()
        windowId = windowIdIn
        itemStacks = stacks
    }


    def readPacketData(buf: PacketBuffer) :Unit ={
        windowId = buf.readUnsignedByte
        itemStacks = new Array[ItemStack](buf.readShort)
        for (i <- itemStacks.indices) {
            //itemStacks(i) = buf.readItemPackFromBuffer
        }
    }


    def writePacketData(buf: PacketBuffer) :Unit ={
        buf.writeByte(windowId)
        buf.writeShort(itemStacks.length)
        for (itemStack <- itemStacks) {
           // buf.writeItemPackToBuffer(itemStack)
        }
    }

    def processPacket(handler: INetHandlerPlayClient) :Unit ={
        handler.handleWindowItems(this)
    }
}
