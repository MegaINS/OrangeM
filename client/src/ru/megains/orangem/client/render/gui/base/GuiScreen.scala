package ru.megains.orangem.client.render.gui.base

import org.lwjgl.glfw.GLFW.{GLFW_KEY_E, GLFW_KEY_ESCAPE}
import ru.megains.orangem.common.entity.player.EntityPlayer

abstract class GuiScreen extends Gui{


    def keyTyped(typedChar: Char, keyCode: Int): Unit = {
//        keyCode match {
//            case GLFW_KEY_E | GLFW_KEY_ESCAPE =>
//                // tar.playerController.net.sendPacket(new CPacketCloseWindow)
//                gameScene.guiRenderer.closeGui()
//            case _ =>
//        }
    }

}
