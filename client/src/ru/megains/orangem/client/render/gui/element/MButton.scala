package ru.megains.orangem.client.render.gui.element

import java.awt.Color
import ru.megains.mge.Mouse
import ru.megains.mge.render.font.Fonts
import ru.megains.mge.render.{MContainer, MSprite}
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.text.Label
import ru.megains.mge.render.texture.{Texture, TextureRegion}
import ru.megains.orangem.client.render.Resources
import ru.megains.orangem.client.render.gui.base.Gui


class MButton(val buttonText: String, widthIn: Int, heightIn: Int, func:MButton=>Unit) extends GuiElement {

    override val height: Int = heightIn
    override val width: Int = widthIn


    val textMesh: Label = new Label(buttonText){
        posX = (widthIn - width)/2
        posY = 10
    }

    val buttonUp:MSprite = new MSprite(new TextureRegion(Resources.WIDGETS,0,66,200,20),width,height)

    val buttonUpOver:MSprite = new MSprite(new TextureRegion(Resources.WIDGETS,0,86,200,20),width,height)
    val buttonDisable:MSprite = new MSprite(new TextureRegion(Resources.WIDGETS,0,46,200,20),width,height)
    var enable = true
    var over = false
    var click = false

    buttonUpOver.active = false
    buttonDisable.active = false

    addChildren(buttonUp,buttonUpOver,buttonDisable,textMesh)
//    def draw(mouseX: Int, mouseY: Int): Unit = {
//
//        val background: Model = if (!enable) buttonDisable else if (isMouseOver(mouseX, mouseY)) buttonDown else buttonUp
//
//
//
//       // drawObject(positionX, positionY, 1, background)
//
//      //  drawObject(positionX + 10 , positionY + 18, 1, textMesh)
//
//    }
//
////    def clear(): Unit = {
////       // textMesh.cleanUp()
////        //buttonDown.cleanUp()
////       // buttonUp.cleanUp()
////    }

    override def update(): Unit = {

        if (!enable) {
            buttonDisable.active = true
            buttonUp.active = false
        } else{
            buttonDisable.active = false
            buttonUp.active = true
        }
    }

    override def mouseMove(x: Int, y: Int): Unit = {
        if(enable){
            if(over != isMouseOver(Mouse.getX,Mouse.getY)){
                over = isMouseOver(Mouse.getX,Mouse.getY)
                if(over) {
                    buttonUp.active = false
                    buttonUpOver.active = true

                } else {
                    buttonUp.active = true
                    buttonUpOver.active = false

                }
            }
        }
    }


    override def mousePress(x: Int, y: Int,button:Int): Unit = {
        if(enable){
            if(isMouseOver(x: Int, y: Int)){
                func(this)
            }
        }

    }

    override def mouseRelease(x:Int,y:Int,button:Int):Unit= {

    }

    override def init(): Unit = {}
}
