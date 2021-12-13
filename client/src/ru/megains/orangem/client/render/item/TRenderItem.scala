package ru.megains.orangem.client.render.item

import ru.megains.mge.render.model.Model
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.client.render.TTextureRegister


trait  TRenderItem {

   // def getItemGui: ItemGui

    def renderInInventory(): Unit

    def renderInWorld( shader: Shader): Unit

    def registerTexture(textureRegister: TTextureRegister): Unit

    def getInventoryModel:Model
}
