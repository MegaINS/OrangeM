package ru.megains.orangem.common.world.chunk

case class ChunkPosition(x:Int, y:Int, z:Int) {

    def blockX: Int = x * Chunk.blockSize
    def blockY: Int = y * Chunk.blockSize
    def blockZ: Int = z * Chunk.blockSize

}
