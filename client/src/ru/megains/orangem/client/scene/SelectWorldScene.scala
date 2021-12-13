package ru.megains.orangem.client.scene

import ru.megains.mge.render.MContainer
import ru.megains.mge.{Mouse, Scene, Window}
import ru.megains.mge.render.camera.OrthographicCamera
import ru.megains.mge.render.shader.Shader
import org.lwjgl.opengl.GL11._
import ru.megains.orangem.client.OrangeMClient
import ru.megains.orangem.client.render.gui.element.MButton
import ru.megains.orangem.client.render.shader.GuiShader

class SelectWorldScene(orangeM: OrangeMClient) extends BaseScene {

    val buttonSelect: MButton = new MButton("Select", 300, 50, () => {
        orangeM.setScene(new GameScene(orangeM))
        //            val saveHandler = tar.saveLoader.getSaveLoader(selectWorld.worldName)
        //            //tar.loadWorld(new World(saveHandler))
        //            tar.guiManager.setGuiScreen(null)
    })
    val buttonDelete: MButton = new MButton("Delete", 300, 50, () => {
        //                    tar.saveLoader.deleteWorldDirectory(selectWorld.worldName)
        //                    tar.guiManager.setGuiScreen(this)
    })

    val buttonCreateWorld: MButton = new MButton("CreateWorld", 300, 50, () => {
        //                    val saveHandler = tar.saveLoader.getSaveLoader("World " + (worldsSlot.length + 1))
        //                    //tar.loadWorld(new World(saveHandler))
        //                    tar.guiManager.setGuiScreen(null)

    })

    val buttonCancel: MButton = new MButton("Cancel", 300, 50, () => {
        orangeM.setScene(new MainMenuScene(orangeM))
    })
    addChildren(buttonSelect, buttonDelete, buttonCreateWorld, buttonCancel)

    override def resize(width: Int, height: Int): Unit = {

        buttonSelect.posX = width / 2 - 350
        buttonSelect.posY = height - 150

        buttonDelete.posX = width / 2 - 350
        buttonDelete.posY = height - 70

        buttonCreateWorld.posX = width / 2 + 50
        buttonCreateWorld.posY = height - 150

        buttonCancel.posX = width / 2 + 50
        buttonCancel.posY = height - 70
    }
}
