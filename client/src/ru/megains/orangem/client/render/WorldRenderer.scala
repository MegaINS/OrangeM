package ru.megains.orangem.client.render

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL32.GL_PROGRAM_POINT_SIZE
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.common.block.data.BlockType
import ru.megains.orangem.client.register.GameRegisterRender
import ru.megains.orangem.common.entity.item.EntityItem
import ru.megains.orangem.common.entity.player.EntityPlayer
import ru.megains.orangem.common.register.GameRegister
import ru.megains.orangem.common.world.World
import ru.megains.orangem.common.world.chunk.Chunk
import ru.megains.orangem.client.render.texture.GameTextureManager.blocksTexture.map
import ru.megains.orangem.client.scene.SceneGame
import ru.megains.orangem.client.utils.Frustum

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.collection.parallel.CollectionConverters.ImmutableIterableIsParallelizable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


class WorldRenderer(gameScene: SceneGame) {

    val world: World = gameScene.world
    val chunkRenderer = new mutable.HashMap[Long, ChunkRenderer]()
    var playerRenderChunks: Seq[ChunkRenderer] = Seq[ChunkRenderer]()
    var lastX: Int = Int.MinValue
    var lastY: Int = Int.MinValue
    var lastZ: Int = Int.MinValue


    def render(entityPlayer: EntityPlayer, shader: Shader): Unit = {


        glEnable(GL_STENCIL_TEST)
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_CULL_FACE)


        ChunkRenderer.resetRenderTime()

        val chunks = getRenderChunks(entityPlayer)

        chunks.foreach(_.render(BlockType.SOLID.id, shader))
        chunks.foreach(_.render(BlockType.GLASS.id, shader))
        // glDisable(GL_DEPTH_TEST)
        // glDisable(GL_CULL_FACE)
        glLineWidth(2f)
        renderEntities(shader: Shader)
        glLineWidth(0.5f)
    }


    def getRenderChunks(entityPlayer: EntityPlayer): Seq[ChunkRenderer] = {
        //        // TODO:  OPTIMIZE
        val posX: Int = (entityPlayer.posX / Chunk.blockSize - (if (entityPlayer.posX < 0) 1 else 0)).toInt
        val posY: Int = (entityPlayer.posY / Chunk.blockSize - (if (entityPlayer.posY < 0) 1 else 0)).toInt
        val posZ: Int = (entityPlayer.posZ / Chunk.blockSize - (if (entityPlayer.posZ < 0) 1 else 0)).toInt
        val flag = playerRenderChunks.exists(_.isVoid)


//        if (posX != lastX ||
//                posY != lastY ||
//                posZ != lastZ ||
//                playerRenderChunks.isEmpty ||
//                flag) {
//            lastX = posX
//            lastY = posY
//            lastZ = posZ
//
//
//            val renderW = gameScene.settings.RENDER_DISTANCE_WIDTH
//            val renderH = gameScene.settings.RENDER_DISTANCE_HEIGHT
//            val R = renderW * renderW
//
//            val futures = (posX - renderW to posX + renderW).map(
//                x1 =>
//                    //  Future {
//                    (posY - renderH to posY + renderH).flatMap(y1 =>
//                        (posZ - renderW to posZ + renderW).map(
//                            (x1, y1, _)
//                        )
//                                .filter(a => ((a._1 - posX) * (a._1 - posX) + (a._3 - posZ) * (a._3 - posZ) <= R))
//                                .map(a => getRenderChunk(a._1, a._2, a._3))
//                    )
//                //}
//            )
//
//            playerRenderChunks = futures.flatten.sortBy((c) => Math.abs(posY - c.yPos) + (Math.abs(posX - c.xPos) + Math.abs(posZ - c.zPos)) / 10)
//        }

        Seq(getRenderChunk(0,0,0))
       // playerRenderChunks//.filter(chunk => Frustum.cubeInFrustum(chunk.xPos, chunk.yPos, chunk.zPos, chunk.xPos + Chunk.blockSize, chunk.yPos + Chunk.blockSize, chunk.zPos + Chunk.blockSize))
    }

    def getRenderChunk(x: Int, y: Int, z: Int): ChunkRenderer = {
        chunkRenderer.getOrElseUpdate(Chunk.getIndex(x, y, z), createChunkRen(x, y, z))
    }

    def createChunkRen(x: Int, y: Int, z: Int): ChunkRenderer = {
        new ChunkRenderer(x, y, z, world)
    }

    def reRender(xIn: Int, yIn: Int, zIn: Int): Unit = {
        //TODO
        val x: Int = xIn >> Chunk.offset
        val y: Int = yIn >> Chunk.offset
        val z: Int = zIn >> Chunk.offset
        getRenderChunk(x, y, z).reRender()
        getRenderChunk(x + 1, y, z).reRender()
        getRenderChunk(x - 1, y, z).reRender()
        getRenderChunk(x, y + 1, z).reRender()
        getRenderChunk(x, y - 1, z).reRender()
        getRenderChunk(x, y, z + 1).reRender()
        getRenderChunk(x, y, z - 1).reRender()
    }


    def renderEntitiesItem(shader: Shader): Unit = {


        world.entities.filter(_.isInstanceOf[EntityItem]) /*. splitter*/ .foreach(
            entity => {
                //  if (frustum.cubeInFrustum(entity.body)) {
                // val modelViewMatrix = transformation.buildEntityItemModelViewMatrix(entity.asInstanceOf[EntityItem])
                //Renderer.currentShaderProgram.setUniform("model", modelViewMatrix)
                GameRegisterRender.getItemRender(entity.asInstanceOf[EntityItem].itemStack.item).renderInWorld(shader)
                //  }
            }
        )
    }

    def renderEntities(shader: Shader): Unit = {


        world.entities.filter {
            case _: EntityItem | _: EntityPlayer => false
            case _ => true
        } /*.splitter*/ .foreach(
            entity => {
                // if (frustum.cubeInFrustum(entity.body)) {
                // val modelViewMatrix = transformation.buildEntityModelViewMatrix(entity)
                // Renderer.currentShaderProgram.setUniform("model", modelViewMatrix)
                GameRegisterRender.getEntityRender(entity).render(entity, world, shader)
            }
            // }
        )
    }

    def cleanUp(): Unit = {

        chunkRenderer.values.foreach(_.cleanUp())

    }
}
