package ru.megains.orangem.client.render

import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.opengl.GL11._
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.mge.{Mouse, Window}
import ru.megains.orangem.client.render.gui.{GuiBlockSelect, GuiDebugInfo, GuiHotBar, GuiPlayerInventory, GuiTarget}
import ru.megains.orangem.client.render.gui.base.{GuiGame, GuiOverlay, GuiUI}
import ru.megains.orangem.client.render.shader.GuiShader
import ru.megains.orangem.client.scene.GameScene
import ru.megains.orangem.client.render.gui.base.GuiUI
import ru.megains.orangem.client.render.gui._

import scala.collection.mutable

class GuiRenderer(gameScene: GameScene) {


    val Z_FAR: Float = 100
    var shader: Shader = new GuiShader()
    var camera: OrthographicCamera = new OrthographicCamera(0, Window.width, Window.height, 0, -100, Z_FAR)

    val guiUIMap: mutable.HashMap[String, GuiUI] = new mutable.HashMap[String, GuiUI]()
    val guiOverlayMap: mutable.HashMap[String, GuiOverlay] = new mutable.HashMap[String, GuiOverlay]()
    val guiPlayerInventory: GuiPlayerInventory = new GuiPlayerInventory(gameScene.player)

    private var openGui: GuiGame = _

    def init(): Unit = {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        shader.create()

        addGuiUI("guiDebugInfo", new GuiDebugInfo())
        addGuiUI("guiBlockSelect", new GuiBlockSelect())
        addGuiUI("guiHotBar", new GuiHotBar())
        addGuiUI("guiTarget", new GuiTarget())


        guiPlayerInventory.init(gameScene)
    }

    def update(): Unit = {

        if (openGui == null) {
            guiUIMap.values.foreach(_.update())
        } else {
            guiUIMap.values.foreach(_.update())
            openGui.update()
            guiOverlayMap.values.foreach(_.update())
        }
    }


    def render(): Unit = {


        glEnable(GL_STENCIL_TEST)
        glEnable(GL_BLEND)
        glEnable(GL_CULL_FACE)
        glDisable(GL_DEPTH_TEST)
        camera.setOrtho(0, Window.width, Window.height, 0, -100, Z_FAR)
        shader.bind()
        shader.setUniform(camera)

        if (openGui == null) {
            guiUIMap.values.toArray.sortWith((a, b) => a.posZ < b.posZ).foreach(_.render(shader))
        } else {
            openGui.render(shader)
            guiOverlayMap.values.foreach(_.render(shader))
        }

        shader.unbind()

        glDisable(GL_BLEND)
        glDisable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)
    }

    def resize(width:Int,height:Int): Unit = {
        guiUIMap.values.foreach(_.resize(width,height))
        guiOverlayMap.values.foreach(_.resize(width,height))
        if (openGui != null) openGui.resize(width,height)
    }

    def openGui(guiGame: GuiGame): Unit = {
        openGui = guiGame
        Mouse.setGrabbed(false)
    }

    def openPlayerInventory(): Unit = {
        openGui(guiPlayerInventory)
    }

    def addGuiUI(name: String, guiUI: GuiUI): Unit = {
        guiUI.init(gameScene)
        guiUIMap += name -> guiUI
    }

    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {
        if (action == GLFW_PRESS) {
            openGui.keyTyped(key.toChar, key)
        }
    }

    def runTickMouse(button: Int, action: Int, mods: Int): Unit = {
        val x = Mouse.getX
        val y = Mouse.getY
        if (button == -1) {
            openGui.mouseClickMove(x, y)
        } else if (action == GLFW_PRESS) {
            openGui.mouseClicked(x, y, button, gameScene.player)
        } else {
            openGui.mouseReleased(x, y, button)
        }

    }

    def closeGui(): Unit = {
        openGui = null
        Mouse.setGrabbed(true)
    }

    def isOpenGui: Boolean = openGui != null
}
