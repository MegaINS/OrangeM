package ru.megains.orangem.common.world

import org.joml.{Vector3d, Vector3i}
import ru.megains.orangem.common.block.BlockAir
import ru.megains.orangem.common.block.data.BlockState
import ru.megains.orangem.common.entity.Entity
import ru.megains.orangem.common.physics.BoundingBox
import ru.megains.orangem.common.utils.{Direction, Logger, RayTraceResult}
import ru.megains.orangem.common.world.chunk.Chunk
import ru.megains.orangem.common.world.chunk.data.{ChunkLoader, ChunkProvider}
import ru.megains.orangem.common.world.data.AnvilSaveHandler

import scala.collection.mutable
import scala.util.Random

class World(val saveHandler: AnvilSaveHandler) extends Logger[World] {


    val height: Int = 10000
    val length: Int = 10000
    val width: Int = 10000
    val chunkMap = new mutable.HashMap[Long, Chunk]()
    val entities: mutable.HashSet[Entity] = new mutable.HashSet[Entity]()
    val chunkLoader: ChunkLoader = saveHandler.getChunkLoader
    val chunkProvider: IChunkProvider = new ChunkProvider(this ,chunkLoader)
    val heightMap: WorldHeightMap = new WorldHeightMap(Random.nextInt())

    def init(): Unit = {

    }

    def update(): Unit = {
        entities.foreach(_.update())
        // tickableTileEntities.foreach(_.update(this))
    }

    def addEntity(entityIn: Entity): Unit = {
        entities += entityIn
    }

    def removeEntity(entityIn: Entity): Unit = {
        entities -= entityIn
    }

    def spawnEntityInWorld(entity: Entity): Unit = {
        val chunk = getChunkBlockPos(entity.posX.toInt, entity.posY.toInt, entity.posZ.toInt)
        if (chunk != null) {
            chunk.addEntity(entity)
        }
    }

    def getChunkBlockPos(x: Int, y: Int, z: Int): Chunk = {
        if ((x <= length && x >= -length) && (z <= width && z >= -width) && (y <= height && y >= -height)) {
            chunkProvider.provideChunk(x >> Chunk.offset, y >> Chunk.offset, z >> Chunk.offset)
        } else {
            ChunkProvider.voidChunk
        }

    }

    def setBlock(blockState: BlockState): Unit = {


        blockState match {
            case state: BlockState =>
                val minX: Int = state.x + state.getSelectedBlockSize.minX / Chunk.blockSize >> Chunk.offset
                val minY: Int = state.y + state.getSelectedBlockSize.minY / Chunk.blockSize >> Chunk.offset
                val minZ: Int = state.z + state.getSelectedBlockSize.minZ / Chunk.blockSize >> Chunk.offset
                val maxX: Int = state.x + state.getSelectedBlockSize.maxX / Chunk.blockSize >> Chunk.offset
                val maxY: Int = state.y + state.getSelectedBlockSize.maxY / Chunk.blockSize >> Chunk.offset
                val maxZ: Int = state.z + state.getSelectedBlockSize.maxZ / Chunk.blockSize >> Chunk.offset

                for (chunkX <- minX to maxX;
                     chunkY <- minY to maxY;
                     chunkZ <- minZ to maxZ) {
                    getChunk(chunkX, chunkY, chunkZ).setBlock(blockState)
                }

            case _ =>
                getChunk(blockState.x >> Chunk.offset, blockState.y >> Chunk.offset, blockState.z >> Chunk.offset).setBlock(blockState)
        }


    }

    def removeBlock(blockState: BlockState): Unit = {
        blockState match {
            case state: BlockState =>
                val minX: Int = state.x + state.getSelectedBlockSize.minX / Chunk.blockSize >> Chunk.offset
                val minY: Int = state.y + state.getSelectedBlockSize.minY / Chunk.blockSize >> Chunk.offset
                val minZ: Int = state.z + state.getSelectedBlockSize.minZ / Chunk.blockSize >> Chunk.offset
                val maxX: Int = state.x + state.getSelectedBlockSize.maxX / Chunk.blockSize >> Chunk.offset
                val maxY: Int = state.y + state.getSelectedBlockSize.maxY / Chunk.blockSize >> Chunk.offset
                val maxZ: Int = state.z + state.getSelectedBlockSize.maxZ / Chunk.blockSize >> Chunk.offset

                for (chunkX <- minX to maxX;
                     chunkY <- minY to maxY;
                     chunkZ <- minZ to maxZ) {
                    getChunk(chunkX, chunkY, chunkZ).removeBlock(blockState)
                }

            case _ =>
                getChunk(blockState.x >> Chunk.offset, blockState.y >> Chunk.offset, blockState.z >> Chunk.offset).removeBlock(blockState)
        }
    }

    def getChunk(x: Int, y: Int, z: Int): Chunk = {
        if ((x <= length && x >= -length) && (z <= width && z >= -width) && (y <= height && y >= -height)) {
            chunkProvider.provideChunk(x, y, z)
        } else {
            ChunkProvider.voidChunk
        }

    }

    def getBlock(x: Int, y: Int, z: Int): BlockState = {
        getChunk(x >> Chunk.offset, y >> Chunk.offset, z >> Chunk.offset).getBlock(x, y, z)
    }

    def isAirBlock(blockState: BlockState): Boolean = {

        blockState match {
            case state: BlockState =>
                val minX: Int = state.x + state.getSelectedBlockSize.minX / Chunk.blockSize >> Chunk.offset
                val minY: Int = state.y + state.getSelectedBlockSize.minY / Chunk.blockSize >> Chunk.offset
                val minZ: Int = state.z + state.getSelectedBlockSize.minZ / Chunk.blockSize >> Chunk.offset
                val maxX: Int = state.x + state.getSelectedBlockSize.maxX / Chunk.blockSize >> Chunk.offset
                val maxY: Int = state.y + state.getSelectedBlockSize.maxY / Chunk.blockSize >> Chunk.offset
                val maxZ: Int = state.z + state.getSelectedBlockSize.maxZ / Chunk.blockSize >> Chunk.offset

                for (chunkX <- minX to maxX;
                     chunkY <- minY to maxY;
                     chunkZ <- minZ to maxZ) {
                    if (!getChunk(chunkX, chunkY, chunkZ).isAirBlock(blockState)) {
                        return false
                    }
                }
                true
            case _ =>
                getBlock(blockState.x, blockState.y, blockState.z).block == BlockAir
        }
    }


    def rayTraceBlocks(posIn: Vector3d, lockVec: Vector3d): RayTraceResult = {

        var result: RayTraceResult = RayTraceResult.VOID
        var direction: Direction = Direction.NONE
        val posStart: Vector3d = posIn
        val posEnd2: Vector3d = new Vector3d(posIn).add(lockVec)

        val posEnd: Vector3i = new Vector3i(Math.floor(posStart.x + lockVec.x).toInt, Math.floor(posStart.y + lockVec.y).toInt, Math.floor(posStart.z + lockVec.z).toInt)
        val posCheck: Vector3i = new Vector3i(Math.floor(posStart.x).toInt, Math.floor(posStart.y).toInt, Math.floor(posStart.z).toInt)

        var steps = 200

        while (posEnd != posCheck && result == RayTraceResult.VOID && steps != 0) {
            steps -= 1
            var dnX = 1.1D
            var dnY = 1.1D
            var dnZ = 1.1D

            if (lockVec.x > 0) {
                dnX = posCheck.x - posStart.x + 1
            } else if (lockVec.x < 0) {
                dnX = posCheck.x - posStart.x
            }
            if (lockVec.y > 0) {
                dnY = posCheck.y - posStart.y + 1
            } else if (lockVec.y < 0) {
                dnY = posCheck.y - posStart.y
            }
            if (lockVec.z > 0) {
                dnZ = posCheck.z - posStart.z + 1
            } else if (lockVec.z < 0) {
                dnZ = posCheck.z - posStart.z
            }

            var sX: Double = dnX / lockVec.x
            var sY: Double = dnY / lockVec.y
            var sZ: Double = dnZ / lockVec.z

            if (sX == -0.0D) {
                sX = -1.0E-4f
            }
            if (sY == -0.0D) {
                sY = -1.0E-4f
            }
            if (sZ == -0.0D) {
                sZ = -1.0E-4f
            }


            if (sX < sY && sX < sZ) {
                direction = if (lockVec.x > 0) Direction.WEST else Direction.EAST
                posStart.set(posStart.x + dnX.toFloat, (posStart.y + lockVec.y * sX).toFloat, (posStart.z + lockVec.z * sX).toFloat)
            } else if (sY < sZ) {
                direction = if (lockVec.y > 0) Direction.DOWN else Direction.UP
                posStart.set((posStart.x + lockVec.x * sY).toFloat, (posStart.y + dnY).toFloat, (posStart.z + lockVec.z * sY).toFloat)
            } else {
                direction = if (lockVec.z > 0) Direction.NORTH else Direction.SOUTH
                posStart.set((posStart.x + lockVec.x * sZ).toFloat, (posStart.y + lockVec.y * sZ).toFloat, (posStart.z + dnZ).toFloat)
            }
            posCheck.set(Math.floor(posStart.x).toInt, Math.floor(posStart.y).toInt, Math.floor(posStart.z).toInt)

            direction match {
                case Direction.EAST | Direction.UP | Direction.SOUTH => posCheck.add(-direction.x, -direction.y, -direction.z)
                case _ =>
            }


            val chunk = getChunk(posCheck.x >> Chunk.offset, posCheck.y >> Chunk.offset, posCheck.z >> Chunk.offset)


            if (chunk != null) {
                val blockState = chunk.getBlock(posCheck.x, posCheck.y, posCheck.z)
                blockState.block match {
                    case BlockAir =>
                    case _ => result = blockState.collisionRayTrace(posStart, posEnd2)
                }
            }
        }
        result
    }

    def addBlocksInList(aabb: BoundingBox): mutable.HashSet[BoundingBox] = {
        var x0: Int = Math.floor(aabb.minX).toInt
        var y0: Int = Math.floor(aabb.minY).toInt
        var z0: Int = Math.floor(aabb.minZ).toInt
        var x1: Int = Math.ceil(aabb.maxX).toInt
        var y1: Int = Math.ceil(aabb.maxY).toInt
        var z1: Int = Math.ceil(aabb.maxZ).toInt


        if (x0 < -length *  Chunk.blockSize) {
            x0 = -length * Chunk.blockSize
        }
        if (y0 < -height * Chunk.blockSize) {
            y0 = -height * Chunk.blockSize
        }
        if (z0 < -width * Chunk.blockSize) {
            z0 = -width * Chunk.blockSize
        }
        if (x1 > length * Chunk.blockSize) {
            x1 = length * Chunk.blockSize
        }
        if (y1 > height * Chunk.blockSize) {
            y1 = height * Chunk.blockSize
        }
        if (z1 > width * Chunk.blockSize) {
            z1 = width * Chunk.blockSize
        }

        val aabbs = mutable.HashSet[BoundingBox]()

        for (x <- x0 to x1; y <- y0 to y1; z <- z0 to z1) {


            getBlock(x, y, z) match {
                case blockState: BlockState if (blockState.block == BlockAir) =>
                case blockState =>
                    aabbs ++= blockState.getSelectedBlockBody
            }
            //            block match {
            //                case state: BlockCellState =>
            //                    aabbs ++= state.block.asInstanceOf[BlockCell].getBlocksState.map(_.getSelectedBlockBody)
            //                case _ =>
            //                    if (!block.isAirBlock) {
            //                        aabbs += block.getSelectedBlockBody
            //                    }
            //
            //            }
        }
        aabbs
    }
    def save(): Unit = {
        log.info("World saved...")
        log.info("Saving chunks for level \'{}\'/{}")
        saveAllChunks(true)
        log.info("Saving players for level \'{}\'/{}")
        //todo
        //        entities.splitter. foreach {
        //            case entityPlayer:EntityPlayer =>
        //                saveHandler.writePlayerData(entityPlayer)
        //            case _ =>
        //        }
        log.info("World saved completed")
    }
    def saveAllChunks(p_73044_1: Boolean /*, progressCallback: IProgressUpdate*/): Unit = {

        chunkProvider.saveChunks(p_73044_1)
    }
}
