import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL11

class ShaderProgram(vertexSource: String, fragmentSource: String) {
	private val program = glCreateProgram()
	
	private val vertexShader = new Shader(vertexSource, GL_VERTEX_SHADER)
	private val fragmentShader = new Shader(fragmentSource, GL_FRAGMENT_SHADER)
	
	glAttachShader(program, vertexShader.id)
	glAttachShader(program, fragmentShader.id)
	glLinkProgram(program)
	
	{
		val success = glGetProgrami(program, GL_LINK_STATUS)
		val len = glGetProgrami(program, GL_INFO_LOG_LENGTH)
		val error = glGetProgramInfoLog(program, len)
		if (error != null && error.length() != 0)
			println(error)
		if (success == GL11.GL_FALSE)
			throw new IllegalStateException("Could not link program")
	}
	
	def setUniform(name: String, a: Int): Unit = {
		glUseProgram(program)
		glUniform1i(getUniformLocation(name), a)
	}
	
	def setUniform(name: String, a: Float): Unit = {
		glUseProgram(program)
		glUniform1f(getUniformLocation(name), a)
	}
	
	def setUniform(name: String, a: Float, b: Float, c: Float, d: Float): Unit = {
		glUseProgram(program)
		glUniform4f(getUniformLocation(name), a, b, c, d)
	}
	
	private def getUniformLocation(name: String): Int = glGetUniformLocation(program, name)
	
	def use(): Unit = {
		glUseProgram(program)
	}
	
	def destroy(): Unit = {
		vertexShader.destroy()
		fragmentShader.destroy()
	}
}
