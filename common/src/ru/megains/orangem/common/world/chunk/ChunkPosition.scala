package ru.megains.orangem.common.world.chunk

case class ChunkPosition(posX:Int,posY:Int,posZ:Int) {

    def blockPosX: Int = posX * Chunk.blockSize
    def blockPosY: Int = posY * Chunk.blockSize
    def blockPosZ: Int = posZ * Chunk.blockSize

}
