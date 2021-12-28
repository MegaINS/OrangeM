package ru.megains.orangem.client.render.item

import ru.megains.mge.render.model.Model
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.client.render.api.TRenderTexture


trait  TRenderItem extends TRenderTexture {

   // def getItemGui: ItemGui

    def renderInInventory(): Unit

    def renderInWorld( shader: Shader): Unit

    def getInventoryModel:Model
}
