package ru.megains.orangem.client.render.api

import ru.megains.mge.render.texture.TTexture


trait  TRenderItem  extends TTexture{

    def renderInInventory(): Unit

    def renderInWorld(): Unit

}
