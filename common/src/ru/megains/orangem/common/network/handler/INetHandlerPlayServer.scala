package ru.megains.orangem.common.network.handler

import ru.megains.orangem.common.network.packet.play.client._

trait INetHandlerPlayServer extends INetHandler {

    def processCloseWindow(packetIn: CPacketCloseWindow): Unit

    def processPlayerMouse(packetIn: CPacketPlayerMouse): Unit

  //  def processNEI(packetIn: CPacketNEI): Unit


    def processClickWindow(packetIn: CPacketClickWindow): Unit

    def processPlayer(packetIn: CPacketPlayer): Unit

  //  def processPlayerDigging(packetIn: CPacketPlayerDigging): Unit

    def processHeldItemChange(packetIn: CPacketHeldItemChange): Unit

   // def processPlayerBlockPlacement(packetIn: CPacketPlayerTryUseItem): Unit

   // def processRightClickBlock(packetIn: CPacketPlayerTryUseItemOnBlock): Unit

}
