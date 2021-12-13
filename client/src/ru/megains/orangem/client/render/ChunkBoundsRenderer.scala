package ru.megains.orangem.client.render

import org.lwjgl.opengl.GL11._
import ru.megains.mge.render.mesh.{Mesh, MeshMaker}
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.client.scene.GameScene
import ru.megains.orangem.common.entity.player.EntityPlayer
import ru.megains.orangem.common.physics.BoundingBox
import ru.megains.orangem.common.world.World
import ru.megains.orangem.common.world.chunk.Chunk

import java.awt.Color

class ChunkBoundsRenderer(gameScene: GameScene) {



    var isActive = false
    val world: World = gameScene.world
    val player: EntityPlayer = gameScene.player
    var chunkMesh: Model = _

    def render(shader: Shader): Unit = {
        if(isActive){
            val posX: Int = (player.posX / Chunk.blockSize - (if (player.posX < 0) 1 else 0)).toInt
            val posY: Int = (player.posY / Chunk.blockSize - (if (player.posY < 0) 1 else 0)).toInt
            val posZ: Int = (player.posZ / Chunk.blockSize - (if (player.posZ < 0) 1 else 0)).toInt
            val chunk = world.getChunk(posX,posY, posZ)

            chunkMesh.posX = chunk.pos.blockPosX
            chunkMesh.posY = chunk.pos.blockPosY
            chunkMesh.posZ = chunk.pos.blockPosZ



            chunkMesh.render(shader)
        }
    }

    def init(): Unit = {
        val mm = MeshMaker.startMake(GL_LINES)

        val aabb = new BoundingBox(Chunk.blockSize)


        val minX:Int = aabb.minX.toInt
        val minY:Int = aabb.minY.toInt
        val minZ:Int = aabb.minZ.toInt
        val maxX :Int= aabb.maxX.toInt
        val maxY:Int = aabb.maxY.toInt
        val maxZ:Int = aabb.maxZ.toInt

        val greenColor = new Color(0, 200, 0)
        mm.setCurrentIndex()
        val blueColor = new Color(0, 0,200)

//        var a1 =0
//        var a2 =1
        for(x <- minX to  maxX){
            mm.setCurrentIndex()
            if(x == minX || x == maxX){
                mm.addColor(blueColor)
            }else{
                mm.addColor(greenColor)
            }

            mm.addVertex(x, minY, minZ)
            mm.addVertex(x, maxY, minZ)
            mm.addVertex(x, minY, maxZ)
            mm.addVertex(x, maxY, maxZ)


            mm.addIndex(0, 1)
            mm.addIndex(2, 3)
            mm.addIndex(0, 2)
            mm.addIndex(1, 3)
        }




        for(z <- minZ to  maxZ ){
            mm.setCurrentIndex()
            if(z == minZ || z == maxZ){
                mm.addColor(blueColor)
            }else{
                mm.addColor(greenColor)
            }
            mm.addVertex(minX, minY, z)
            mm.addVertex(minX, maxY, z)
            mm.addVertex(maxX, minY, z)
            mm.addVertex(maxX, maxY, z)



            mm.addIndex(0, 1)
            mm.addIndex(2, 3)
            mm.addIndex(0, 2)
            mm.addIndex(1, 3)
        }


        for(y <- minY to  maxY){
            mm.setCurrentIndex()
            if(y == minY || y == maxY){
                mm.addColor(blueColor)
            }else{
                mm.addColor(greenColor)

            }

            mm.addVertex(minX, y, minZ)
            mm.addVertex(minX, y, maxZ)
            mm.addVertex(maxX, y, minZ)
            mm.addVertex(maxX, y, maxZ)

            mm.addIndex(0, 1)
            mm.addIndex(2, 3)
            mm.addIndex(0, 2)
            mm.addIndex(1, 3)
        }

//        mm.addVertex(minX, minY, minZ)
//        mm.addVertex(minX, minY, maxZ)
//        mm.addVertex(minX, maxY, minZ)
//        mm.addVertex(minX, maxY, maxZ)
//        mm.addVertex(maxX, minY, minZ)
//        mm.addVertex(maxX, minY, maxZ)
//        mm.addVertex(maxX, maxY, minZ)
//        mm.addVertex(maxX, maxY, maxZ)

//        mm.addIndex(0, 1)
//        mm.addIndex(0, 2)
//        mm.addIndex(0, 4)
//
//        mm.addIndex(6, 2)
//        mm.addIndex(6, 4)
//        mm.addIndex(6, 7)
//
//        mm.addIndex(3, 1)
//        mm.addIndex(3, 2)
//        mm.addIndex(3, 7)
//
//        mm.addIndex(5, 1)
//        mm.addIndex(5, 4)
//        mm.addIndex(5, 7)
        chunkMesh = new Model(mm.make())
    }


}
