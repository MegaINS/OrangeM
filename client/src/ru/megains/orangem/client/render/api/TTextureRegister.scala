package ru.megains.orangem.client.render.api

import ru.megains.mge.render.texture.TTexture


trait TTextureRegister {

    def registerTexture(textureName : String): TTexture

}
