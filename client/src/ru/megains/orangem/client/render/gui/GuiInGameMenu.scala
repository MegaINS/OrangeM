package ru.megains.orangem.client.render.gui

import org.lwjgl.glfw.GLFW.{GLFW_KEY_E, GLFW_KEY_ESCAPE}
import ru.megains.orangem.client.render.gui.base.{GuiInGame, GuiScreen}
import ru.megains.orangem.client.render.gui.element.MButton
import ru.megains.orangem.client.render.gui.menu.GuiGameSettings
import ru.megains.orangem.client.scene.{SceneGame, SceneGui}

class GuiInGameMenu(gameScene: SceneGame) extends GuiScreen {

    val buttonMainMenu: MButton = new MButton("Main menu", 300, 40, _ => {
        gameScene.game.setScene(new SceneGui(gameScene.game))
    })
    val buttonOption: MButton = new MButton("Option", 300, 40, _ => {
        gameScene.guiRenderer.openGui(new GuiGameSettings(gameScene.game, ()=>{gameScene.guiRenderer.openGui(this)}))
    })
    val buttonReturnToGame: MButton = new MButton("Return to game", 300, 40, _ => {
        gameScene.guiRenderer.closeGui()
    })


    override def init(): Unit = {

        addChildren(buttonMainMenu, buttonOption, buttonReturnToGame)
    }
    override def keyTyped(typedChar: Char, keyCode: Int): Unit = {
        keyCode match {
            case GLFW_KEY_ESCAPE =>
                // tar.playerController.net.sendPacket(new CPacketCloseWindow)
                gameScene.guiRenderer.closeGui()
            case _ =>
        }
    }

    override def resize(width: Int, height: Int): Unit = {
        buttonMainMenu.posX = (width - 300) / 2
        buttonMainMenu.posY = 240

        buttonOption.posX = (width - 300) / 2
        buttonOption.posY = 310

        buttonReturnToGame.posX = (width - 300) / 2
        buttonReturnToGame.posY = 380
    }
}

