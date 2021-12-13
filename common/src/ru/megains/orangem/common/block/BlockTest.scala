package ru.megains.orangem.common.block

import ru.megains.orangem.common.physics.{BlockSize, BoundingBox}


class BlockTest(name:String,size:Int) extends MiniBlock(name) {



   override val blockSize:BlockSize = new BlockSize(size,size,size)
   override val boundingBox: BoundingBox = new BoundingBox(0.0625f*size)
   override val physicalBody: BoundingBox = new BoundingBox(0,0,0,0.0625f*size,0.0625f*size,0.0625f*size)


}
