package ru.megains.mge
package render.shader

import java.nio.FloatBuffer

import org.lwjgl.BufferUtils

class UniformData(val uniformLocation: Int){

     val floatBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)

}
