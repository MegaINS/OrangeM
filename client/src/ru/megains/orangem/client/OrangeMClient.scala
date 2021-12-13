package ru.megains.orangem.client

import ru.megains.mge.{Game, Mouse, Scene, Window}
import ru.megains.orangem.client.register.{Bootstrap, GameRegisterRender}
import ru.megains.orangem.client.render.texture.GameTextureManager
import ru.megains.orangem.client.scene.{MainMenuScene, PlayerSelectScene}
import ru.megains.orangem.client.utils.Logger
import ru.megains.orangem.common.PacketProcess
import ru.megains.orangem.common.network.NetworkManager
import ru.megains.orangem.common.utils.Timer
import ru.megains.orangem.client.register.Bootstrap

import scala.collection.mutable


class OrangeMClient(config: Configuration) extends Logger[OrangeMClient] with Game with  PacketProcess{


    var playerName: String = ""


    var networkManager:NetworkManager = _
    var running = true
    var window = new Window(config.width,config.height,config.title)
    var scene:Scene = _
    val timerU = new Timer(20)
    val timerR = new Timer(60)
    val textureManager = new GameTextureManager()
    val futureTaskQueue: mutable.Queue[()=>Unit] = new mutable.Queue[()=>Unit]
    def start(): Unit ={
        if(init()){
            gameLoop()
            destroy()
        }else{
            error()
        }
    }

    private def gameLoop(): Unit ={

        while (running) {

            if (window.isClose) running = false


            timerR.update()
            for (_ <- 0 until timerR.tick) {
                render()
            }

            timerU.update()
            for (_ <- 0 until timerU.tick) {
                update()
            }


        }
        stop()
    }


    private def init(): Boolean ={

        window.create(this)
        Mouse.init(this)
        Bootstrap.init()
        textureManager.init()
        GameRegisterRender.entityData.idRender.values.foreach(_.init())
        setScene(new PlayerSelectScene(this))
        timerU.init()
        timerR.init()
        true
    }

    def stop(): Unit ={
        if(networkManager!= null){
            networkManager.closeChannel("")
        }
    }

    private def update(): Unit = {
        futureTaskQueue synchronized {
            while (futureTaskQueue.nonEmpty){
                val a = futureTaskQueue.dequeue()
                if(a!= null) a()
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

    private def error(): Unit ={
    }

    override def runTickMouse(button: Int,action: Int, mods: Int): Unit = scene.runTickMouse(button,action, mods)

    override def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = scene.runTickKeyboard(key: Int, action: Int, mods: Int)

    override def addPacket(packet:()=>Unit): Unit = {
        futureTaskQueue += packet
    }

    override def setScene(sceneIn: Scene): Unit = {
        if(scene!= null){
            scene.destroy()
        }
        scene = sceneIn
        scene.init()
    }

    override def resize(width:Int,height:Int): Unit ={
        scene.resize(width,height)
    }

}

object OrangeMClient{
    def getSystemTime: Long = System.currentTimeMillis
}
