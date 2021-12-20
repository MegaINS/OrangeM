package ru.megains.mge
package render

import org.joml.Matrix4f
import ru.megains.mge.render.shader.Shader

abstract class MObject {


    var parent: MObject = _

    val matrix: Matrix4f = new Matrix4f()

    var posX: Float = 0.0F
    var posY: Float = 0.0F
    var posZ: Float = 0.0F
    var scale: Float = 1.0F
    var xRot: Float = 0.0F
    var yRot: Float = 0.0F
    var zRot: Float = 0.0F

    def buildMatrix(): Matrix4f = {
        matrix.identity
        matrix.translate(posX, posY, posZ)
        matrix.rotateXYZ(Math.toRadians(xRot).toFloat, Math.toRadians(yRot).toFloat, Math.toRadians(zRot).toFloat)
        matrix.scale(scale)

        parent match {
            case null =>
                matrix
            case _ =>
                parent.buildMatrix().mul(matrix)
        }
    }

    def render(shader: Shader): Unit= {

    }

    def update(): Unit= {

    }

    def mouseMove(x:Int,y:Int):Unit = {

    }

    def mousePress(x:Int,y:Int,button: Int):Unit= {

    }

    def mouseRelease(x:Int,y:Int,button: Int):Unit= {

    }

    def resize(width:Int,height:Int):Unit= {

    }

}
