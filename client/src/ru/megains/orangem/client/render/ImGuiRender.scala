package ru.megains.orangem.client.render

import imgui.ImGui
import imgui.flag.ImGuiConfigFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import ru.megains.orangem.client.scene.SceneGame


class ImGuiRender(gameScene: SceneGame) {

    private val imGuiGlfw = new ImGuiImplGlfw
    private val imGuiGl3 = new ImGuiImplGl3

    private val glslVersion = "#version 130"


    def init(): Unit ={
        ImGui.createContext
        val io = ImGui.getIO
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable)
        imGuiGlfw.init(gameScene.game.window.id, true)
        imGuiGl3.init(glslVersion)
    }

    def render(): Unit ={
        imGuiGlfw.newFrame()
        ImGui.newFrame()

        imguiLayer.imgui()

        ImGui.render()
        imGuiGl3.renderDrawData(ImGui.getDrawData)

        if (ImGui.getIO.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            val backupWindowPtr = org.lwjgl.glfw.GLFW.glfwGetCurrentContext()
            ImGui.updatePlatformWindows()
            ImGui.renderPlatformWindowsDefault()

            // GLFW.glfwMakeContextCurrent(backupWindowPtr)

        }
    }

    val imguiLayer = new ImGuiLayer()
    class ImGuiLayer {
        var showText = false


        def imgui(): Unit = {
            ImGui.begin("Cool Window")
           // ImGui.sliderInt2("renderRangeH", renderRange, 1, 50)
            if (ImGui.button("I am a button")) showText = true
            if (showText) {
                ImGui.text("You clicked a button")
                ImGui.sameLine()
                if (ImGui.button("Stop showing text")) showText = false
            }
            ImGui.end
        }
    }
}
