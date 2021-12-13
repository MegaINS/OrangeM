package ru.megains.mge

trait Game {

    var window:Window

    def runTickMouse(button: Int, action: Int, mods: Int):Unit

    def runTickKeyboard(key: Int, action: Int, mods: Int):Unit

    def setScene(scene: Scene):Unit

    def resize(width:Int,height:Int): Unit
}
