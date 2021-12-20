package ru.megains.orangem.client.scene

import org.lwjgl.glfw.GLFW.{GLFW_PRESS, GLFW_RELEASE}
import org.lwjgl.opengl.GL11.{GL_BLEND, GL_CULL_FACE, GL_DEPTH_TEST, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_STENCIL_TEST, glBlendFunc, glDisable, glEnable}
import ru.megains.mge.render.MContainer
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.render.gui.base.GuiScreen
import ru.megains.orangem.client.render.gui.menu.GuiPlayerSelect
import ru.megains.orangem.client.render.shader.ShaderManager

class SceneGui(orangeM: OrangeM) extends Scene{

    val Z_FAR: Float = 100
    val shader: Shader = ShaderManager.guiShader
    var camera: OrthographicCamera = new OrthographicCamera(0, Window.width,Window.height, 0, -100, Z_FAR)
    var guiScreen:GuiScreen = _

    override def init(): Unit = {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        setGuiScreen(new GuiPlayerSelect(orangeM))
    }

    def setGuiScreen(guiScreenIn:GuiScreen): Unit ={
        guiScreen = guiScreenIn
        guiScreen.init(orangeM)
        guiScreen.resize(Window.width,Window.height)
    }


    override def render(): Unit = {

            glEnable(GL_STENCIL_TEST)
            glEnable(GL_BLEND)
            glEnable(GL_CULL_FACE)
            glDisable(GL_DEPTH_TEST)
            camera.setOrtho(0, Window.width,Window.height, 0, -100, Z_FAR)
            ShaderManager.bindShader(shader)
            shader.setUniform(camera)

        guiScreen.render(shader)

            ShaderManager.unbindShader()

            glDisable(GL_BLEND)
            glDisable(GL_CULL_FACE)
            glEnable(GL_DEPTH_TEST)

    }

    override def update(): Unit = {
        guiScreen.mouseMove(Mouse.x.toInt,Mouse.y.toInt)
        guiScreen.update()
    }

    override def resize(width: Int, height: Int): Unit = {
        guiScreen.resize(width, height)
    }

    override def runTickMouse(button: Int, action: Int, mods: Int): Unit = {
        action match {
            case GLFW_PRESS=>
                guiScreen.mousePress(Mouse.getX,Mouse.getY,button)
            case GLFW_RELEASE =>
                guiScreen.mouseRelease(Mouse.getX,Mouse.getY,button)
        }
    }
    override def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = ???

    override def destroy(): Unit = {}
}
