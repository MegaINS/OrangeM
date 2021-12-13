package ru.megains.orangem.client.scene

import org.joml.Vector3i
import org.lwjgl.glfw.GLFW._
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.orangem.common.block.data.BlockState
import ru.megains.orangem.client.{OrangeMClient, PlayerController}
import ru.megains.orangem.client.render.{ChunkRenderer, GameRenderer, GuiRenderer, ImGuiRender}
import ru.megains.orangem.client.utils.Logger
import ru.megains.orangem.common.entity.player.EntityPlayer
import ru.megains.orangem.common.item.ItemBlock
import ru.megains.orangem.common.item.itemstack.ItemStack
import ru.megains.orangem.common.utils.{FrameCounter, RayTraceResult, RayTraceType}
import ru.megains.orangem.common.world.World
import ru.megains.orangem.client.OrangeMClient
import ru.megains.orangem.client.render.GuiRenderer

class GameScene(val game:OrangeMClient) extends Scene with Logger[GameScene]{

    var world:World = new World()
    var player:EntityPlayer = new EntityPlayer(game.playerName)
    val gameRenderer:GameRenderer  = new GameRenderer(this)
    val guiRenderer:GuiRenderer  = new GuiRenderer(this)
    val imGuiRender = new ImGuiRender(this)
    val moved = new Vector3i(0,0,0)
    var rayTrace: RayTraceResult = RayTraceResult.VOID
    var blockSetPosition:BlockState = _


    var playerController:PlayerController = new PlayerController(this)


    override def init(): Unit = {
        world.init()
        world.addEntity(player)
        gameRenderer.init()
        guiRenderer.init()
        imGuiRender.init()
        FrameCounter.start()
        player.world = world

        player.setPosition(0,world.heightMap.getChunkHeightMap(0,0).getHeight(0,0)+1,0)
        Window.window.setSwapInterval(0)
    }

    override def render(): Unit = {

        gameRenderer.render()
        guiRenderer.render()
        imGuiRender.render()
        FrameCounter.gameRender()

    }

    override def update(): Unit = {
        world.update()
        FrameCounter.gameUpdate()

        moved.zero()
        if (glfwGetKey(game.window.id, GLFW_KEY_W) == GLFW_PRESS) moved.add(0,0,-1)
        if (glfwGetKey(game.window.id, GLFW_KEY_S) == GLFW_PRESS) moved.add(0,0,1)
        if (glfwGetKey(game.window.id, GLFW_KEY_A) == GLFW_PRESS) moved.add(-1,0,0)
        if (glfwGetKey(game.window.id, GLFW_KEY_D) == GLFW_PRESS) moved.add(1,0,0)
        if (glfwGetKey(game.window.id, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) moved.add(0,-1,0)
        if (glfwGetKey(game.window.id, GLFW_KEY_SPACE) == GLFW_PRESS) moved.add(0,1,0)

        player.inventory.changeStackSelect(Mouse.getDWheel * -1)

        if (!guiRenderer.isOpenGui)  player.turn(Mouse.getDY.toFloat, Mouse.getDX.toFloat)

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


        while (FrameCounter.isTimePassed(1000)) {



            log.info(s"${FrameCounter.frames} fps, ${FrameCounter.tick} tick, ${ChunkRenderer.chunkUpdate} chunkUpdate, ${ ChunkRenderer.chunkRender/ (if (FrameCounter.frames == 0) 1 else FrameCounter.frames)} chunkRender, ${ChunkRenderer.blockRender/ (if (FrameCounter.frames == 0) 1 else FrameCounter.frames)} blockRender ")
            ChunkRenderer.reset()
            FrameCounter.step(1000)
        }


        gameRenderer.update()
        guiRenderer.update()
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

    }

    override def resize(width:Int,height:Int): Unit =
        guiRenderer.resize(width,height)
}
