package ru.megains.orangem.client.scene

import org.lwjgl.opengl.GL11.{GL_BLEND, GL_CULL_FACE, GL_DEPTH_TEST, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_STENCIL_TEST, glBlendFunc, glDisable, glEnable}
import ru.megains.mge.render.MContainer
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.client.render.shader.GuiShader
import org.lwjgl.glfw.GLFW._

class BaseScene extends MContainer with Scene {
    val Z_FAR: Float = 100
    var shader: Shader = new GuiShader()
    var camera: OrthographicCamera = new OrthographicCamera(0, Window.width,Window.height, 0, -100, Z_FAR)

    override def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = ???

    override def init(): Unit = {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        shader.create()
        resize(Window.width,Window.height)
    }

    override def render(): Unit = {
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_BLEND)
        glEnable(GL_CULL_FACE)
        glDisable(GL_DEPTH_TEST)
        camera.setOrtho(0, Window.width,Window.height, 0, -100, Z_FAR)
        shader.bind()
        shader.setUniform(camera)

        super.render(shader)

        shader.unbind()

        glDisable(GL_BLEND)
        glDisable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)
    }

    override def update(): Unit = {
        super.update()
        mouseMove(Mouse.x.toInt,Mouse.y.toInt)
    }


    override def resize(width:Int,height:Int): Unit = {

    }

    override def destroy(): Unit = {

    }

    override def runTickMouse(button: Int, action: Int, mods: Int): Unit = {
        action match {
            case GLFW_PRESS=>
                mousePress(Mouse.getX,Mouse.getY)
            case GLFW_RELEASE =>
                mouseRelease(Mouse.getX,Mouse.getY)
        }
    }

}
