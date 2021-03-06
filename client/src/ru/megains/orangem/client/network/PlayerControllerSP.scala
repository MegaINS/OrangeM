package ru.megains.orangem.client.network

import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.entity.EntityPlayerSP
import ru.megains.orangem.client.network.handler.NetHandlerPlayClient
import ru.megains.orangem.common.entity.player.{EntityPlayer, GameType}
import ru.megains.orangem.common.network.packet.play.client.CPacketClickWindow
import ru.megains.orangem.common.world.World

class PlayerControllerSP(tar:OrangeM, val net: NetHandlerPlayClient) {



    var isHittingBlock: Boolean = false
    var blockHitDelay: Int = 0
    var currentPlayerItem: Int = 0

    var currentGameType: GameType = GameType.CREATIVE

    def setGameType(gameType: GameType): Unit = {
        currentGameType = gameType
    }

    def sendQuittingDisconnectingPacket(): Unit = {
        net.netManager.closeChannel("Quitting")
    }
    def createClientPlayer(world: World): EntityPlayerSP = {
        new EntityPlayerSP(tar, world, net)
    }

    def rightClickMouse(): Unit = {
//        syncCurrentPlayItem()
//        net.sendPacket(new CPacketPlayerMouse(1,0,tar))
//        val rayTrace = tar.objectMouseOver
//
//        rayTrace.rayTraceType match {
//            case RayTraceType.BLOCK  =>
//                val itemstack: ItemPack = tar.player.getHeldItem
//                val block: BlockState = tar.world.getBlock(rayTrace.blockPos)
//
//                if (block.onBlockActivated(tar.world, rayTrace.blockPos, tar.player, itemstack, rayTrace.sideHit, rayTrace.hitVec.x, rayTrace.hitVec.y, rayTrace.hitVec.z)){
//
//                }else{
//                    if(itemstack!= null){
//                        itemstack.item match {
//                            case _:ItemBlock =>
////                                val blockState = tar.blockSelectPosition
////                                if(blockState!=null){
////                                    itemstack.onItemUse(tar.player, tar.world,blockState, rayTrace.sideHit, rayTrace.hitVec.x, rayTrace.hitVec.y, rayTrace.hitVec.z)
////                                }
//
//                            case _ =>
//                        }
//                    }
//
//
//
//                }
//            case RayTraceType.VOID  =>
//            case RayTraceType.ENTITY  =>
//        }
    }

    def leftClickMouse(): Unit = {
//        syncCurrentPlayItem()
//        net.sendPacket(new CPacketPlayerMouse(0,0, tar))
//
//        tar.objectMouseOver.rayTraceType match {
//            case RayTraceType.BLOCK  =>
//            case RayTraceType.VOID  =>
//            case RayTraceType.ENTITY  =>
//        }
    }



    def syncCurrentPlayItem() {
//        val i: Int = tar.player.inventory.stackSelect
//        if (i != currentPlayerItem) {
//            currentPlayerItem = i
//            net.sendPacket(new CPacketHeldItemChange(currentPlayerItem))
//        }
    }
    def windowClick(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        player.openContainer.mouseClicked(x, y, button, player)
        net.sendPacket(new CPacketClickWindow(x, y, button))
    }


}
