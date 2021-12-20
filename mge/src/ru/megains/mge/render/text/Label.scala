package ru.megains.mge
package render.text

import org.lwjgl.opengl.GL11.{GL_BLEND, GL_CULL_FACE, GL_DEPTH_TEST, GL_LIGHTING, GL_ONE_MINUS_SRC_ALPHA, GL_QUADS, GL_SRC_ALPHA, GL_TEXTURE_2D, glBegin, glBindTexture, glBlendFunc, glDisable, glEnable, glEnd, glTexCoord2f, glVertex2f}
import ru.megains.mge.render.{MContainer, MObject}

import java.awt.Color
import ru.megains.mge.render.font.{FontRender, Fonts}
import ru.megains.mge.render.model.Model
import ru.megains.mge.render.shader.Shader



class Label(private var _text: String = "",private var _textStyle: TextStyle = TextStyle.default) extends MContainer {

    def width: Float = Fonts.firaSans.getWidth(text)

    var textModel:Model = new Model()
    var shadowModel:Model = new Model(){
        posX = 2
        posY = 2
    }

    addChildren(shadowModel,textModel)

    text = _text
    def text: String = _text

    def text_=(text: String): Unit = {
        _text = text

        textModel.mesh =FontRender.createStringGui(text, Color.WHITE, Fonts.firaSans)
        shadowModel.mesh = FontRender.createStringGui(text, Color.BLACK, Fonts.firaSans)
    }

    override def render(shader: Shader): Unit = {
        super.render(shader)
    }

    def textStyle: TextStyle = _textStyle

    def textStyle_=(textStyle: TextStyle): Unit = {
        _textStyle = textStyle
    }


}
