package ru.megains.orangem.client.render.gui.menu

import ru.megains.mge.render.MContainer
import ru.megains.mge.render.text.Label
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.render.gui.base.GuiScreen
import ru.megains.orangem.client.render.gui.element.{MButton, MSlider}

class GuiGameSettings(orangeM: OrangeM, onClose:()=>Unit) extends GuiScreen {

    val name = new Label("Options")

    val fpsSlider:MSlider = new MSlider("FPS",300,40,1,500,orangeM.gameSettings.FPS,()=>{orangeM.gameSettings.FPS = fpsSlider.value;orangeM.timerFPS.targetTick = fpsSlider.value})
    val fovSlider:MSlider = new MSlider("FOV",300,40,1,100,orangeM.gameSettings.FOV,()=>{orangeM.gameSettings.FOV = fovSlider.value})

    val renderDistanceWidthSlider:MSlider = new MSlider("RENDER_DISTANCE_WIDTH",300,40,1,20,orangeM.gameSettings.RENDER_DISTANCE_WIDTH,()=>{orangeM.gameSettings.RENDER_DISTANCE_WIDTH = renderDistanceWidthSlider.value})
    val renderDistanceHeightSlider:MSlider = new MSlider("RENDER_DISTANCE_HEIGHT",300,40,1,10,orangeM.gameSettings.RENDER_DISTANCE_HEIGHT,()=>{orangeM.gameSettings.RENDER_DISTANCE_HEIGHT = renderDistanceHeightSlider.value})
    val buttonCancel: MButton = new MButton("Cancel", 300, 40, _ => {
        onClose()
    })

    override def init(): Unit ={
        addChildren(name,fpsSlider,fovSlider,renderDistanceWidthSlider,renderDistanceHeightSlider,buttonCancel)
    }


    override def resize(width: Int, height: Int): Unit = {
        name.posX = (width - name.width) / 2
        name.posY = 200

        fpsSlider.posX = (width - 300) / 2
        fpsSlider.posY = 240

        fovSlider.posX = (width - 300) / 2
        fovSlider.posY = 310

        renderDistanceWidthSlider.posX = (width - 300) / 2
        renderDistanceWidthSlider.posY = 380

        renderDistanceHeightSlider.posX = (width - 300) / 2
        renderDistanceHeightSlider.posY = 450

        buttonCancel.posX = (width - 300) / 2
        buttonCancel.posY = 520

    }



}
