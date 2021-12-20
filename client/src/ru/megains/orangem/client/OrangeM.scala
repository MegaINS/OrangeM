package ru.megains.orangem.client

import ru.megains.mge.{Game, Mouse, Scene, Window}
import ru.megains.orangem.client.register.{Bootstrap, GameRegisterRender}
import ru.megains.orangem.client.render.texture.GameTextureManager
import ru.megains.orangem.client.scene.{SceneGame, SceneGui}
import ru.megains.orangem.client.utils.Logger
import ru.megains.orangem.common.PacketProcess
import ru.megains.orangem.common.network.NetworkManager
import ru.megains.orangem.common.utils.Timer
import ru.megains.orangem.client.register.Bootstrap
import ru.megains.orangem.client.render.ChunkRenderer
import ru.megains.orangem.client.render.gui.base.GuiScreen
import ru.megains.orangem.client.render.gui.menu.GuiPlayerSelect
import ru.megains.orangem.client.render.shader.ShaderManager
import ru.megains.orangem.common.world.data.AnvilSaveFormat

import scala.collection.mutable
import scala.reflect.io.Path


class OrangeM(config: Configuration) extends Logger[OrangeM] with Game with PacketProcess {


    var playerName: String = ""
    val gameSettings = new GameSettings(this, config.filePath)
    val saveLoader = new AnvilSaveFormat(Path("W:/OrangeM/Client").toDirectory)
    var networkManager: NetworkManager = _
    var running = true
    var window = new Window(config.width, config.height, config.title)
    var scene:Scene = _
    val timerU = new Timer(20)
    val timerFPS = new Timer(gameSettings.FPS)
    val textureManager = new GameTextureManager()
    val futureTaskQueue: mutable.Queue[() => Unit] = new mutable.Queue[() => Unit]

    //lazy val guiScene = new GuiScene1(this)
   // var gameScene: GameScene = _

    def start(): Unit = {
        if (init()) {
            gameLoop()
            destroy()
        } else {
            error()
        }
    }

    private def gameLoop(): Unit = {

        while (running) {

            if (window.isClose) running = false


            timerFPS.update()
            for (_ <- 0 until timerFPS.tick) {
                render()
            }

            timerU.update()
            for (_ <- 0 until timerU.tick) {
                update()
            }
            Thread.sleep(1)

        }
        stop()
    }


    private def init(): Boolean = {
        window.create(this)
        Mouse.init(this)
        Bootstrap.init()
        ShaderManager.init()
        textureManager.init()
        ChunkRenderer.game = this
        GameRegisterRender.entityData.idRender.values.foreach(_.init())
        setScene(new SceneGui(this))
        timerU.init()
        timerFPS.init()
        true
    }

    def stop(): Unit = {
        if (networkManager != null) {
            networkManager.closeChannel("")
        }
    }

    private def update(): Unit = {
        futureTaskQueue synchronized {
            while (futureTaskQueue.nonEmpty) {
                val a = futureTaskQueue.dequeue()
                if (a != null) a()
            }

        }
        scene.update()
        Mouse.update()
    }

    private def render(): Unit = {

        scene.render()
        window.update()
    }

    private def destroy(): Unit = {
        window.destroy()
    }

    private def error(): Unit = {
    }

    override def runTickMouse(button: Int,action: Int, mods: Int): Unit = scene.runTickMouse(button,action, mods)

    override def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = scene.runTickKeyboard(key: Int, action: Int, mods: Int)

    override def addPacket(packet: () => Unit): Unit = {
        futureTaskQueue += packet
    }

    override def setScene(sceneIn: Scene): Unit = {
        if(scene!=null) scene.destroy()
        scene = sceneIn
        scene.init()
    }

    override def resize(width:Int,height:Int): Unit ={
        scene.resize(width,height)
    }

}

object OrangeM {
    def getSystemTime: Long = System.currentTimeMillis
}
