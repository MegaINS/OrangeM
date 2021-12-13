package ru.megains.orangem.client.render.gui

import ru.megains.mge.Window
import ru.megains.mge.render.{MContainer, MSprite}
import ru.megains.mge.render.texture.Texture
import ru.megains.orangem.client.render.gui.base.GuiUI

class GuiHotBar() extends GuiUI {

    var hotBar: MSprite = _
    var stackSelect: MSprite = _

    override def init(): Unit = {
        stackSelect = new MSprite(new Texture("textures/gui/stackSelect.png"), 56, 54)
        hotBar = new MSprite(new Texture("textures/gui/hotBar.png"), 484, 52) {
            posX = 2
            posY = 2
        }

        posY = Window.height - 54
        posX = (Window.width - 488) / 2

        addChildren(hotBar, stackSelect)

        val container = new MContainer
        container.posX = -6
        for (i <- 0 to 9) {
            val slot = new GuiSlot(gameScene.player.inventoryContainer.inventorySlots(i)) {
                posY = 7
            }
            container.addChildren(slot)
        }

        addChildren(container)
    }




    override def update(): Unit = {
        super.update()
        posX = (Window.width - 488) / 2
        stackSelect.posX = gameScene.player.inventory.stackSelect * 48
    }

    override def resize(width:Int,height:Int): Unit ={
        posY = height - 54
        posX = (width - 488) / 2
    }

}
