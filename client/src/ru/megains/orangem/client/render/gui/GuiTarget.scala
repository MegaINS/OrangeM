package ru.megains.orangem.client.render.gui

import ru.megains.mge.Window
import ru.megains.mge.render.MSprite
import ru.megains.mge.render.texture.Texture
import ru.megains.orangem.client.render.gui.base.GuiUI

class GuiTarget extends GuiUI {

    var target: MSprite = _

    override def init(): Unit = {
        target = new MSprite(new Texture("textures/gui/target.png"), 40, 40)
        addChildren(target)
        resize(Window.width,Window.height)
    }

    override def resize(width:Int,height:Int): Unit = {
        posY = height / 2 - 20
        posX = width / 2 - 20
    }
}
