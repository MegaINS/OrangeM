package ru.megains.orangem.common.network.packet.play.client

import ru.megains.orangem.common.block.data.BlockState
import ru.megains.orangem.common.network.handler.INetHandlerPlayServer
import ru.megains.orangem.common.network.packet.{Packet, PacketBuffer}
import ru.megains.orangem.common.utils.RayTraceResult

class CPacketPlayerMouse extends Packet[INetHandlerPlayServer]{

    var button:Int = -1
    var action:Int = -1
    var rayTraceResult:RayTraceResult = _
    var blockState:BlockState = _
//    def this(buttonIn:Int,actionIn:Int,tartess: OrangeM){
//        this()
//        button = buttonIn
//        action = actionIn
//        rayTraceResult = tartess.objectMouseOver
//        blockState = tartess.blockSelectPosition
//    }

    override def writePacketData(buf: PacketBuffer): Unit = {
//
//        buf.writeInt(button)
//        buf.writeInt(action)
//        buf.writeByte(rayTraceResult.rayTraceType.id)
//        if( rayTraceResult.rayTraceType!=RayTraceType.VOID){
//            buf.writeBlockPos(rayTraceResult.blockPos)
//            buf.writeInt(Blocks.getIdByBlock(rayTraceResult.block) )
//        }
//
//        buf.writeBlockState(blockState)
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
//        button = buf.readInt()
//        action = buf.readInt()
//        val rayTraceType = RayTraceType(buf.readByte())
//        if( rayTraceType!=RayTraceType.VOID){
//            rayTraceResult = new RayTraceResult(rayTraceType,new Vec3f(),Direction.NONE,buf.readBlockPos(),Blocks.getBlockById( buf.readInt()))
//        }else{
//            rayTraceResult = new RayTraceResult()
//        }
//
//        blockState = buf.readBlockState()
    }

    override def processPacket(handler: INetHandlerPlayServer): Unit = {
        handler.processPlayerMouse(this)
    }
}
