package ru.megains.orangem.client.render.gui.menu

import org.lwjgl.glfw.GLFW._
import ru.megains.mge.render.MContainer
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.render.gui.GuiSlotWorld
import ru.megains.orangem.client.render.gui.base.GuiScreen
import ru.megains.orangem.client.render.gui.element.MButton
import ru.megains.orangem.client.scene.{SceneGame, SceneGui}
import ru.megains.orangem.common.world.World
import ru.megains.orangem.common.world.data.AnvilSaveHandler

class GuiSelectWorld(orangeM: OrangeM, previousScreen: GuiScreen) extends GuiScreen {

    val sceneGui: SceneGui = orangeM.scene.asInstanceOf[SceneGui]
    var selectWorld: GuiSlotWorld = _
    var savesArray: Array[String] = _
    var worldsSlot: Array[GuiSlotWorld] = _

    val buttonSelect: MButton = new MButton("Select", 300, 40, _ => {
        val saveHandler: AnvilSaveHandler = orangeM.saveLoader.getSaveLoader(selectWorld.worldName)
        orangeM.setScene(new SceneGame(orangeM, saveHandler))
    }) {
        enable = false
    }


    val buttonDelete: MButton = new MButton("Delete", 300, 40, _ => {
        orangeM.saveLoader.deleteWorldDirectory(selectWorld.worldName)
        sceneGui.setGuiScreen(this)
    }) {
        enable = false
    }

    val buttonCreateWorld: MButton = new MButton("CreateWorld", 300, 40, _ => {
        val saveHandler = orangeM.saveLoader.getSaveLoader("World " + (worldsSlot.length + 1))
        orangeM.setScene(new SceneGame(orangeM, saveHandler))
    })

    val buttonCancel: MButton = new MButton("Cancel", 300, 40, _ => {
        sceneGui.setGuiScreen(previousScreen)
    })

    override def init(): Unit = {

        savesArray = orangeM.saveLoader.getSavesArray
        worldsSlot = new Array[GuiSlotWorld](savesArray.length)
        for (i <- savesArray.indices) {
            worldsSlot(i) = new GuiSlotWorld(i, savesArray(i), orangeM)


            addChildren(worldsSlot(i))
        }
        addChildren(buttonSelect, buttonDelete, buttonCreateWorld, buttonCancel)
    }


    override def resize(width: Int, height: Int): Unit = {

        buttonSelect.posX = width / 2 - 350
        buttonSelect.posY = height - 150

        buttonDelete.posX = width / 2 - 350
        buttonDelete.posY = height - 70

        buttonCreateWorld.posX = width / 2 + 50
        buttonCreateWorld.posY = height - 150

        buttonCancel.posX = width / 2 + 50
        buttonCancel.posY = height - 70
        for (i <- worldsSlot.indices) {
            val ws = worldsSlot(i)
            ws.posX = (width - ws.width) / 2
            ws.posY = 100 + 70 * i
        }


    }

    override def mousePress(x: Int, y: Int, button: Int): Unit = {
        super.mousePress(x, y, button)

        if (button == GLFW_MOUSE_BUTTON_1) {
            var isSelect = false
            worldsSlot.foreach(
                slot => {
                    if (slot.isMouseOver(x - posX.toInt, y - posY.toInt)) {
                        slot.select = true
                        selectWorld = slot
                        isSelect = true
                    } else {
                        slot.select = false
                    }
                }
            )
            buttonDelete.enable = isSelect
            buttonSelect.enable = isSelect
            if (!isSelect) selectWorld = null
        }


    }


}
