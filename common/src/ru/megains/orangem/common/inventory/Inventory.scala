package ru.megains.orangem.common.inventory

import ru.megains.orangem.common.item.itemstack.ItemStack

trait Inventory {

    def getStackInSlot(index: Int): ItemStack

    def setInventorySlotContents(index: Int, itemStack: ItemStack): Unit

    def decrStackSize(index: Int, size:Int): ItemStack

   // def writeToNBT(data: NBTCompound): Unit

    //def readFromNBT(data: NBTCompound): Unit

}
