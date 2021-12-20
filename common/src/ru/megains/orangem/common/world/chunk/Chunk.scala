package ru.megains.orangem.common.world.chunk

import ru.megains.orangem.common.block.data.BlockState
import ru.megains.orangem.common.entity.Entity
import ru.megains.orangem.common.utils.Logger
import ru.megains.orangem.common.world.World

import scala.collection.mutable.ArrayBuffer


class Chunk(val pos: ChunkPosition, val world: World) extends Logger[Chunk]{


    var chunkEntityMap: ArrayBuffer[Entity] = new ArrayBuffer[Entity]()

    val blockStorage = new BlockStorage(pos)

    var isEmpty: Boolean = true

    def getBlock(x: Int, y: Int, z: Int):BlockState = {
        blockStorage.getBlock(x & (Chunk.blockSize-1), y & (Chunk.blockSize-1), z & (Chunk.blockSize-1))
    }

    def getBlockByLocPos(x: Int, y: Int, z: Int):BlockState ={
        blockStorage.getBlock(x, y, z)
    }
    def isAirBlock(blockState: BlockState):Boolean = {
        blockStorage.isAirBlock(blockState: BlockState)
    }

    def removeBlock(blockState: BlockState):Unit={
        blockStorage.removeBlock(blockState)
    }
    def setBlock(blockState: BlockState):Unit={
        blockStorage.setBlock(blockState)
    }

    def addEntity(entityIn: Entity): Unit = {
        chunkEntityMap += entityIn
        entityIn.chunkCoordX = pos.posX
        entityIn.chunkCoordY = pos.posY
        entityIn.chunkCoordZ = pos.posZ
        world.addEntity(entityIn)
        entityIn.world = world
    }


    override def toString: String = s"Chunk x=${pos.posX},y=${pos.posY},z=${pos.posZ}"

}


object Chunk {

    val blockSize:Int = 32

    val offset: Int =log2(blockSize)
    println(offset)
    def log2(value:Int,current:Int = 1): Int ={
       if(value>current) log2(value,current*2)+1 else 0
    }

    def getIndex(x: Long, y: Long, z: Long): Long = (x & 16777215L) << 40 | (z & 16777215L) << 16 | (y & 65535L)
}
