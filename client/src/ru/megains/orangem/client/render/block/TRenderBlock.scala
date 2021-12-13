package ru.megains.orangem.client.render.block

import ru.megains.mge.render.mesh.MeshMaker
import ru.megains.mge.render.texture.TTexture
import ru.megains.orangem.common.block.data.BlockState
import ru.megains.orangem.client.render.TTextureRegister
import ru.megains.orangem.common.utils.Direction
import ru.megains.orangem.common.world.World

trait TRenderBlock {

    def getATexture: TTexture

    def registerTexture(textureRegister: TTextureRegister): Unit

    def render(mm: MeshMaker, blockState: BlockState, world: World, xRIn: Float, yRIn: Float, zRIn: Float): Unit

    def getATexture(blockState: BlockState, direction: Direction, world: World): TTexture
}
