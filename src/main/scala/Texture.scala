import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL12._
import org.lwjgl.opengl.GL13._
import org.lwjgl.opengl.GL30._

import java.nio.ByteBuffer
import java.io.InputStream

class Texture(in: InputStream) {
	
	private val texture = glGenTextures()
	
	glBindTexture(GL_TEXTURE_2D, texture)
	
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
	
	{
		val decoder = new PNGDecoder(in);
		val data = ByteBuffer.allocateDirect(decoder.getWidth * decoder.getHeight * 4)
		decoder.decodeFlipped(data, decoder.getWidth * 4, PNGDecoder.Format.RGBA)
		data.flip()
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth, decoder.getHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, data)
		glGenerateMipmap(GL_TEXTURE_2D)
	}
	
	def bind(unit: Int): Unit = {
		glActiveTexture(unit)
		glBindTexture(GL_TEXTURE_2D, texture)
	}
	
	def destroy(): Unit = {
		glDeleteTextures(texture)
	}
}
