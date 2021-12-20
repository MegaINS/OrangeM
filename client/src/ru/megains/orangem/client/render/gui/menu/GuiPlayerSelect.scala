package ru.megains.orangem.client.render.gui.menu

import ru.megains.mge.render.MContainer
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.render.gui.base.GuiScreen
import ru.megains.orangem.client.render.gui.element.MButton
import ru.megains.orangem.client.scene.SceneGui

class GuiPlayerSelect(orangeM: OrangeM) extends GuiScreen {

    val buttonTest_1: MButton = new MButton("Test_1", 300, 40, buttonSelectPlayer)
    val buttonTest_2: MButton = new MButton("Test_2", 300, 40, buttonSelectPlayer)
    val buttonTest_3: MButton = new MButton("Test_3", 300, 40, buttonSelectPlayer)
    val buttonTest_4: MButton = new MButton("Test_4", 300, 40, buttonSelectPlayer)



    def buttonSelectPlayer(mButton: MButton): Unit = {
        orangeM.playerName = mButton.buttonText
        orangeM.scene.asInstanceOf[SceneGui].setGuiScreen(new GuiMainMenu(orangeM))
    }


    override def resize(width: Int, height: Int): Unit = {

        buttonTest_1.posX = (width - 300) / 2
        buttonTest_1.posY = 240

        buttonTest_2.posX = (width - 300) / 2
        buttonTest_2.posY = 310


        buttonTest_3.posX = (width - 300) / 2
        buttonTest_3.posY = 380

        buttonTest_4.posX = (width - 300) / 2
        buttonTest_4.posY = 450


    }

    override def init(): Unit = {
        addChildren(buttonTest_1, buttonTest_2, buttonTest_3, buttonTest_4)
    }
}
