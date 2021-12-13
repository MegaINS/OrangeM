package ru.megains.mge

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.{GL, GL11}
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.opengl.GL11._

class Window(var width:Int, var height:Int, title:String) {


    lazy val id: Long = glfwCreateWindow(width, height, title, NULL, NULL)

    Window.window = this
    def create(game: Game): Unit = {

        if (!glfwInit) throw new IllegalStateException("Unable to initialize GLFW")
        glfwDefaultWindowHints()
        GLFWErrorCallback.createPrint(System.err).set
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        if (id == NULL) throw new RuntimeException("Failed to create the GLFW window")
        glfwSetKeyCallback(id, (window, key, scancode, action, mods) => {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true)
        })
//
//        try {
//            val stack = stackPush
//            try {
//                val pWidth = stack.mallocInt(1)
//                val pHeight = stack.mallocInt(1)
//                glfwGetWindowSize(id, pWidth, pHeight)
//                val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor)
//                glfwSetWindowPos(id, (vidmode.width - pWidth.get(0)) / 2, (vidmode.height - pHeight.get(0)) / 2)
//                if (stack != null) stack.close()
//            }catch {
//                case e: Throwable => e.printStackTrace()
//            }
//        }catch {
//            case e: Throwable => e.printStackTrace()
//        }

        glfwMakeContextCurrent(id)
        glfwSwapInterval(0)
        glfwShowWindow(id)
        GL.createCapabilities
        GL11.glViewport(0, 0, width, height)
        glfwSetWindowSizeCallback(id,(window: Long, widthIn: Int, heightIn: Int)=>{
            width = widthIn
            height = heightIn
            GL11.glViewport(0, 0, width, height)
            game.resize(width,height)
        })

        setClearColor(150f/256f,187f/256f,1f,1)
        setSwapInterval(1)
        //glfwWindowHint(GLFW_DEPTH_BITS, 32)
    }

    def update(): Unit ={
        glfwSwapBuffers(id)
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT)
    }

    def isClose:Boolean = glfwWindowShouldClose(id)

    def destroy(): Unit ={
        glfwFreeCallbacks(id)
        glfwDestroyWindow(id)
        glfwTerminate()
        glfwSetErrorCallback(null).free()
    }

    def setClearColor(r:Float,g:Float,b:Float,a:Float): Unit ={
        glClearColor(r, g, b, a)
    }

    def setSwapInterval(interval:Int) :Unit={
        glfwSwapInterval(interval)
    }
}

object Window{

    var window:Window =_
    def width: Int = window.width
    def height: Int = window.height
}
