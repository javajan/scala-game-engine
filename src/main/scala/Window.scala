import org.lwjgl._
import org.lwjgl.glfw._
import org.lwjgl.opengl._
import org.lwjgl.system._

import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryStack._
import org.lwjgl.system.MemoryUtil._

import java.nio._

import scala.util.Using

object Window {
	
	private[this] var window: Long = 0;
	
	def create(): Unit = {
		GLFWErrorCallback.createPrint(System.err).set();
	
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW")
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
		
		window = glfwCreateWindow(800, 600, "Cool Game 3", NULL, NULL)
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window"); 
		
		
		Using(MemoryStack.stackPush()) {stack =>
			val pWidth: IntBuffer = stack.mallocInt(1); // int*
			val pHeight: IntBuffer = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			val vidmode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
		
		glfwMakeContextCurrent(window);
		
		// Enable v-sync
		glfwSwapInterval(1);

		glfwShowWindow(window);
	}
	
	def checkInputs(): Unit = {
		if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
			glfwSetWindowShouldClose(window, true);
	}
	
	def update(): Unit = {
		

		glfwSwapBuffers(window);
		glfwPollEvents();
	}
	
	def close(): Unit = {
		glfwSetWindowShouldClose(window, true);
	}
	
	def shouldClose(): Boolean = {
		return glfwWindowShouldClose(window);
	}
	
	def destroy(): Unit = {
		glfwFreeCallbacks(window)
		glfwDestroyWindow(window)
		
		glfwTerminate()
		glfwSetErrorCallback(null).free();
	}
}
