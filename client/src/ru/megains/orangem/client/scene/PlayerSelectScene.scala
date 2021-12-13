package ru.megains.orangem.client.scene

import ru.megains.mge.render.MContainer
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import org.lwjgl.opengl.GL11._
import ru.megains.orangem.client.OrangeMClient
import ru.megains.orangem.client.render.gui.element.{MButton, MSlider}
import ru.megains.orangem.client.render.shader.GuiShader

class PlayerSelectScene(orangeM:OrangeMClient) extends BaseScene {

    val buttonTest_1:MButton = new MButton("Test_1",300, 40,() =>{orangeM.playerName = "Test_1"; orangeM.setScene(new MainMenuScene(orangeM))})
    val buttonTest_2:MButton = new MButton("Test_2",300, 40,() =>{orangeM.playerName = "Test_2"; orangeM.setScene(new MainMenuScene(orangeM))})
    val buttonTest_3:MButton = new MButton("Test_3",300, 40,() =>{orangeM.playerName = "Test_3"; orangeM.setScene(new MainMenuScene(orangeM))})
    val buttonTest_4:MButton = new MButton("Test_4",300, 40,() =>{orangeM.playerName = "Test_4"; orangeM.setScene(new MainMenuScene(orangeM))})
    val testSlider:MSlider = new MSlider("Test",300,40,0,100,50)
    addChildren( buttonTest_1, buttonTest_2, buttonTest_3, buttonTest_4,testSlider)

    override def resize(width:Int,height:Int): Unit = {


        testSlider.posX = (width - 300) / 2
        testSlider.posY = 150


        buttonTest_1.posX = (width - 300) / 2
        buttonTest_1.posY = 240

        buttonTest_2.posX = (width - 300) / 2
        buttonTest_2.posY = 310


        buttonTest_3.posX = (width - 300) / 2
        buttonTest_3.posY = 380

        buttonTest_4.posX = (width - 300) / 2
        buttonTest_4.posY = 450
    }
}
