package ru.megains.orangem.client.scene

import org.joml.Vector3i
import org.lwjgl.glfw.GLFW._
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.orangem.common.block.data.BlockState
import ru.megains.orangem.client.{GameSettings, OrangeM, PlayerController}
import ru.megains.orangem.client.render.{ChunkRenderer, ImGuiRender, RendererGame, RendererGui}
import ru.megains.orangem.client.utils.Logger
import ru.megains.orangem.common.entity.player.EntityPlayer
import ru.megains.orangem.common.item.ItemBlock
import ru.megains.orangem.common.item.itemstack.ItemStack
import ru.megains.orangem.common.utils.{FrameCounter, RayTraceResult, RayTraceType}
import ru.megains.orangem.common.world.World
import ru.megains.orangem.client.render.RendererGui
import ru.megains.orangem.common.world.data.AnvilSaveHandler

class SceneGame(val game:OrangeM,saveHandler: AnvilSaveHandler) extends Scene with Logger[SceneGame]{

    var world:World = new World(saveHandler)
    val settings: GameSettings = game.gameSettings
    var player:EntityPlayer = new EntityPlayer(game.playerName)
    val gameRenderer:RendererGame  = new RendererGame(this)
    val guiRenderer:RendererGui  = new RendererGui(this)
    val moved = new Vector3i(0,0,0)
    var rayTrace: RayTraceResult = RayTraceResult.VOID
    var blockSetPosition:BlockState = _


    var playerController:PlayerController = new PlayerController(this)


    override def init(): Unit = {
        world.init()
        world.addEntity(player)
        gameRenderer.init()
        guiRenderer.init()
        FrameCounter.start()
        player.world = world

        player.setPosition(16,world.heightMap.getChunkHeightMap(0,0).getHeight(16,16)+1,16)
        Window.window.setSwapInterval(0)
    }

    override def render(): Unit = {

        gameRenderer.render()
        guiRenderer.render()
        FrameCounter.gameRender()

    }

    override def update(): Unit = {
        world.update()
        FrameCounter.gameUpdate()
        if(!guiRenderer.isOpenGui){
            moved.zero()
            if (glfwGetKey(game.window.id, GLFW_KEY_W) == GLFW_PRESS) moved.add(0,0,-1)
            if (glfwGetKey(game.window.id, GLFW_KEY_S) == GLFW_PRESS) moved.add(0,0,1)
            if (glfwGetKey(game.window.id, GLFW_KEY_A) == GLFW_PRESS) moved.add(-1,0,0)
            if (glfwGetKey(game.window.id, GLFW_KEY_D) == GLFW_PRESS) moved.add(1,0,0)
            if (glfwGetKey(game.window.id, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) moved.add(0,-1,0)
            if (glfwGetKey(game.window.id, GLFW_KEY_SPACE) == GLFW_PRESS) moved.add(0,1,0)

            player.inventory.changeStackSelect(Mouse.getDWheel * -1)

            player.turn(Mouse.getDY.toFloat, Mouse.getDX.toFloat)

            player.update(moved.x,moved.y,moved.z)


            rayTrace = player.rayTrace(5)

            if (rayTrace.traceType == RayTraceType.BLOCK) {

                val stack: ItemStack = player.inventory.getStackSelect
                if(stack != null){
                    blockSetPosition = stack.item match {
                        case itemBlock:ItemBlock =>itemBlock.block.getSelectPosition(world, player, rayTrace)
                        case _ => null
                    }
                } else  blockSetPosition = null
            }else blockSetPosition = null
            gameRenderer.update()
        }

        guiRenderer.update()


        while (FrameCounter.isTimePassed(1000)) {



            log.info(s"${FrameCounter.frames} fps, ${FrameCounter.tick} tick, ${ChunkRenderer.chunkUpdate} chunkUpdate, ${ ChunkRenderer.chunkRender/ (if (FrameCounter.frames == 0) 1 else FrameCounter.frames)} chunkRender, ${ChunkRenderer.blockRender/ (if (FrameCounter.frames == 0) 1 else FrameCounter.frames)} blockRender ")
            ChunkRenderer.reset()
            FrameCounter.step(1000)
        }

    }

    def runTickMouse(button: Int,action: Int, mods: Int): Unit = {
        if(guiRenderer.isOpenGui){
            guiRenderer.runTickMouse(button: Int, action, mods)
        }else{
            playerController.runTickMouse(button: Int, action, mods)
        }
    }

    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit ={
        if(guiRenderer.isOpenGui){
            guiRenderer.runTickKeyboard(key: Int, action: Int, mods: Int)
        }else{
            playerController.runTickKeyboard(key: Int, action: Int, mods: Int)
        }
    }


    override def destroy(): Unit = {
        world.save()
        gameRenderer.destroy()
       // guiRenderer.destroy()
    }

    override def resize(width:Int,height:Int): Unit =
        guiRenderer.resize(width,height)
}
