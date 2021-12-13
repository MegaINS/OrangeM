package ru.megains.orangem.common.entity

import org.joml.Vector3d
import ru.megains.orangem.nbt.tag.NBTCompound
import ru.megains.orangem.common.physics.BoundingBox
import ru.megains.orangem.common.utils.{Direction, RayTraceResult}
import ru.megains.orangem.common.world.World

import scala.collection.mutable

abstract class Entity(val height: Float,val wight: Float,val levelView: Float) {

    var posX:Float = 0
    var posY:Float = 0
    var posZ:Float = 0
    var rotYaw:Float = 0
    var rotPitch:Float = 0

    var motionX = 0f
    var motionY = 0f
    var motionZ = 0f
    var speed = 1.5f
    var side:Direction = Direction.NONE
    var onGround = true
    val body: BoundingBox = new BoundingBox(-wight/2,0,-wight/2,wight/2,height,wight/2)

    var world:World = _
    var goY: Float = 0.5f
    var chunkCoordX = 0
    var chunkCoordY = 0
    var chunkCoordZ = 0

    def setWorld( world: World): Unit ={
        this.world = world
    }

    def setPosition(x: Float, y: Float, z: Float) :Unit ={
        posX = x
        posY = y
        posZ = z
        val i = wight/2
        body.set(x-i, y, z-i, x+i,y+ height, z+i)
    }

    def update(): Unit ={
        rotYaw match {
            case y if y > 315 || y <45 => side = Direction.NORTH
            case y if y <135 => side = Direction.EAST
            case y if y <225 => side = Direction.SOUTH
            case y if y <315 => side = Direction.WEST
            case _ => side = Direction.UP
        }
    }



    def rayTrace(blockDistance: Int): RayTraceResult = {
        val pos = new Vector3d(posX,posY+levelView,posZ)
        val lockVec: Vector3d = getLook.mul(blockDistance)
        world.rayTraceBlocks(pos,lockVec)
    }

    def getLook: Vector3d = {
        getVectorForRotation(rotPitch, rotYaw)
    }

    def getVectorForRotation(pitch : Float, yaw : Float):Vector3d = {

        val sinPitch:Float = Math.sin(-pitch.toRadians).toFloat
        val cosPitch:Float = Math.cos(-pitch.toRadians).toFloat
        val cosYaw : Float = Math.cos(yaw.toRadians- Math.PI ).toFloat
        val sinYaw : Float = Math.sin(yaw.toRadians).toFloat
        new Vector3d(sinYaw*cosPitch, sinPitch, cosYaw*cosPitch)

    }
    def calculateMotion(x: Float, z: Float, limit: Float): Unit = {
        val dist: Float = x * x + z * z
        if (dist > 0.0f) {
            val dX: Float = x / dist * speed * limit
            val dZ: Float = z / dist * speed * limit
            val sinYaw: Float = Math.sin(rotYaw.toRadians).toFloat
            val cosYaw: Float = Math.cos(rotYaw.toRadians).toFloat

            motionX += (dX * cosYaw - dZ * sinYaw)
            motionZ += (dZ * cosYaw + dX * sinYaw)
        }

    }
    def move(x: Float, y: Float, z: Float) :Unit ={
        var x0: Float = x
        var z0: Float = z
        var y0: Float = y
        var x1: Float = x
        var z1: Float = z
        var y1: Float = y

        val bodyCopy: BoundingBox = body.getCopy
        var physicalBodes: mutable.HashSet[BoundingBox] = world.addBlocksInList(body.expand(x0, y0, z0))

        physicalBodes.foreach(aabb => {
            y0 = aabb.checkYcollision(body, y0)
        })
        body.move(0, y0, 0)

        physicalBodes.foreach(aabb => {
            x0 = aabb.checkXcollision(body, x0)
        })
        body.move(x0, 0, 0)

        physicalBodes.foreach(aabb => {
            z0 = aabb.checkZcollision(body, z0)
        })
        body.move(0, 0, z0)

        onGround = y != y0 && y < 0.0F
        var a = true
        if (onGround && (Math.abs(x) > Math.abs(x0) || Math.abs(z) > Math.abs(z0))) {
            val b: Float = 0.0625f
            var tY = b
            while (tY <= goY && a) {
                val bodyCopy1: BoundingBox = bodyCopy.getCopy
                x1 = x
                z1 = z
                y1 = y
                physicalBodes = world.addBlocksInList(bodyCopy1.expand(x1, tY, z1))

                physicalBodes.foreach(aabb => {
                    y1 = aabb.checkYcollision(bodyCopy1, tY)
                })
                bodyCopy1.move(0, y1, 0)

                physicalBodes.foreach(aabb => {
                    x1 = aabb.checkXcollision(bodyCopy1, x1)
                })
                bodyCopy1.move(x1, 0, 0)

                physicalBodes.foreach(aabb => {
                    z1 = aabb.checkZcollision(bodyCopy1, z1)
                })
                bodyCopy1.move(0, 0, z1)

                if (Math.abs(x1) > Math.abs(x0) || Math.abs(z1) > Math.abs(z0)) {
                    body.set(bodyCopy1)
                    a = false
                }
                tY += b
            }
        }
        if (x0 != x & x1 != x) {
            motionX = 0.0F
        }
        if (y0 != y) {
            motionY = 0.0F
        }
        if (z0 != z & z1 != z) {
            motionZ = 0.0F
        }

        posX = body.getCenterX
        posY = body.minY
        posZ = body.getCenterZ

    }
    def readEntityFromNBT(compound: NBTCompound): Unit = {}

    def readFromNBT(compound: NBTCompound) {

//        val listPos = compound.getList("Pos")
//        val listMotion = compound.getList("Motion")
//        val listRotation = compound.getList("Rotation")
//        motionX = listMotion.getFloat(0)
//
//        motionY = listMotion.getFloat(1)
//
//        motionZ = listMotion.getFloat(2)
//
//        if (Math.abs(motionX) > 10.0D) motionX = 0.0f
//        if (Math.abs(motionY) > 10.0D) motionY = 0.0f
//        if (Math.abs(motionZ) > 10.0D) motionZ = 0.0f
//        posX = listPos.getFloat(0)
//
//        posY = listPos.getFloat(1)
//
//        posZ = listPos.getFloat(2)
//
//        prevPosX = posX
//        prevPosY = posY
//        prevPosZ = posZ
//
//        rotationYaw = listRotation.getFloat(0)
//        rotationPitch = listRotation.getFloat(1)
//        prevRotationYaw = rotationYaw
//        prevRotationPitch = rotationPitch
//
//        onGround = compound.getBoolean("OnGround")
//        readEntityFromNBT(compound)
//        setPosition(posX, posY, posZ)
//        setRotation(rotationYaw, rotationPitch)

    }

    def writeEntityToNBT(compound: NBTCompound): Unit = {}

    def writeToNBT(compound: NBTCompound): Unit = {

//        val listPos = compound.createList("Pos", EnumNBTFloat)
//        listPos.setValue(posX)
//        listPos.setValue(posY)
//        listPos.setValue(posZ)
//
//        val listMotion = compound.createList("Motion",EnumNBTFloat)
//        listMotion.setValue(motionX)
//        listMotion.setValue(motionY)
//        listMotion.setValue(motionZ)
//
//        val listRotation = compound.createList("Rotation", EnumNBTFloat)
//        listRotation.setValue(rotationYaw)
//        listRotation.setValue(rotationPitch)
//        compound.setValue("OnGround", onGround)
//        writeEntityToNBT(compound)

    }
}
