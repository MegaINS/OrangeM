package ru.megains.orangem.client.render.entity

import java.awt.Color
import org.lwjgl.opengl.GL11._
import ru.megains.mge.render.mesh.{Mesh, MeshMaker}
import ru.megains.mge.render.model.{Model, TModel}
import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.common.entity.Entity
import ru.megains.orangem.common.physics.BoundingBox
import ru.megains.orangem.common.world.World



object RenderEntityCube extends TRenderEntity with TModel{


    var entityCube:Mesh = _

    override def init(): Unit = {
        val mm = MeshMaker.startMake(GL_LINES)
        val aabb:BoundingBox= new BoundingBox(-0.3f,0,-0.3f,0.3f, 1.8f, 0.3f)

        val minX = aabb.minX
        val minY = aabb.minY
        val minZ = aabb.minZ
        val maxX = aabb.maxX
        val maxY = aabb.maxY
        val maxZ = aabb.maxZ




        mm.addColor(Color.BLACK)
        mm.addVertex(minX, minY, minZ)
        mm.addVertex(minX, minY, maxZ)
        mm.addVertex(minX, maxY, minZ)
        mm.addVertex(minX, maxY, maxZ)
        mm.addVertex(maxX, minY, minZ)
        mm.addVertex(maxX, minY, maxZ)
        mm.addVertex(maxX, maxY, minZ)
        mm.addVertex(maxX, maxY, maxZ)

        mm.addIndex(0, 1)
        mm.addIndex(0, 2)
        mm.addIndex(0, 4)

        mm.addIndex(6, 2)
        mm.addIndex(6, 4)
        mm.addIndex(6, 7)

        mm.addIndex(3, 1)
        mm.addIndex(3, 2)
        mm.addIndex(3, 7)

        mm.addIndex(5, 1)
        mm.addIndex(5, 4)
        mm.addIndex(5, 7)


        entityCube = mm.make()
    }

    override def render(entity: Entity, world: World,shader: Shader): Boolean = {

        zPos = entity.posZ
        xPos = entity.posX
        yPos = entity.posY
        shader.setUniform("modelMatrix",  buildViewMatrix())
        entityCube.render()
        true
    }
}
