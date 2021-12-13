package ru.megains.orangem.common

trait PacketProcess {

  def addPacket(packet: () => Unit): Unit

}
