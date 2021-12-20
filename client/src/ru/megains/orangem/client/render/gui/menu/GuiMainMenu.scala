package ru.megains.orangem.client.render.gui.menu

import ru.megains.mge.render.MContainer
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.render.gui.base.GuiScreen
import ru.megains.orangem.client.render.gui.element.MButton
import ru.megains.orangem.client.scene.SceneGui

class GuiMainMenu(orangeM: OrangeM) extends GuiScreen {

    val sceneGui:SceneGui = orangeM.scene.asInstanceOf[SceneGui]

    val buttonSingleGame: MButton = new MButton("SingleGame", 300, 40, _ => {
        sceneGui.setGuiScreen(new GuiSelectWorld(orangeM,this))
    })
    val buttonMultiPlayerGame: MButton = new MButton("MultiPlayerGame", 300, 40, _ => {
        sceneGui.setGuiScreen(new GuiMultiPlayer(orangeM,this))
    })
    val buttonOption: MButton = new MButton("Option", 300, 40, _ => {
        sceneGui.setGuiScreen(new GuiGameSettings(orangeM, ()=>{sceneGui.setGuiScreen(this)}))
    })
    val buttonExitGame: MButton = new MButton("Exit game", 300, 40,_ => {
        orangeM.running = false
    })

    override def init(): Unit ={
        addChildren(buttonSingleGame, buttonMultiPlayerGame, buttonOption, buttonExitGame)
    }


    override def resize(width: Int, height: Int): Unit = {

        buttonSingleGame.posX = (width - 300) / 2
        buttonSingleGame.posY = 240

        buttonMultiPlayerGame.posX = (width - 300) / 2
        buttonMultiPlayerGame.posY = 310

        buttonOption.posX = (width - 300) / 2
        buttonOption.posY = 380

        buttonExitGame.posX = (width - 300) / 2
        buttonExitGame.posY = 450


    }



}
