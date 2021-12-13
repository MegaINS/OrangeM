package ru.megains.orangem.client.render.gui.item

import java.awt.Color
import ru.megains.mge.render.MContainer
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.text.Label
import ru.megains.orangem.client.render.gui.base.Gui
import ru.megains.orangem.common.item.itemstack.ItemStack

class GuiItemStack(itemStack:ItemStack) extends MContainer{

    val stackSize: Label = new Label(itemStack.stackSize.toString){
        posY = 26
        scale = 0.7f
    }
    val stackMass: Label = new Label(itemStack.stackMass.toString){
        posX = 23
        posY = 26
        scale = 0.7f
    }
    val cubeSize: Model = Gui.createRect(40,15,Color.WHITE)
        cubeSize.posY = 25

    addChildren(new GuiItem(itemStack.item))
    addChildren(stackSize,stackMass)

}
