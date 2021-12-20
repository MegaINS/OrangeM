package ru.megains.orangem.client.render

import ru.megains.mge.render.mesh.{Mesh, MeshMaker}
import ru.megains.mge.render.model.{Model, TModel}
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.common.block.{BlockAir, BlockCell}
import ru.megains.orangem.client.register.GameRegisterRender
import ru.megains.orangem.client.render.texture.GameTextureManager
import ru.megains.orangem.client.utils.Logger
import ru.megains.orangem.common.world.World
import ru.megains.orangem.common.world.chunk.{Chunk, ChunkVoid}


class ChunkRenderer(posX: Int, posY: Int, posZ: Int, world: World) extends TModel with Logger[ChunkRenderer] {


    lazy val chunk: Chunk = world.getChunk(posX, posY, posZ)
    var isReRender = true
    var mesh: Array[Mesh] = new Array[Mesh](2)
    var blocks: Array[Int] = new Array[Int](2)
    var notEmpty = false
    xPos = posX * Chunk.blockSize
    yPos = posY * Chunk.blockSize
    zPos = posZ * Chunk.blockSize

    def isVoid: Boolean = chunk == ChunkVoid

    def makeChunk(layer: Int): Unit = {
        blocks(layer) = 0


        if (!chunk.isEmpty) {
            val mm: MeshMaker = MeshMaker.startMakeTriangles()

            mm.setTexture(GameTextureManager.blocksTexture)
            for (x <- 0 until Chunk.blockSize;
                 y <- 0 until Chunk.blockSize;
                 z <- 0 until Chunk.blockSize) {


                val blockState = chunk.getBlockByLocPos(x, y, z)
                if (layer == blockState.block.blockType.id) {
                    blockState.block match {
                        case BlockAir =>
                        case cell: BlockCell =>
                            cell.getChildrenBlocksState.foreach(
                                b => {
                                    GameRegisterRender.getBlockRender(b.block).render(mm, b, chunk.world, x, y, z)
                                    blocks(layer) += 1
                                }
                            )

                        case block =>
                            GameRegisterRender.getBlockRender(block).render(mm, blockState, chunk.world, x, y, z)
                            blocks(layer) += 1
                    }
                }
            }
            if (blocks(layer) != 0) {
                ChunkRenderer.chunkUpdate += 1
                mesh(layer) = mm.make()
                notEmpty = true
            } else {
                if (blocks.sum <= 0) notEmpty = false
            }
        }
    }


    def reRender(): Unit = {
        isReRender = true
    }

    def render(layer: Int, shader: Shader): Unit = {
        if (ChunkRenderer.isNext) {
            if (isReRender) {
                if (!chunk.isEmpty) {

                    cleanUp()
                    makeChunk(0)
                    makeChunk(1)

                    isReRender = false
                }
            }

        }

        if (notEmpty) {
            if (mesh(layer) != null) {
                shader.setUniform("modelMatrix", buildViewMatrix())
                mesh(layer).render()
                if (layer == 0) ChunkRenderer.chunkRender += 1
                ChunkRenderer.blockRender += blocks(layer)
            }
        }


    }

    def cleanUp(): Unit = {
        for (i <- mesh.indices) {
            if (mesh(i) != null) {
                mesh(i).cleanUp()
                mesh(i) = null
            }

        }
    }


}

object ChunkRenderer {

    var game:OrangeM = _


    def isNext: Boolean = {
        if (isRender && (System.currentTimeMillis() - startTime) < (1000f / (game.gameSettings.FPS*2f))) {
            true
        } else {
            isRender = false
            isRender
        }
    }

    private var isRender = true
    var startTime: Long = 0
    var blockRender: Long = 0
    var chunkUpdate = 0
    var chunkRender = 0

    var blockRenderLast: Long = 0
    var chunkUpdateLast = 0
    var chunkRenderLast = 0

    def reset(): Unit = {
        blockRenderLast = blockRender
        chunkUpdateLast = chunkUpdate
        chunkRenderLast = chunkRender
        blockRender = 0
        chunkUpdate = 0
        chunkRender = 0
    }

    def resetRenderTime(): Unit = {
        isRender = true
        startTime = System.currentTimeMillis()
    }
}
