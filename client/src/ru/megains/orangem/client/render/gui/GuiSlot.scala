package ru.megains.orangem.client.render.gui

import ru.megains.mge.render.MContainer
import ru.megains.orangem.client.render.gui.item.GuiItemStack
import ru.megains.orangem.common.inventory.Slot
import ru.megains.orangem.common.item.itemstack.ItemStack

class GuiSlot(slot:Slot) extends MContainer {


    var itemStackRender:GuiItemStack = _
    var stack:ItemStack = _
    posX = slot.xPos
    posY = slot.yPos
    override def update(): Unit = {
        if (stack != slot.getStack) {
            removeChildren(itemStackRender)
            val slotStack = slot.getStack

            slotStack match {
                case null =>
                    stack = null
                case _ =>
                    stack = new ItemStack(slot.getStack)
                    itemStackRender = new GuiItemStack(stack)
                    addChildren(itemStackRender)
            }
        }
    }
}
