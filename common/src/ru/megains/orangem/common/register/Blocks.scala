package ru.megains.orangem.common.register

import ru.megains.orangem.common.block.Block

object Blocks {

    lazy val air:Block = GameRegister.getBlockByName("air")
    lazy val dirt:Block = GameRegister.getBlockByName("dirt")
    lazy val stone:Block = GameRegister.getBlockByName("stone")
    lazy val test0:Block = GameRegister.getBlockByName("test0")
    lazy val test1:Block = GameRegister.getBlockByName("test1")
    lazy val test2:Block = GameRegister.getBlockByName("test2")
    lazy val glass:Block = GameRegister.getBlockByName("glass")
    lazy val grass:Block = GameRegister.getBlockByName("grass")
    lazy val log_oak:Block = GameRegister.getBlockByName("log_oak")
    lazy val leaves_oak:Block = GameRegister.getBlockByName("leaves_oak")

    def getBlockById(id: Int): Block = {
        GameRegister.getBlockById(id)
    }

    def getIdByBlock(block: Block): Int = {
        GameRegister.getIdByBlock(block)
    }

}
