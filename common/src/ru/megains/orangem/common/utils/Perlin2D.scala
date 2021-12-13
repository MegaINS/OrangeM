package ru.megains.orangem.common.utils

import scala.util.Random

class Perlin2D(seed:Int) {

    val rand = new Random(seed)
    val permutationTable = new Array[Byte](1024)
    rand.nextBytes(permutationTable)

    def getPseudoRandomGradientVector(x: Int, y: Int): Array[Float] = {
        var v = (((x * 1836311903L) ^ (y * 2971215073L) + 4807526976L) & 1023).toInt
        v = permutationTable(v) & 3
        v match {
            case 0 =>
                Array[Float](1, 0)
            case 1 =>
                Array[Float](-1, 0)
            case 2 =>
                Array[Float](0, 1)
            case _ =>
                Array[Float](0, -1)
        }
    }

    def qunticCurve(t: Float): Float = t * t * t * (t * (t * 6 - 15) + 10)

    def lerp(a: Float, b: Float, t: Float): Float = a + (b - a) * t

    def dot(a: Array[Float], b: Array[Float]): Float = a(0) * b(0) + a(1) * b(1)

    private def noise(fx: Float, fy: Float): Float = {
        val left: Int = Math.floor(fx).asInstanceOf[Int]
        val top: Int = Math.floor(fy).asInstanceOf[Int]
        var pointInQuadX: Float = fx - left
        var pointInQuadY: Float = fy - top

        val topLeftGradient: Array[Float] = getPseudoRandomGradientVector(left, top)
        val topRightGradient: Array[Float] = getPseudoRandomGradientVector(left + 1, top)
        val bottomLeftGradient: Array[Float] = getPseudoRandomGradientVector(left, top + 1)
        val bottomRightGradient: Array[Float] = getPseudoRandomGradientVector(left + 1, top + 1)

        val distanceToTopLeft: Array[Float] = Array[Float](pointInQuadX, pointInQuadY)
        val distanceToTopRight: Array[Float] = Array[Float](pointInQuadX - 1, pointInQuadY)
        val distanceToBottomLeft: Array[Float] = Array[Float](pointInQuadX, pointInQuadY - 1)
        val distanceToBottomRight: Array[Float] = Array[Float](pointInQuadX - 1, pointInQuadY - 1)

        val tx1: Float = dot(distanceToTopLeft, topLeftGradient)
        val tx2: Float = dot(distanceToTopRight, topRightGradient)
        val bx1: Float = dot(distanceToBottomLeft, bottomLeftGradient)
        val bx2: Float = dot(distanceToBottomRight, bottomRightGradient)

        pointInQuadX = qunticCurve(pointInQuadX)
        pointInQuadY = qunticCurve(pointInQuadY)
        val tx: Float = lerp(tx1, tx2, pointInQuadX)
        val bx: Float = lerp(bx1, bx2, pointInQuadX)
        val tb: Float = lerp(tx, bx, pointInQuadY)
        tb
    }

    def noise(fxIn: Float, fyIn: Float, octavesIn: Int, persistence: Float): Float = {
        var fx = fxIn
        var fy = fyIn
        var octaves = octavesIn

        var amplitude: Float = 1
        var max : Float= 0
        var result: Float = 0

        while (octaves > 0) {
            max += amplitude
            result += noise(fx, fy) * amplitude
            amplitude *= persistence
            fx *= 2
            fy *= 2
            octaves -= 1
        }
        result / max
    }

}
