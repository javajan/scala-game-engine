import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._

import org.lwjgl.BufferUtils
import java.nio.FloatBuffer

class Mesh(vertices: Array[Float], indices: Array[Int]) {

	private val vertexBuffer = glGenBuffers()
	private val vertexArray = glGenVertexArrays()
	private val elementBuffer = glGenBuffers()
	
	glBindVertexArray(vertexArray)
	
	glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer)
	glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)
	
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBuffer)
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)
	
	glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0)
	glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * 4, 3 * 4)
	glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * 4, 6 * 4)
	glEnableVertexAttribArray(0)
	glEnableVertexAttribArray(1)
	glEnableVertexAttribArray(2)
	
	glBindVertexArray(0)
	glBindBuffer(GL_ARRAY_BUFFER, 0)
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
	
	def draw(): Unit = {
		glBindVertexArray(vertexArray)
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)
		glBindVertexArray(0)
	}
	
	def destroy(): Unit = {
		glDeleteBuffers(vertexBuffer)
		glDeleteBuffers(elementBuffer)
		glDeleteVertexArrays(vertexArray)
	}
}
