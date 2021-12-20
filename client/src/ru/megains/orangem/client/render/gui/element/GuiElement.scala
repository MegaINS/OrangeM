package ru.megains.orangem.client.render.gui.element

import ru.megains.orangem.client.render.gui.base.Gui

abstract class GuiElement() extends Gui {

    val width: Int = 0
    val height: Int = 0

   // var posX:Int = 0
    //var posY:Int= 0

    def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height
    }

 //   def this(tar: Tartess) {
//        this()
//        setData(tar)
//    }

   // var itemRender: ItemRender = _
   // var renderer: Renderer = _
   // var fontRender: FontRender = _
   // var tar: Tartess = _


//    def setData(tarIn: Tartess): Unit = {
//        //tar = tarIn
//       // itemRender = tar.itemRender
//       // renderer = tar.renderer
//       // fontRender = tar.fontRender
//
//
//        initGui()
//    }

    def initGui(): Unit = {}

   // def drawObject(mesh: Mesh, xPos: Int, yPos: Int): Unit = super.drawObject(xPos+posX, yPos+posY, 1, mesh, renderer)

   // def drawObject(xPos: Int, yPos: Int, scale: Float, mesh: Mesh): Unit = super.drawObject(xPos+posX, yPos+posY, scale, mesh, renderer)

   // def createString(text: String, color: Color): Mesh = fontRender.createStringGui(text, color, Fonts.timesNewRomanR)

   // def drawItemStack(itemStack: ItemPack, xPos: Int, yPos: Int): Unit = itemRender.renderItemStackToGui(xPos+posX, yPos+posY, itemStack)
}
