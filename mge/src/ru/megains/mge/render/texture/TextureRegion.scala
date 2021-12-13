package ru.megains.mge.render.texture

class TextureRegion(texture: Texture,x:Int,y:Int,width:Int, height:Int) extends TTexture(texture.textureData) {


    minU = x.toFloat/texture.width.toFloat
    maxU = (x+width).toFloat/texture.width.toFloat
    minV = y.toFloat/texture.width.toFloat
    maxV = (y+height).toFloat/texture.width.toFloat


    override def getGlTextureId: Int = texture.getGlTextureId

    override def deleteGlTexture(): Unit = texture.deleteGlTexture()
}
