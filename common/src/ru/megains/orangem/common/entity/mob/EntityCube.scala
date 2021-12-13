package ru.megains.orangem.common.entity.mob

import ru.megains.orangem.common.entity.EntityLivingBase

import scala.util.Random


class EntityCube() extends EntityLivingBase(1.8f, 0.6f, 1.6f) {


    rotYaw = (Random.nextFloat()-0.5f)*720f




    var x = 0
    var t = 20
    var xo = 0
    var zo = 0
    override def update(): Unit = {


        x+=1
        if(x % t == 0){
            //t = Random.nextInt(20)+1
            xo = if(Random.nextFloat()>0.5) 1 else 0
            zo = if(Random.nextFloat()>0.5) 1 else 0
            if(onGround){
                motionY = if( Random.nextFloat()<0.2) 0.42f  else 0
            }

            if(x % 100 == 0){
                turn(0,(Random.nextFloat()-0.5f)*720f)
            }
        }

        calculateMotion(xo, zo, if (onGround) 0.04f else 0.02f)
        move(motionX, motionY, motionZ)
        motionX *= 0.8f
        if (motionY > 0.0f) {
            motionY *= 0.90f
        }
        else {
            motionY *= 0.98f
        }
        motionZ *= 0.8f
        motionY -= 0.04f
        if (onGround) {
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