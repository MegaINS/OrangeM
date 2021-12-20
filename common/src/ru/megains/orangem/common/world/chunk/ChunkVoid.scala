package ru.megains.orangem.common.world.chunk

object ChunkVoid extends Chunk(ChunkPosition(0,0,0),null) {
   override val isEmpty: Boolean = true
}
