package ru.megains.orangem.common.world

import ru.megains.orangem.common.world.chunk.{Chunk, ChunkPosition}

abstract class IChunkProvider {
    def unload(chunk: Chunk): Unit


    def loadChunk(pos: ChunkPosition): Chunk = {
        loadChunk(pos.posX, pos.posY, pos.posZ)
    }

    def loadChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk

    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk

    def saveChunks(p_186027_1: Boolean): Boolean

}
