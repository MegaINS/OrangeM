package ru.megains.orangem.client.scene

import ru.megains.mge.render.MContainer
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.shader.Shader
import org.lwjgl.opengl.GL11._
import ru.megains.orangem.client.OrangeMClient
import ru.megains.orangem.client.render.gui.element.MButton
import ru.megains.orangem.client.render.shader.GuiShader

class MainMenuScene(orangeM: OrangeMClient) extends BaseScene {

    val buttonSingleGame: MButton = new MButton("SingleGame", 300, 40, () => {
        orangeM.setScene(new SelectWorldScene(orangeM))
    })
    val buttonMultiPlayerGame: MButton = new MButton("MultiPlayerGame", 300, 40, () => {
        orangeM.setScene(new MultiPlayerScene(orangeM))
    })
    val buttonOption: MButton = new MButton("Option", 300, 40, () => {
        /*orangeM.setScene(new OptionScene())*/
    })
    val buttonExitGame: MButton = new MButton("Exit game", 300, 40, () => {
        orangeM.running = false
    })

    val buttonTest_1: MButton = new MButton("Test_1", 100, 40, () => {
        orangeM.playerName = "Test_1";
    })
    val buttonTest_2: MButton = new MButton("Test_2", 100, 40, () => {
        orangeM.playerName = "Test_2";
    })
    val buttonTest_3: MButton = new MButton("Test_3", 100, 40, () => {
        orangeM.playerName = "Test_3";
    })
    val buttonTest_4: MButton = new MButton("Test_4", 100, 40, () => {
        orangeM.playerName = "Test_4";
    })


    addChildren(buttonSingleGame, buttonMultiPlayerGame, buttonOption, buttonExitGame)

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
