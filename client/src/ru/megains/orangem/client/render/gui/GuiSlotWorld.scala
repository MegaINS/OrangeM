package ru.megains.orangem.client.render.gui

import ru.megains.mge.render.MContainer
import ru.megains.mge.render.mesh.Mesh
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.text.Label
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.render.gui.base.Gui
import ru.megains.orangem.client.render.gui.element.GuiElement

import java.awt.Color

class GuiSlotWorld(id: Int, val worldName: String, orangeM: OrangeM) extends GuiElement {

    override val width: Int = 400
    override val height: Int = 60


    val label: Label = new Label(worldName) {
        posX = 10
        posY = 22
    }

    val slotSelect: Model = Gui.createRect(width, height, Color.LIGHT_GRAY)
    val slot: Model = Gui.createRect(width, height, Color.BLACK)
    slotSelect.active = false

    var _select: Boolean = false

    addChildren(slotSelect, slot, label)


    def select: Boolean = _select

    def select_=(select:Boolean): Unit = {
        slotSelect.active = select
        slot.active = !select
    }





    override def resize(width: Int, height: Int): Unit = {

    }



    override def init(): Unit = {}

}
