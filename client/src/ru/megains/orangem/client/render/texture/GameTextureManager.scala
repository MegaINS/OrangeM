package ru.megains.orangem.client.render.texture

import org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load
import ru.megains.mge.render.texture.TextureManager
import ru.megains.orangem.client.register.GameRegisterRender

class GameTextureManager extends TextureManager {
    def init(): Unit = {
        stbi_set_flip_vertically_on_load(true)
        GameTextureManager.blocksTexture.registerTexture( GameRegisterRender.blockData.idRender.values.toList)
        GameTextureManager.blocksTexture.registerTexture( GameRegisterRender.itemData.idRender.values.toList)
        GameTextureManager.blocksTexture.loadTextureAtlas()
        loadTexture(GameTextureManager.blocksTexture.name, GameTextureManager.blocksTexture)
        GameTextureManager.entityTexture.registerTexture( GameRegisterRender.entityData.idRender.values.toList)
        GameTextureManager.entityTexture.loadTextureAtlas()

        loadTexture( GameTextureManager.entityTexture.name,  GameTextureManager.entityTexture)
    }
}

object GameTextureManager {
    val blocksTexture: TextureMap = new TextureMap("texture/blocks.png")
    val entityTexture: TextureMap = new TextureMap("texture/entity.png")

}
