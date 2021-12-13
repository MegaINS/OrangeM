package ru.megains.orangem.common.world.chunk

case class ChunkPosition(posX:Int,posY:Int,posZ:Int) {

    val blockPosX: Int = posX * Chunk.blockSize
    val blockPosY: Int = posY * Chunk.blockSize
    val blockPosZ: Int = posZ * Chunk.blockSize

}
