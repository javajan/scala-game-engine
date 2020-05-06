import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL11

class Shader(source: String, shaderType: Int) {
	private val shader: Int = glCreateShader(shaderType)
	
	glShaderSource(shader, source)
	glCompileShader(shader)
	
	{
		val success = glGetShaderi(shader, GL_COMPILE_STATUS)
		val len = glGetShaderi(shader, GL_INFO_LOG_LENGTH)
		val error = glGetShaderInfoLog(shader, len)
		if (error != null && error.length() != 0)
			println(error)
		if (success == GL11.GL_FALSE)
			throw new IllegalStateException("Could not compile shader: " + source)
	}
	
	def destroy(): Unit = {
		glDeleteShader(shader)
	}
	
	def id = shader
}

