package ru.megains.orangem.common.world.chunk.data

import scala.math.Numeric.IntIsIntegral


class ChunkHeightMap(heightMap: Array[Array[Int]]) {

    def getHeight(x: Int, z: Int):Int = heightMap(x)(z)

    def getMinHeight:Int = heightMap.map(_.min).min
    def getMaxHeight:Int = heightMap.map(_.max).max
}
