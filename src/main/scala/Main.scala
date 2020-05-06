import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL13._

import javax.imageio.ImageIO
import scala.io.Source

object Main {

	def run(): Unit = {
		Window.create()
		loop()
		Window.destroy()
	}
	
	def loop(): Unit = {
		GL.createCapabilities
		
		glViewport(0, 0, 800, 600)
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
		glEnable(GL_BLEND)
		
		val program = new ShaderProgram(
							Source.fromResource("vertex.glsl").mkString, 
							Source.fromResource("fragment.glsl").mkString )
		program.use
		program.setUniform("texture1", 0)
		program.setUniform("texture2", 1)
		
		val v1 = Array(
			// positions        colors            texture coordinates
			-0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, // bottom left
			 0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // bottom right
			-0.5f,  0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, // top left
			 0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, // top right
		)
		val i1 = Array(
			0, 2, 1,
			2, 3, 1,
		)
		val mesh = new Mesh(v1, i1)
		val texture1 = new Texture(getClass().getResourceAsStream("TriHard.png"))
		val texture2 = new Texture(getClass().getResourceAsStream("WutFace.png"))
		
		var currentTime = System.currentTimeMillis
		var timePassed: Long = 0

		while (!Window.shouldClose()) {
			glClear(GL_COLOR_BUFFER_BIT)
			
			Window.checkInputs
			
			// Rendering
			program.use
			program.setUniform("offset", (Math.sin(timePassed.toDouble / 1000.0).toFloat) / 2.0f)
			
			texture1.bind(GL_TEXTURE0)
			texture2.bind(GL_TEXTURE1)
			mesh.draw
			
			Window.update
			
			timePassed += System.currentTimeMillis - currentTime
			currentTime = System.currentTimeMillis
		}
		
		mesh.destroy
		texture1.destroy
		texture2.destroy
		program.destroy
	}

	def main(args: Array[String]): Unit = {
		run
	}
}
