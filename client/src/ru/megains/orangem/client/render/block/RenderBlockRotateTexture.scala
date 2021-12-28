package ru.megains.orangem.client.render.block

import ru.megains.mge.render.texture.TTexture
import ru.megains.orangem.common.block.data.BlockState
import ru.megains.orangem.client.render.api.TTextureRegister
import ru.megains.orangem.common.utils.Direction
import ru.megains.orangem.common.world.World

class RenderBlockRotateTexture(name:String) extends RenderBlockStandard(name) {


    var aTexture0: TTexture = _
    var aTexture1: TTexture = _
    var aTexture2: TTexture = _
    var aTexture3: TTexture = _
    var aTexture4: TTexture = _
    var aTexture5: TTexture = _

    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        aTexture0 = textureRegister.registerTexture("0")
        aTexture1 = textureRegister.registerTexture("1")
        aTexture2 = textureRegister.registerTexture("2")
        aTexture3 = textureRegister.registerTexture("3")
        aTexture4 = textureRegister.registerTexture("4")
        aTexture5 = textureRegister.registerTexture("5")
    }



    override def getATexture(blockState: BlockState, direction: Direction, world: World): TTexture = {
        direction match {
            case Direction.UP => aTexture0
            case Direction.DOWN => aTexture5
            case Direction.WEST =>
                blockState.direction match {
                    case Direction.WEST => aTexture2
                    case Direction.NORTH => aTexture3
                    case Direction.SOUTH => aTexture4
                    case _=> aTexture1
                }
            case Direction.NORTH =>
                blockState.direction match {
                    case Direction.WEST => aTexture4
                    case Direction.NORTH => aTexture2
                    case Direction.SOUTH => aTexture1
                    case _ => aTexture3
                }
            case Direction.SOUTH =>
                blockState.direction match {
                    case Direction.WEST => aTexture3
                    case Direction.NORTH => aTexture1
                    case Direction.SOUTH => aTexture2
                    case _ => aTexture4
                }
            case _ =>
                blockState.direction match {
                    case Direction.WEST => aTexture1
                    case Direction.NORTH => aTexture4
                    case Direction.SOUTH => aTexture3
                    case _ => aTexture2
                }
        }
    }




//    override def render(blockState: BlockState, world: World, posWorld: ChunkPosition): Boolean = {
//
//    var isRender = false
//
//    val pos = blockState.pos
//    val aabb:AABB = blockState.getBlockBody.hashSet.last.div(16).sum(pos.rendX, pos.rendY, pos.rendZ)
//    val minX = aabb.minX
//    val minY = aabb.minY
//    val minZ = aabb.minZ
//    val maxX = aabb.maxX
//    val maxY = aabb.maxY
//    val maxZ = aabb.maxZ
//
//
//    if (!world.isOpaqueCube(pos,Direction.SOUTH)) {
//    RenderBlock.renderSideSouth(minX, maxX, minY, maxY, maxZ, getATexture(blockState,Direction.SOUTH,world))
//    isRender = true
//}
//
//    if (!world.isOpaqueCube(pos,Direction.NORTH)) {
//    RenderBlock.renderSideNorth(minX, maxX, minY, maxY, minZ, getATexture(blockState,Direction.NORTH,world))
//    isRender = true
//}
//
//    if (!world.isOpaqueCube(pos,Direction.DOWN)) {
//    RenderBlock.renderSideDown(minX, maxX, minY, minZ, maxZ, getATexture(blockState,Direction.DOWN,world))
//    isRender = true
//}
//
//
//    if (!world.isOpaqueCube(pos,Direction.UP)) {
//    RenderBlock.renderSideUp(minX, maxX, maxY, minZ, maxZ, getATexture(blockState,Direction.UP,world))
//    isRender = true
//}
//
//    if (!world.isOpaqueCube(pos,Direction.WEST)) {
//    RenderBlock.renderSideWest(minX, minY, maxY, minZ, maxZ, getATexture(blockState,Direction.WEST,world))
//    isRender = true
//}
//
//    if (!world.isOpaqueCube(pos,Direction.EAST)) {
//    RenderBlock.renderSideEast(maxX, minY, maxY, minZ, maxZ, getATexture(blockState,Direction.EAST,world))
//    isRender = true
//}
//    isRender
//}
  override def getATexture: TTexture = aTexture0
}

