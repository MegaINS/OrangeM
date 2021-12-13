package ru.megains.orangem.common.world

import ru.megains.orangem.common.utils.Perlin2D
import ru.megains.orangem.common.world.chunk.Chunk
import ru.megains.orangem.common.world.chunk.data.ChunkHeightMap

import scala.collection.mutable

class WorldHeightMap(val seed: Int) {

    val perlin2D = new Perlin2D(seed)
    val ChunkHeightMaps: mutable.HashMap[Long, ChunkHeightMap] = new mutable.HashMap[Long, ChunkHeightMap]

    def getChunkHeightMap(x: Int, z: Int): ChunkHeightMap = {
        ChunkHeightMaps.getOrElseUpdate( getIndex(x, z), generateHeightMap(x, z))
    }

    private def generateHeightMap(xIn: Int, zIn: Int): ChunkHeightMap = {
        val array: Array[Array[Int]] = new Array[Array[Int]](Chunk.blockSize)
        for (i <- 0 until  Chunk.blockSize) {
            array(i) = new Array[Int](Chunk.blockSize)
        }

        for(x<-0 until  Chunk.blockSize;
            z<-0 until  Chunk.blockSize){
            val height =  generateHeight(x+xIn*Chunk.blockSize, z+zIn*Chunk.blockSize)
            array(x)(z) = if(height<0) height >> 2 else height
        }

       new ChunkHeightMap(array)
    }


    def generateHeight(x: Int, z: Int): Int = {
        (perlin2D.noise(x/100f,z/100f,8,0.3F) * 32 + 8).toInt
    }

    def getIndex(x: Long, z: Long): Long = (x & 16777215l) << 24 | (z & 16777215l)



}
