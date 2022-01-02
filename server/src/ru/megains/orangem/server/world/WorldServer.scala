package ru.megains.orangem.server.world

import ru.megains.orangem.common.block.data.BlockPos
import ru.megains.orangem.common.world.World
import ru.megains.orangem.common.world.data.AnvilSaveHandler
import ru.megains.orangem.server.PlayerChunkMap

class WorldServer(saveHandler:AnvilSaveHandler) extends World(saveHandler){

    val spawnPoint: BlockPos = new BlockPos(0, 20, 0)

    val playerManager: PlayerChunkMap = new PlayerChunkMap(this)
}
