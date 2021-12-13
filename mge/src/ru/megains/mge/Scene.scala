package ru.megains.mge

trait Scene {


    def runTickKeyboard(key: Int, action: Int, mods: Int):Unit

    def init():Unit

    def render():Unit

    def update():Unit

    def resize(width:Int,height:Int): Unit

    def destroy():Unit

    def runTickMouse(button: Int,action: Int, mods: Int):Unit

}
