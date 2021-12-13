package ru.megains.orangem.client.render.guiold

import ru.megains.orangem.common.entity.player.EntityPlayer


abstract class  GuiScreen /*extends GuiElement*/ {




   // val buttonList: ArrayBuffer[GuiButton] = new ArrayBuffer[GuiButton]()


//    override def setData(tar: Tartess): Unit = {
//        buttonList.foreach(_.clear())
//        buttonList.clear()
//        super.setData(tar)
//    }


    def mouseReleased(x: Int, y: Int, button: Int): Unit = {}

    def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        if (button == 0) {
//            buttonList.foreach(
//                guiButton => {
//                    if (guiButton.isMouseOver(x, y)) {
//                        actionPerformed(guiButton)
//                        return
//                    }
//                }
//            )
        }

    }

    def mouseClickMove(x: Int, y: Int): Unit = {}

   // def actionPerformed(button: GuiButton) {}

    def keyTyped(typedChar: Char, keyCode: Int): Unit = {

    }


    def drawScreen(mouseX: Int, mouseY: Int): Unit = {
       // buttonList.foreach(_.draw(mouseX, mouseY))
    }

    def drawDefaultBackground(): Unit = {
       // drawObject( 0, 0,GuiScreen.background)
    }

    def cleanup(): Unit = {
       // buttonList.foreach(_.clear())
      //  buttonList.clear()
    }

    def tick(): Unit = {}
}

//object GuiScreen extends Gui {
//
//    val background: Mesh = createGradientRect(2000, 2000, new Color(128, 128, 128, 128), new Color(0, 0, 0, 128))
//}


