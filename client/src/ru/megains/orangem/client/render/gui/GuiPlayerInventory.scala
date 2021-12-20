package ru.megains.orangem.client.render.gui

import org.lwjgl.glfw.GLFW._
import ru.megains.mge.Window
import ru.megains.mge.render.MSprite
import ru.megains.mge.render.texture.Texture
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.scene.SceneGame
import ru.megains.orangem.common.entity.player.EntityPlayer

class GuiPlayerInventory(entityPlayer: EntityPlayer,gameScene: SceneGame) extends GuiContainer(entityPlayer.inventoryContainer,gameScene: SceneGame) {

    val playerInventory = new MSprite(new Texture("textures/gui/playerInventory.png"), 500, 240)

    override def init(gameIn: OrangeM): Unit = {
        super.init(gameIn)
        addChildren(playerInventory)
    }

    override def resize(width:Int,height:Int): Unit = {
        posX = (width - 500) / 2
        posY = height - 240
    }
}
