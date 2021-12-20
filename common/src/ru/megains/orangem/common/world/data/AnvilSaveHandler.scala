package ru.megains.orangem.common.world.data

import ru.megains.orangem.common.utils.Logger
import ru.megains.orangem.common.entity.player.EntityPlayer
import ru.megains.orangem.common.world.chunk.data.ChunkLoader
import ru.megains.orangem.nbt.NBTData
import ru.megains.orangem.nbt.tag.NBTCompound

import scala.reflect.io.{Directory, Path}

class AnvilSaveHandler(savesDirectory: Directory, worldName: String)  extends ISaveHandler with Logger[AnvilSaveHandler] {


    def flush(): Unit = {}


    val worldDirectory: Directory = savesDirectory / Path(worldName).toDirectory
    val playersDirectory: Directory = worldDirectory / Path("playerData").toDirectory
    val chunkLoader: ChunkLoader = new ChunkLoader(worldDirectory)

    def getChunkLoader: ChunkLoader = {
        chunkLoader
    }


    def readPlayerData(player: EntityPlayer): NBTCompound = {
        var compound: NBTCompound = null
        try {
            compound = NBTData.readOfFile(playersDirectory, player.name).getCompound
        } catch {
            case _: Exception =>
                log.warn("Failed to load player data for {}", Array[AnyRef](player.name))
        }
        if (compound != null && compound.nbtMap.nonEmpty ) {
           player.readFromNBT(compound)
        }
       compound
    }

    def writePlayerData(playerIn: EntityPlayer): Unit = {
        val compound: NBTCompound = NBTData.createCompound()
        playerIn.writeToNBT(compound)
        NBTData.writeToFile(compound, playersDirectory, playerIn.name)

    }


}
