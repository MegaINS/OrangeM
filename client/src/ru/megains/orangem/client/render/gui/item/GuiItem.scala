package ru.megains.orangem.client.render.gui.item

import ru.megains.mge.render.MContainer
import ru.megains.orangem.client.register.GameRegisterRender
import ru.megains.orangem.common.item.Item

class GuiItem(item:Item) extends MContainer{
    addChildren( GameRegisterRender.getItemRender(item).getInventoryModel)
}
