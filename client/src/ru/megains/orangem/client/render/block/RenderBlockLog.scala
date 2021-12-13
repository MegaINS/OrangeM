package ru.megains.orangem.client.render.block

import ru.megains.mge.render.texture.TTexture
import ru.megains.orangem.common.block.data.BlockState
import ru.megains.orangem.client.render.TTextureRegister
import ru.megains.orangem.common.utils.Direction
import ru.megains.orangem.common.world.World

class RenderBlockLog(name: String) extends RenderBlockStandard (name){

    var aTextureTop: TTexture = _



    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        texture = textureRegister.registerTexture(name )
        aTextureTop = textureRegister.registerTexture(name + "_top")

    }

    override def getATexture(blockState: BlockState,direction: Direction,world: World): TTexture = {
        direction match {
            case Direction.UP |Direction.DOWN  => aTextureTop
            case _ => texture
        }

    }

}
