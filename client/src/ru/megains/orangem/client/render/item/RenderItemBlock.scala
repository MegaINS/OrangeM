package ru.megains.orangem.client.render.item

import ru.megains.mge.render.mesh.{Mesh, MeshMaker}
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.shader.Shader
import ru.megains.mge.render.texture.TTexture
import ru.megains.orangem.common.block.data.BlockState
import ru.megains.orangem.common.block.{Block, BlockAir}
import ru.megains.orangem.client.register.GameRegisterRender
import ru.megains.orangem.client.render.api.TTextureRegister
import ru.megains.orangem.client.render.texture.GameTextureManager
import ru.megains.orangem.common.item.ItemBlock

class RenderItemBlock(item: ItemBlock) extends TRenderItem {

    lazy val mesh: Mesh = createMesh()

    val block: Block = item.block

    override def registerTexture(textureRegister: TTextureRegister): Unit = {}

    override def renderInWorld( shader: Shader): Unit = mesh.render()

    override def renderInInventory(): Unit = mesh.render()

    override def getInventoryModel: Model = {
        new Model(mesh) {
            yRot = 45
            xRot = 20
            posX = 20
            posY = 20
        }
    }

    def createMesh(): Mesh = {

        val mm = MeshMaker.startMakeTriangles()

        mm.setTexture(GameTextureManager.blocksTexture)
        val aabbs = block match {
            case BlockAir => null
            case _ =>
                block.getBoundingBox(new BlockState(block, 0, 0, 0)) //new BlockState(block,0,0,0)//.getBlockBody.div(16)
        }

        //todo
        for (aabb <- aabbs) {

            val maxX: Float = (aabb.maxX - aabb.minX) / 2 * 25
            val maxY: Float = (aabb.maxY - aabb.minY) / 2 * 25
            val maxZ: Float = (aabb.maxZ - aabb.minZ) / 2 * 25
            val minX: Float = -(aabb.maxX - aabb.minX) / 2 * 25
            val minY: Float = -(aabb.maxY - aabb.minY) / 2 * 25
            val minZ: Float = -(aabb.maxZ - aabb.minZ) / 2 * 25


            val averageX = (minX + maxX) / 2
            val averageY = (minY + maxY) / 2
            val averageZ = (minZ + maxZ) / 2


            var minU: Float = 0
            var maxU: Float = 0
            var minV: Float = 0
            var maxV: Float = 0
            var averageU: Float = 0
            var averageV: Float = 0
            var texture: TTexture = null

            val blockRender = GameRegisterRender.getBlockRender(block)

            texture = blockRender.getATexture
              //texture = blockRender.getATexture(Direction.UP)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV

            mm.setCurrentIndex()
            mm.addVertexWithUV(minX, maxY, minZ, maxU, maxV)
            mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
            mm.addVertexWithUV(maxX, maxY, minZ, minU, maxV)
            mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
            mm.addVertexWithUV(averageX, maxY, averageZ, averageU, averageV)
            mm.addIndex(0, 1, 4)
            mm.addIndex(3, 2, 4)
            mm.addIndex(1, 3, 4)
            mm.addIndex(2, 0, 4)



            //V2


            // texture = blockRender.getATexture(blockDirection =BlockDirection.DOWN)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV


            mm.setCurrentIndex()
            mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
            mm.addVertexWithUV(minX, minY, maxZ, minU, minV)
            mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
            mm.addVertexWithUV(maxX, minY, maxZ, maxU, minV)
            mm.addVertexWithUV(averageX, minY, averageZ, averageU, averageV)
            mm.addIndex(1, 0, 4)
            mm.addIndex(2, 3, 4)
            mm.addIndex(3, 1, 4)
            mm.addIndex(0, 2, 4)




            // texture = blockRender.getATexture(blockDirection =BlockDirection.NORTH)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV


            mm.setCurrentIndex()

            mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
            mm.addVertexWithUV(minX, minY, maxZ, maxU, maxV)
            mm.addVertexWithUV(minX, maxY, minZ, minU, minV)
            mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
            mm.addVertexWithUV(minX, averageY, averageZ, averageU, averageV)
            mm.addIndex(0, 1, 4)
            mm.addIndex(1, 3, 4)
            mm.addIndex(3, 2, 4)
            mm.addIndex(2, 0, 4)



            // texture = blockRender.getATexture(blockDirection =BlockDirection.SOUTH)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV


            mm.setCurrentIndex()
            mm.addColor(0.5f, 0.5f, 0.5f)

            mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
            mm.addVertexWithUV(maxX, minY, maxZ, minU, maxV)
            mm.addVertexWithUV(maxX, maxY, minZ, maxU, minV)
            mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
            mm.addVertexWithUV(maxX, averageY, averageZ, averageU, averageV)
            mm.addIndex(1, 0, 4)
            mm.addIndex(3, 1, 4)
            mm.addIndex(2, 3, 4)
            mm.addIndex(0, 2, 4)




            // texture = blockRender.getATexture(blockDirection =BlockDirection.WEST)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV


            mm.setCurrentIndex()
            mm.addColor(0.7f, 0.7f, 0.7f)
            mm.addVertexWithUV(minX, minY, maxZ, minU, maxV)
            mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)
            mm.addVertexWithUV(maxX, minY, maxZ, maxU, maxV)
            mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)
            mm.addVertexWithUV(averageX, averageY, maxZ, averageU, averageV)
            mm.addIndex(1, 0, 4)
            mm.addIndex(4, 0, 2)
            mm.addIndex(4, 2, 3)
            mm.addIndex(3, 1, 4)





            // texture = blockRender.getATexture(blockDirection =BlockDirection.EAST)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV


            mm.setCurrentIndex()
            mm.addColor(1f, 1f, 1f)
            mm.addVertexWithUV(minX, minY, minZ, maxU, maxV)
            mm.addVertexWithUV(minX, maxY, minZ, maxU, minV)
            mm.addVertexWithUV(maxX, minY, minZ, minU, maxV)
            mm.addVertexWithUV(maxX, maxY, minZ, minU, minV)
            mm.addVertexWithUV(averageX, averageY, minZ, averageU, averageV)
            mm.addIndex(0, 1, 4)
            mm.addIndex(0, 4, 2)
            mm.addIndex(2, 4, 3)
            mm.addIndex(1, 3, 4)


        }

        mm.make()
    }

    // override def getItemGui: ItemGui = new ItemBlockGui(item)
}
