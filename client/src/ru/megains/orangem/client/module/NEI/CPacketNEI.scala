package ru.megains.orangem.client.module.NEI






//
//class CPacketNEI extends Packet[INetHandlerPlayServer]{
//
//    var itemPack:ItemPack = _
//    def this(itemPackIn: ItemPack){
//        this()
//        itemPack = itemPackIn
//    }
//
//
//
//    override def writePacketData(buf: PacketBuffer): Unit = {
//            buf.writeItemPackToBuffer(itemPack)
//    }
//
//    override def readPacketData(buf: PacketBuffer): Unit = {
//        itemPack = buf.readItemPackFromBuffer()
//    }
//
//    override def processPacket(handler: INetHandlerPlayServer): Unit = {
//        handler.processNEI(this)
//    }
//}
