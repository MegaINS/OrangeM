package ru.megains.orangem.common.container

import ru.megains.orangem.common.inventory.{InventoryPlayer, Slot}

class ContainerPlayerInventory(inventoryPlayer: InventoryPlayer) extends Container {

    for (i <- 0 to 9) {
        addSlotToContainer(new Slot(inventoryPlayer, i, 14 + i * 48,  193))
    }
    for (i <- 0 to 9; j <- 0 to 2) {
        addSlotToContainer(new Slot(inventoryPlayer, 10 + i + j * 10, 14 + i * 48, 122 - j * 46))
    }

}
