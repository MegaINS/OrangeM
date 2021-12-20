package ru.megains.mge
package render.texture

import java.nio.ByteBuffer

import org.lwjgl.stb.STBImage._
import org.lwjgl.system.MemoryStack.stackPush

class TextureData() {

    var components:Int  = 0
    var width: Int = _
    var height: Int = _

    var image:ByteBuffer = _

    def this(name:String,imageBuffer: ByteBuffer) ={
        this()
        try {
            val stack = stackPush
            try {
                val w = stack.mallocInt(1)
                val h = stack.mallocInt(1)
                val comp = stack.mallocInt(1)
                // Use info to read image metadata without decoding the entire image.
                // We don't need this for this demo, just testing the API.
                if (!stbi_info_from_memory(imageBuffer, w, h, comp)) {
                    throw new RuntimeException("Failed to read image information: " + stbi_failure_reason)
                }
                else System.out.println("OK with reason: " + stbi_failure_reason)
                System.out.println("Image imagePath: " + name)
                System.out.println("Image width: " + w.get(0))
                System.out.println("Image height: " + h.get(0))
                System.out.println("Image components: " + comp.get(0))
                // System.out.println("Image HDR: " + stbi_is_hdr_from_memory(imageBuffer))
                // Decode the image
                image = stbi_load_from_memory(imageBuffer, w, h, comp, 0)
                if (image == null) throw new RuntimeException("Failed to load image: " + stbi_failure_reason)
                width = w.get(0)
                height = h.get(0)
                components = comp.get(0)

            } finally if (stack != null) stack.close()
        } catch {
            case e: Exception =>
                println(e.fillInStackTrace())
        }



    }







}
