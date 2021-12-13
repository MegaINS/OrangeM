package ru.megains.orangem.client.render.gui

import java.awt.Color
import org.lwjgl.glfw.GLFW.{GLFW_KEY_E, GLFW_KEY_ESCAPE}
import ru.megains.mge.Mouse
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.client.render.gui.base.{Gui, GuiGame}
import ru.megains.orangem.client.render.gui.item.GuiItemStack
import ru.megains.orangem.common.container.Container
import ru.megains.orangem.common.entity.player.EntityPlayer
import ru.megains.orangem.common.inventory.Slot
import ru.megains.orangem.common.item.itemstack.ItemStack
import ru.megains.orangem.client.render.gui.base.GuiGame


abstract class GuiContainer(val inventorySlots: Container) extends GuiGame {


    val rect: Model = Gui.createRect(40, 40, new Color(200, 255, 100, 100))
    var stack:ItemStack = _
    var itemStackRender:GuiItemStack =_
    def init(): Unit = {

        addChildren(rect)
        for(slot <- inventorySlots.inventorySlots){
            addChildren( new GuiSlot(slot))
        }
    }

    override def update(): Unit = {
        super.update()
        inventorySlots.inventorySlots.find(inventorySlots.isMouseOverSlot(_, (Mouse.x - posX).toInt, (Mouse.y - posY).toInt)) match {
            case Some(slot) =>
                rect.posX = slot.xPos
                rect.posY = slot.yPos
                rect.active = true
            case _ =>
                rect.active = false
        }

        if(stack !=  gameScene.player.inventory.itemStack){
            stack =  gameScene.player.inventory.itemStack
            if(stack!= null){
                itemStackRender = new GuiItemStack(stack)
            }else{
                itemStackRender = null
            }
        }
    }

    override def render(shader: Shader): Unit = {
        super.render(shader)
        if(itemStackRender!=null){
            itemStackRender.posX = Mouse.getX
            itemStackRender.posY = Mouse.getY
            itemStackRender.render(shader)
        }
    }

    override def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        inventorySlots.mouseClicked(x-posX.toInt,y-posY.toInt,button,player)
       // player.openContainer.mouseClicked(x-posX, y-posY, button, player)
       // tar.playerController.windowClick(x-posX, y-posY, button, player: EntityPlayer)
    }

    def getSlotAtPosition(x: Int, y: Int): Slot = inventorySlots.inventorySlots.find(inventorySlots.isMouseOverSlot(_, x, y)).orNull
}
