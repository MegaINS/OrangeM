package ru.megains.mge
package render

import ru.megains.mge.render.shader.Shader

import scala.collection.mutable.ArrayBuffer

class MContainer extends MObject {

    val _children:ArrayBuffer[MObject] = ArrayBuffer[MObject]()

    override def render(shader: Shader): Unit = {
        _children.foreach(_.render(shader))

    }

    def addChildren(children:MObject*): Unit ={
        children.foreach(_.parent = this)
        _children.addAll(children)
    }

    def removeChildren(children:MObject*): Unit = {
        children.foreach(_children -= _)
    }

    override def update(): Unit = {
        _children.foreach(_.update())
    }

    override def mousePress(x: Int, y: Int): Unit = {
        _children.foreach(_.mousePress(x- posX.toInt,y - posY.toInt))
    }

    override def mouseRelease(x:Int,y:Int):Unit= {
        _children.foreach(_.mouseRelease(x- posX.toInt,y - posY.toInt))
    }

    override def mouseMove(x: Int, y: Int): Unit = {
        _children.foreach(_.mouseMove(x- posX.toInt,y- posY.toInt))

    }

}
