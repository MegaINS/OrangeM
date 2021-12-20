package ru.megains.orangem.common.world.data

import ru.megains.orangem.common.world.chunk.data.ChunkLoader

abstract class ISaveHandler {

    def getChunkLoader: ChunkLoader
}
