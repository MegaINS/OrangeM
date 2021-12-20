package ru.megains.orangem.common.world.data

import ru.megains.orangem.common.world.World
import ru.megains.orangem.common.world.chunk.{Chunk, ChunkPosition}

class ChunkVoid(world:World) extends Chunk(ChunkPosition(0,0,0),world) {

}
