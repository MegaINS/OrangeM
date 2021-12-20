package ru.megains.orangem.client.render.shader

import ru.megains.mge.render.camera.Camera
import ru.megains.mge.render.shader.Shader

private class GuiShader extends Shader {

    override val dir: String = "shaders/2d"

    override def createUniforms(): Unit = {
        createUniform("projectionMatrix")
        createUniform("modelMatrix")
    }

    override def setUniform(camera: Camera): Unit = {
        setUniform("projectionMatrix", camera.buildProjectionMatrix())
    }

}