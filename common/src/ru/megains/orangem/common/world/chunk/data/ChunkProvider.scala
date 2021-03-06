package ru.megains.orangem.common.world.chunk.data

import ru.megains.orangem.common.utils.Logger
import ru.megains.orangem.common.world.{IChunkProvider, World}
import ru.megains.orangem.common.world.chunk.{Chunk, ChunkGenerator, ChunkVoid}

import java.io.IOException
import scala.collection.mutable


class ChunkProvider(world: World,chunkLoader:ChunkLoader )  extends IChunkProvider with Logger[ChunkProvider]{
    ChunkProvider.voidChunk = ChunkVoid
    val voidChunk: ChunkVoid.type = ChunkVoid
    val chunkMap = new mutable.HashMap[Long,Chunk]()
    val chunkGenerator: ChunkGenerator = new ChunkGenerator(world)

    def getLoadedChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        chunkMap.getOrElse(Chunk.getIndex(chunkX, chunkY, chunkZ), null)
    }

    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        var chunk: Chunk = loadChunk(chunkX, chunkY, chunkZ)

        if (chunk == null) {
            val i: Long = Chunk.getIndex(chunkX, chunkY, chunkZ)
            try {
                chunk = chunkGenerator.provideChunk(chunkX, chunkY, chunkZ)
            } catch {
                case throwable: Throwable =>
                    throwable.printStackTrace()
            }
            chunkMap += i -> chunk
        }
        chunk
    }

    def loadChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        var chunk: Chunk = getLoadedChunk(chunkX, chunkY, chunkZ)
        if (chunk == null) {
            chunk =  chunkLoader.loadChunk(world, chunkX, chunkY, chunkZ)
            if (chunk != null) {
                chunkMap += Chunk.getIndex(chunkX, chunkY, chunkZ) -> chunk
            }
        }
        chunk
    }





    def saveChunks(p_186027_1: Boolean): Boolean = {

        println()
        chunkMap.values.foreach(
            chunk => {

                saveChunkData(chunk)

            }
        )
        chunkLoader.regionLoader.close()
        true
    }
    def saveChunkData(chunkIn: Chunk): Unit = {
        try {
           chunkLoader.saveChunk(chunkIn)
        }
        catch {
            case ioexception: IOException => {
                log.error("Couldn\'t save chunk", ioexception.asInstanceOf[Throwable])
            }
        }
    }

    /*override*/ def unload(chunk: Chunk): Unit = {

    }
}
object ChunkProvider{
    var voidChunk:Chunk = _
}