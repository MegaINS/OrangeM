package ru.megains.orangem.client.render.shader

import org.joml.Vector3f
import ru.megains.mge.render.camera.Camera
import ru.megains.mge.render.shader.Shader

class SkyBoxShader extends Shader{


    override val dir: String = "shaders/skybox"


    override def createUniforms(): Unit = {
        createUniform("projectionMatrix")
        createUniform("viewMatrix" )
        createUniform("modelMatrix")
        createUniform("ambientLight")
       // createUniform("dataTC")
    }

    override def setUniform(camera: Camera): Unit = {
        setUniform("projectionMatrix", camera.buildProjectionMatrix())
        val viewMatrix = camera.buildViewMatrix()
        viewMatrix.m30(0)
        viewMatrix.m31(0)
        viewMatrix.m32(0)

        setUniform("viewMatrix",viewMatrix)

    }
}
