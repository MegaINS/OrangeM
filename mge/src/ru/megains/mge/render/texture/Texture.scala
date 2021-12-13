package ru.megains.mge.render.texture

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL30.glGenerateMipmap
import ru.megains.mge.File

class Texture private(val textureData: TextureData) extends TTexture(textureData) {


    def this(name: String) = {
        this(new TextureData(File.ioResourceToByteBuffer(name, 8 * 1024)))

        glBindTexture(GL_TEXTURE_2D, getGlTextureId)
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)


        //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        //        glGenerateMipmap(GL_TEXTURE_2D)
        //
        //       // glGenerateMipmap(GL_TEXTURE_2D)
        //        //glGenerateMipmap(GL_TEXTURE_2D)

        // glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        // Когда минимизируем — берем две ближних мипмапы и лиейно смешиваем цвета
        // glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        // И создаем сами мипмапы.
        // glGenerateMipmap(GL_TEXTURE_2D)
        var format = 0
        if (textureData.components == 3) {
            if ((width & 3) != 0) glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width & 1))
            format = GL_RGB
        }
        else {

            format = GL_RGBA
        }
        glTexImage2D(GL_TEXTURE_2D, 0, format, textureData.width, textureData.height, 0, format, GL_UNSIGNED_BYTE, textureData.image)

        //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_REPEAT)
        //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        glGenerateMipmap(GL_TEXTURE_2D)
    }


}
