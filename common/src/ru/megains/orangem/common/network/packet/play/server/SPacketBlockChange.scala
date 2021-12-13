package ru.megains.orangem.common.network.packet.play.server

import ru.megains.orangem.common.block.data.{BlockPos, BlockState}
import ru.megains.orangem.common.network.handler.INetHandlerPlayClient
import ru.megains.orangem.common.network.packet.{Packet, PacketBuffer}
import ru.megains.orangem.common.register.Blocks
import ru.megains.orangem.common.world.World

class SPacketBlockChange extends Packet[INetHandlerPlayClient] {


    var block: BlockState = _

    def this(worldIn: World, posIn: BlockPos) {
        this()


        block = worldIn.getBlock(posIn.x,posIn.y,posIn.z)
        if(block.block == Blocks.air){
            block = new BlockState(Blocks.air,posIn.x,posIn.y,posIn.z)
        }
    }

    /**
      * Reads the raw packet data from the data stream.
      */

    def readPacketData(buf: PacketBuffer) :Unit ={
      //  block =buf.readBlockState()
    }

    /**
      * Writes the raw packet data to the data stream.
      */

    def writePacketData(buf: PacketBuffer):Unit = {
      //  buf.writeBlockState(block)
    }

    /**
      * Passes this Packet on to the NetHandler for processing.
      */
    def processPacket(handler: INetHandlerPlayClient):Unit = {
        handler.handleBlockChange(this)
    }


}
