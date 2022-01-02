package ru.megains.orangem.server

import ru.megains.orangem.common.{PacketProcess, ServerStatusResponse}
import ru.megains.orangem.common.register.{Bootstrap, GameRegister}
import ru.megains.orangem.common.utils.Logger
import ru.megains.orangem.common.world.World
import ru.megains.orangem.common.world.data.AnvilSaveFormat
import ru.megains.orangem.server.network.NetworkSystem
import ru.megains.orangem.server.world.{ServerWorldEventHandler, WorldServer}

import scala.collection.mutable
import scala.reflect.io.{Directory, Path}

class OrangeMGServer(serverDir:Directory) extends Runnable with  Logger[OrangeMGServer] with PacketProcess {


    var running = true

    val networkSystem:NetworkSystem = new NetworkSystem(this)
    val statusResponse: ServerStatusResponse = new ServerStatusResponse

    var saveLoader: AnvilSaveFormat = _
    var world: WorldServer = _
    var serverThread: Thread = Thread.currentThread

    var playerList: PlayerList = _

    var timeOfLastWarning: Long = 0
    val futureTaskQueue: mutable.Queue[()=>Unit] = new mutable.Queue[()=>Unit]
    def start(): Boolean = {
        log.info("Starting OrangeMG server  version 0.0.1")
        Bootstrap.init(GameRegister)


        networkSystem.startLan(null, 20000)
        // networkSystem.startLocal()


        saveLoader = new AnvilSaveFormat(serverDir)
        loadAllWorlds()

        true
    }


    override def run(): Unit = {

        if (start()) {

            var var1: Long = OrangeMGServer.getCurrentTimeMillis
            var var50: Long = 0L


            while (running) {

                val var5: Long = OrangeMGServer.getCurrentTimeMillis
                var var7: Long = var5 - var1


                if (var7 > 2000L && var1 - timeOfLastWarning >= 15000L) {
                    log.warn("Can\'t keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", var7, var7 / 50L)
                    var7 = 2000L
                    timeOfLastWarning = var1
                }

                if (var7 < 0L) {
                    log.warn("Time ran backwards! Did the system time change?")
                    var7 = 0L
                }



                var50 += var7
                var1 = var5


                while (var50 > 50L) {
                    var50 -= 50L
                    tick()

                }


                Thread.sleep(Math.max(1L, 50L - var50))

            }



            stopServer()


        }
    }

    def saveAllWorlds(dontLog: Boolean) {

        if (!dontLog) log.info("Saving chunks for level \'{}\'/{}")
        world.saveAllChunks(true)
        /*
                for (worldserver <- this.worldServers) {
                    if (worldserver != null) {
                        if (!dontLog) log.info("Saving chunks for level \'{}\'/{}", Array[AnyRef](worldserver.getWorldInfo.getWorldName, worldserver.provider.getDimensionType.getName))
                        try
                            worldserver.saveAllChunks(true, null.asInstanceOf[IProgressUpdate])

                        catch {
                            case minecraftexception: MinecraftException => {
                                LOG.warn(minecraftexception.getMessage)
                            }
                        }
                    }
                }
        */
    }

    def stopServer(): Unit = {
        log.info("Stopping server")
        networkSystem.stop()
        if (this.playerList != null) {
            log.info("Saving players")
            // playerList.saveAllPlayerData()
            //  playerList.removeAllPlayers()
        }

        if (world != null) {
            log.info("Saving worlds")
            saveAllWorlds(false)


            /*
                          for (worldserver <- this.worldServers) {
                            if (worldserver != null) worldserver.disableLevelSaving = false
                        }
            //todo  world.flush()
                        for (worldserver1 <- this.worldServers) {
                            if (worldserver1 != null) {
                                net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new WorldEvent.Unload(worldserver1))
                                worldserver1.flush()
                            }
                        }
                        val tmp: Array[WorldServer] = worldServers
                        for (world <- tmp) {
                            net.minecraftforge.common.DimensionManager.setWorld(world.provider.getDimension, null, this)
                        }
            */
        }
    }

    def tick(): Unit = {
        futureTaskQueue synchronized {
            while (futureTaskQueue.nonEmpty){
                val a =  futureTaskQueue.dequeue()
                if(a!= null){
                    a()
                }
            }

        }



        networkSystem.networkTick()
        world.update()

    }

    def loadAllWorlds(): Unit = {
        val saveHandler = saveLoader.getSaveLoader("world")
        world = new WorldServer( saveHandler)
       // world.addEventListener(new ServerWorldEventHandler(this, world))
        playerList = new PlayerList(this)

        initialWorldChunkLoad()
    }

    def initialWorldChunkLoad(): Unit = {

    }


    def getServerStatusResponse: ServerStatusResponse = statusResponse

    override def addPacket(packet:()=>Unit): Unit = {
        futureTaskQueue += packet
    }
}
object OrangeMGServer {
    def getCurrentTimeMillis: Long = System.currentTimeMillis
}