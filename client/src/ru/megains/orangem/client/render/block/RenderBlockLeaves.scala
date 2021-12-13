package ru.megains.orangem.client.render.block

import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.orangem.common.block.BlockGlass
import ru.megains.orangem.common.block.data.BlockState
import ru.megains.orangem.common.utils.Direction
import ru.megains.orangem.common.world.World

import java.awt.Color

class RenderBlockLeaves(name:String) extends RenderBlockStandard(name) {


    override def render(mm: MeshMaker, blockState: BlockState, world: World, xRIn: Float, yRIn: Float, zRIn: Float): Unit = {

        //todo head.
        val box = blockState.getBoundingBox.head.sum(xRIn, yRIn, zRIn)
        val color: Color = new Color(0,128,0)

        

        RenderBlock.renderSideWest(mm, box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, getATexture(blockState, Direction.WEST, world),color)
        RenderBlock.renderSideWest(mm, box.minX, box.minY, box.maxY, box.minZ, box.maxZ, getATexture(blockState, Direction.WEST, world),color)



        RenderBlock.renderSideEast(mm, box.minX, box.minY, box.maxY, box.minZ, box.maxZ, getATexture(blockState, Direction.EAST, world),color)
        RenderBlock.renderSideEast(mm, box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, getATexture(blockState, Direction.EAST, world),color)


        RenderBlock.renderSideNorth(mm, box.minX, box.maxX, box.minY, box.maxY, box.maxZ, getATexture(blockState, Direction.NORTH, world),color)
        RenderBlock.renderSideNorth(mm, box.minX, box.maxX, box.minY, box.maxY, box.minZ, getATexture(blockState, Direction.NORTH, world),color)


        RenderBlock.renderSideSouth(mm, box.minX, box.maxX, box.minY, box.maxY, box.minZ, getATexture(blockState, Direction.SOUTH, world),color)
        RenderBlock.renderSideSouth(mm, box.minX, box.maxX, box.minY, box.maxY, box.maxZ, getATexture(blockState, Direction.SOUTH, world),color)


        RenderBlock.renderSideDown(mm, box.minX, box.maxX, box.maxY, box.minZ, box.maxZ, getATexture(blockState, Direction.DOWN, world),color)
        RenderBlock.renderSideDown(mm, box.minX, box.maxX, box.minY, box.minZ, box.maxZ, getATexture(blockState, Direction.DOWN, world),color)


        RenderBlock.renderSideUp(mm, box.minX, box.maxX, box.minY, box.minZ, box.maxZ, getATexture(blockState, Direction.UP, world),color)
        RenderBlock.renderSideUp(mm, box.minX, box.maxX, box.maxY, box.minZ, box.maxZ, getATexture(blockState, Direction.UP, world),color)

    }
}
