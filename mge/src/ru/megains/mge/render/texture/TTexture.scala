package ru.megains.mge.render.texture

import org.lwjgl.opengl.GL11


abstract class TTexture(data:TextureData) {

    protected var glTextureId: Int = -1

    var minV: Float = 0
    var maxV: Float = 1
    var minU: Float = 0
    var maxU: Float = 1
    var averageV: Float = 0.5f
    var averageU: Float = 0.5f
    var width: Int = data.width
    var height: Int = data.height
    var components: Int = data.components

    def getGlTextureId: Int = {
        if (glTextureId == -1) {
            glTextureId = GL11.glGenTextures
        }
        glTextureId
    }

    def deleteGlTexture(): Unit = {
        if (glTextureId != -1) {
            GL11.glDeleteTextures(glTextureId)
            glTextureId = -1
        }
    }


}
