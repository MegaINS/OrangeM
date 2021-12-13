package ru.megains.mge
package render.texture

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL30.glGenerateMipmap
import org.lwjgl.opengl.GL11.glPixelStorei
import org.lwjgl.stb.STBImage.stbi_image_free
import org.lwjgl.stb.STBImageResize._
import org.lwjgl.system.MemoryUtil.{memAlloc, memFree}
import ru.megains.mge.File


class TextureAtlas(data: TextureData) extends TTexture(data) {

    var startX: Int = 0
    var startY: Int = 0

    def this(filePath: String)={
        this(new TextureData(File.ioResourceToByteBuffer(filePath, 8 * 1024)))
    }



    def isMissingTexture: Boolean = data.image == null



    def updateTexture(widthAll: Float, heightAll: Float): Unit = {
        minU = startX / widthAll
        maxU = (startX + width) / widthAll
        minV = startY / heightAll
        maxV = (startY + height) / heightAll
        averageU = (minU + maxU) / 2
        averageV = (minV + maxV) / 2

        var format = 0
        if ( data.components == 3) {
            if ((width & 3) != 0) glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width & 1))
            format = GL_RGB
        }
        else {
            //premultiplyAlpha()
            glEnable(GL_BLEND)
            glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA)
            format = GL_RGBA
        }
        glTexSubImage2D(GL_TEXTURE_2D, 0, startX, startY, width, height, format, GL_UNSIGNED_BYTE, data.image)
        var input_pixels = data.image
        var input_w = width
        var input_h = height
        var mipmapLevel = 0
        while (1 < input_w || 1 < input_h) {
            val output_w = Math.max(1, input_w >> 1)
            val output_h = Math.max(1, input_h >> 1)
            val output_pixels = memAlloc(output_w * output_h * data.components)
            stbir_resize_uint8_generic(input_pixels, input_w, input_h, input_w * data.components, output_pixels, output_w, output_h, output_w * data.components, data.components, if (data.components == 4) 3
            else STBIR_ALPHA_CHANNEL_NONE, STBIR_FLAG_ALPHA_PREMULTIPLIED, STBIR_EDGE_CLAMP, STBIR_FILTER_MITCHELL, STBIR_COLORSPACE_SRGB)
            if (mipmapLevel == 0) stbi_image_free(data.image)
            else memFree(input_pixels)
            mipmapLevel += 1;
            glTexSubImage2D(GL_TEXTURE_2D, mipmapLevel, startX, startY, width, height, format, GL_UNSIGNED_BYTE, output_pixels)

            input_pixels = output_pixels
            input_w = output_w
            input_h = output_h
        }
        if (mipmapLevel == 0) stbi_image_free(data.image)
        else memFree(input_pixels)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
//        glGenerateMipmap(GL_TEXTURE_2D)
    }


}
