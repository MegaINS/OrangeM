package ru.megains.orangem.common.block

import ru.megains.orangem.common.block.data.BlockType

class BlockGlass(name:String) extends Block(name) {


    override val blockType: BlockType.Value = BlockType.GLASS
    override def isOpaqueCube:Boolean = false
}
