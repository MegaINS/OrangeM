package ru.megains.orangem.common.world.chunk

import ru.megains.orangem.noise.NoiseGenerator
import ru.megains.orangem.common.register.Blocks
import ru.megains.orangem.common.utils.Perlin2D
import ru.megains.orangem.common.world.World
import ru.megains.orangem.common.world.chunk.data.ChunkHeightMap

import scala.util.Random


class ChunkGenerator(world: World) {

    val noiseGenerator = new NoiseGenerator
    val perlin2D = new Perlin2D(10)




    def generateHeight(x: Int, z: Int): Int = {
        (perlin2D.noise(x/5f,z/5f,1,0.8F) * 64 + 16).toInt
    }


    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        val chunk = new Chunk(ChunkPosition(chunkX, chunkY, chunkZ), world)
        val blockStorage: BlockStorage = chunk.blockStorage
        val blockData = blockStorage.blockId

        val chunkHeightMap: ChunkHeightMap = world.heightMap.getChunkHeightMap(chunkX, chunkZ)

        val trees: Array[Array[Int]] = new Array[Array[Int]](Chunk.blockSize)
        for (i <- 0 until  Chunk.blockSize) {
            trees(i) = new Array[Int](Chunk.blockSize)
        }

        for(x<-chunk.pos.blockPosX until chunk.pos.blockPosX+ Chunk.blockSize;
            z<-chunk.pos.blockPosZ until  chunk.pos.blockPosZ+Chunk.blockSize){
            val height =  generateHeight(x, z)
            trees(x-chunk.pos.blockPosX)(z-chunk.pos.blockPosZ) = if(height>46) 1 else 0
        }





        if(chunkHeightMap.getMaxHeight < (chunkY) * Chunk.blockSize){

        }else if (chunkHeightMap.getMinHeight > (chunkY+1) * Chunk.blockSize) {
            for (i <- 0 until Chunk.blockSize*Chunk.blockSize*Chunk.blockSize) {
                blockData(i) = Blocks.getIdByBlock(Blocks.stone).toShort
            }

        }else {
            for (x1 <- 0 until  Chunk.blockSize; y1 <- 0 until  Chunk.blockSize; z1 <- 0 until  Chunk.blockSize) {
                val height = chunkHeightMap.getHeight(x1, z1)
                if (height-1 > y1 + (chunkY * Chunk.blockSize)) {
                    blockStorage.setBlock(x1, y1, z1, Blocks.stone)
                } else if (height == y1 + (chunkY * Chunk.blockSize)) {
                    blockStorage.setBlock(x1, y1, z1, Blocks.grass)
                    if(trees( x1)(z1)==1){
                        createTree(x1,y1+1,z1,blockStorage)
                    }
                }else if( height-1 == y1 + (chunkY * Chunk.blockSize)){
                    blockStorage.setBlock(x1, y1, z1, Blocks.dirt)
                }
            }
        }
//    var min: Double = Double.MaxValue
//        var max: Double = Double.MinValue
//            //if(chunkY == 0 ||chunkY == 1|| chunkY == -1 ) {
//                    for (x1 <- 0 until  Chunk.blockSize; y1 <- 0 until  Chunk.blockSize; z1 <- 0 until  Chunk.blockSize) {
//                       val a = noiseGenerator.noise(x1+chunkX*Chunk.blockSize,y1+chunkY*Chunk.blockSize,z1+chunkZ*Chunk.blockSize) + (y1+chunkY*Chunk.blockSize)/64f
//                        if (a < min) min = a
//                        if (a > max) max = a
//                        if (a < 0) {
//                            blockStorage.setBlock(x1, y1, z1, Blocks.stone)
//                        }
//                    }
//           // }
//
//        println("Min "+ min)
//        println("Max "+ max)
        chunk
    }

    def createTree(xIn:Int,yIn:Int,zIn:Int,blockStorage:BlockStorage): Unit ={


        val treeHeight = 3 + 4 + Random.nextInt(2)
        for(y<-yIn to yIn + treeHeight ){
            var var13 = 0
            val var20 = 0
            var var21 = 0
            if(y>yIn+ 3){

                var21 = y - (yIn + treeHeight)
                var13 = var20 + 1 - var21 / 2

                val x1 = if(xIn-var13<0) 0 else xIn-var13
                val x2 = if(xIn+var13>Chunk.blockSize-1) Chunk.blockSize-1 else xIn+var13

                val z1 = if(zIn-var13<0) 0 else zIn-var13
                val z2 = if(zIn+var13>Chunk.blockSize-1) Chunk.blockSize-1 else zIn+var13

                for(x<-x1 to x2;
                    z<-z1 to z2){
                        blockStorage.setBlock(x,y,z,Blocks.leaves_oak)

                }
            }
            blockStorage.setBlock(xIn,y,zIn,Blocks.log_oak)
        }


    }



}
