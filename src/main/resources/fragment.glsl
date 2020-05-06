#version 330 core

uniform sampler2D texture1;
uniform sampler2D texture2;

in vec3 vertexColor;
in vec2 texCoord;

out vec4 fragColor;

void main()
{
    fragColor = mix(texture(texture1, texCoord), texture(texture2, texCoord), 0.5) * vec4(vertexColor, 1.0);
} 
