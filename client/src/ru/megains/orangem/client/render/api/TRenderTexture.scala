package ru.megains.orangem.client.render.api

import ru.megains.mge.render.texture.TTexture

trait TRenderTexture {


    def registerTexture(textureRegister: TTextureRegister): Unit


}
