package ru.megains.orangem.common.entity.player

import ru.megains.orangem.common.container.{Container, ContainerPlayerInventory}
import ru.megains.orangem.common.entity.Entity
import ru.megains.orangem.common.inventory.InventoryPlayer
import ru.megains.orangem.common.item.itemstack.ItemStack
import ru.megains.orangem.common.physics.BoundingBox
import ru.megains.orangem.common.register.GameRegister

import scala.collection.mutable
import scala.util.Random

class EntityPlayer(val name:String) extends Entity(1.8f, 0.6f, 1.6f) {



    val inventory = new InventoryPlayer(this)
    val inventoryContainer: Container = new ContainerPlayerInventory(inventory)
    var openContainer: Container = inventoryContainer
    var gameType: GameType = GameType.CREATIVE


    val g:Float = 20f/20f/20f
    val speedJump:Float = 6.5f/20f
    GameRegister.getItems.foreach(i => inventory.addItemStackToInventory(new ItemStack(i, Random.nextInt(100))))

    //    def openInventory(): Unit = {
    //        openContainer = inventoryContainer
    //        oc.guiManager.setGuiScreen(new GuiPlayerInventory(this))
    //    }


    def update(y: Float): Unit = {


        if (gameType.isCreative) {
            motionY += y * 0.15f
        } else {
            if (isJumping && onGround) {
                motionY = speedJump
            }
        }

        calculateMotion(moveForward, moveStrafing, if (onGround || gameType.isCreative) 0.04f else 0.02f)

        move(motionX, motionY, motionZ)



        motionX *= 0.8f
        motionZ *= 0.8f
        motionY *= 0.98f

        if(gameType.isSurvival){
            motionY -= g
        }
        if (gameType.isCreative) {
            motionY *= 0.90f
        }
        if (onGround && gameType.isSurvival) {
            motionX *= 0.9f
            motionZ *= 0.9f
        }
    }



    def turn(xo: Float, yo: Float) :Unit ={
        rotYaw += yo * 0.15f
        rotPitch += xo * 0.15f
        if (rotPitch < -90.0F) {
            rotPitch = -90.0F
        }
        if (rotPitch > 90.0F) {
            rotPitch = 90.0F
        }
        if (rotYaw > 360.0F) {
            rotYaw -= 360.0F
        }
        if (rotYaw < 0.0F) {
            rotYaw += 360.0F
        }
    }




}
