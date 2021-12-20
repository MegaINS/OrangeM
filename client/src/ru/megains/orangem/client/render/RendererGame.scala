package ru.megains.orangem.client.render

import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import imgui.ImGui
import imgui.flag.ImGuiConfigFlags
import org.joml.{Matrix4f, Vector3f}
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW._
import ru.megains.mge.Window
import ru.megains.mge.render.camera.PerspectiveCamera
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.client.render.shader.{ShaderManager, WorldShader}
import org.lwjgl.opengl.GL11._
import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.texture.Texture
import ru.megains.orangem.client.render.block.RenderBlock
import ru.megains.orangem.client.render.shader.WorldShader
import ru.megains.orangem.client.scene.SceneGame
import ru.megains.orangem.client.utils.{Frustum, Logger}

import java.awt.Color
import java.nio.FloatBuffer

class RendererGame(gameScene: SceneGame) extends Logger[RendererGame] {


    val Z_NEAR: Float = 0.01f
    val Z_FAR: Float = 2000f
    val FOV: Float = Math.toRadians(45.0f).toFloat

    var worldShader: Shader = ShaderManager.worldShader
    var worldCamera: PerspectiveCamera = new PerspectiveCamera(FOV, Window.width, Window.height, Z_NEAR, Z_FAR)
    var worldRenderer: WorldRenderer = new WorldRenderer(gameScene)
    var skyBoxRenderer = new SkyBoxRenderer()
    var rayTraceRender: RayTraceRender = new RayTraceRender(gameScene)
    var blockPlaceSetRender: BlockSetPositionRender = new BlockSetPositionRender()
    val chunkBoundsRenderer:ChunkBoundsRenderer = new ChunkBoundsRenderer(gameScene)



    def init(): Unit = {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_DEPTH_TEST)
        glLineWidth( 	0.5f)
        rayTraceRender.init()
        skyBoxRenderer.init()
        chunkBoundsRenderer.init()
    }


    def update(): Unit = {
        rayTraceRender.update(gameScene.rayTrace)
        blockPlaceSetRender.update(gameScene.blockSetPosition)
    }

    def render(): Unit = {
        skyBoxRenderer.render(worldCamera)
        renderWorld()

    }
    val _proj: FloatBuffer = BufferUtils.createFloatBuffer(16)
    val _modl: FloatBuffer = BufferUtils.createFloatBuffer(16)

    def renderWorld(): Unit = {

        worldCamera.setPerspective(Math.toRadians(gameScene.settings.FOV).toFloat, Window.width, Window.height, Z_NEAR, Z_FAR)
        worldCamera.setPos(gameScene.player.posX, gameScene.player.posY + gameScene.player.levelView, gameScene.player.posZ)
        worldCamera.setRot(gameScene.player.rotPitch, gameScene.player.rotYaw, 0)


        val projectionMatrix: Matrix4f =  worldCamera.buildProjectionMatrix()
        val viewMatrix: Matrix4f = worldCamera.buildViewMatrix()

        projectionMatrix.get(_proj.clear().asInstanceOf[FloatBuffer])
        viewMatrix.get(_modl.clear().asInstanceOf[FloatBuffer])

        Frustum.calculateFrustum(_proj, _modl)

        ShaderManager.bindShader(worldShader)
        worldShader.setUniform(worldCamera)

        worldRenderer.render(gameScene.player, worldShader)

        glDisable(GL_DEPTH_TEST)
        rayTraceRender.render(worldShader)
        blockPlaceSetRender.render(worldShader)
        glEnable(GL_DEPTH_TEST)
        chunkBoundsRenderer.render(worldShader)

        ShaderManager.unbindShader()
    }




    def destroy(): Unit ={


        worldRenderer.cleanUp()
        skyBoxRenderer.cleanUp()
        rayTraceRender.cleanUp()
        blockPlaceSetRender.cleanUp()
        chunkBoundsRenderer.cleanUp()
    }


}


