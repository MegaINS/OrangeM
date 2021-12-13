package ru.megains.orangem.client.render.gui

import org.lwjgl.glfw.GLFW._
import ru.megains.mge.Window
import ru.megains.mge.render.MSprite
import ru.megains.mge.render.texture.Texture
import ru.megains.orangem.common.entity.player.EntityPlayer

class GuiPlayerInventory(entityPlayer: EntityPlayer) extends GuiContainer(entityPlayer.inventoryContainer) {

    override def init(): Unit = {
        val playerInventory = new MSprite(new Texture("textures/gui/playerInventory.png"), 500, 240)

        addChildren(playerInventory)
        posX = (Window.width - 500) / 2
        posY = Window.height - 240
        super.init()
    }

    override def resize(width:Int,height:Int): Unit = {
        posX = (width - 500) / 2
        posY = height - 240
    }
}
