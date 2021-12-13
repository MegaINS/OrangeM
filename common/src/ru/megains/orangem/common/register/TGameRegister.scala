package ru.megains.orangem.common.register

import ru.megains.orangem.common.block.Block
import ru.megains.orangem.common.entity.Entity
import ru.megains.orangem.common.item.Item

trait TGameRegister {

    def registerBlock(id: Int, block: Block): Boolean
    def registerItem(id: Int, item: Item): Boolean
  //  def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Boolean
    def registerEntity(id: Int, tileEntity: Class[_<:Entity]): Boolean
}
