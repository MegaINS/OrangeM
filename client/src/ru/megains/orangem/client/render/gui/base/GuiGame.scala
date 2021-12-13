package ru.megains.orangem.client.render.gui.base

import org.lwjgl.glfw.GLFW.{GLFW_KEY_E, GLFW_KEY_ESCAPE}
import ru.megains.orangem.common.entity.player.EntityPlayer

abstract class GuiGame extends Gui{


    def keyTyped(typedChar: Char, keyCode: Int): Unit = {
        keyCode match {
            case GLFW_KEY_E | GLFW_KEY_ESCAPE =>
               // tar.playerController.net.sendPacket(new CPacketCloseWindow)
                 gameScene.guiRenderer.closeGui()
            case _ =>
        }
    }
    def mouseReleased(x: Int, y: Int, button: Int): Unit = {}

    def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        //        if (button == 0) {
        //            buttonList.foreach(
        //                guiButton => {
        //                    if (guiButton.isMouseOver(x, y)) {
        //                        actionPerformed(guiButton)
        //                        return
        //                    }
        //                }
        //            )
        //        }

    }

    def mouseClickMove(x: Int, y: Int): Unit = {}
}
