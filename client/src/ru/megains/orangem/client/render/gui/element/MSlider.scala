package ru.megains.orangem.client.render.gui.element

import ru.megains.mge.render.text.Label
import ru.megains.mge.render.texture.TextureRegion
import ru.megains.mge.render.{MContainer, MSprite}
import ru.megains.orangem.client.render.Resources

class MSlider(val sliderText: String, weidth: Int, height: Int,min:Int,max:Int,var value:Int) extends MContainer {


    val label: Label = new Label(sliderText) {
        posX = weidth / 2 - 35
        posY = 10
    }
    val buttonUp: MSprite = new MSprite(new TextureRegion(Resources.WIDGETS, 0, 66, 200, 20), 20, height) {
        posX = 2
    }
    val buttonDisable: MSprite = new MSprite(new TextureRegion(Resources.WIDGETS, 0, 46, 200, 20), weidth, height)

    addChildren(buttonDisable, buttonUp, label)

    var enable = true
    var isDrag = false


    override def update(): Unit = {
        label.text = sliderText + ":  " + value
        buttonUp.posX = 2 + (weidth - 24) * value / max
    }

    override def mousePress(x: Int, y: Int): Unit = {
        if(isMouseOver(x,y)){
            isDrag = !isDrag
        }
    }

    override def mouseMove(x: Int, y: Int): Unit = {
        if(isDrag){
          value = Math.min( Math.max(((x - posX-12)/(weidth.toFloat-24) * max ).toInt,min),max)
        }
    }

    override def mouseRelease(x: Int, y: Int): Unit = {
        if(isDrag) isDrag = !isDrag
    }

    def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        enable && mouseX >= posX && mouseX <= posX + weidth && mouseY >= posY && mouseY <= posY + height
    }
}
