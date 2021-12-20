package ru.megains.orangem.client.render.gui.base
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.scene.SceneGame

abstract class GuiInGame extends Gui{


    var gameScene:SceneGame = _

    override def init(gameIn: OrangeM): Unit = {
        gameScene = gameIn.scene.asInstanceOf[SceneGame]
        super.init(gameIn)
    }
}
