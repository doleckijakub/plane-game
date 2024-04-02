#version 400 core

layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_uv;

uniform mat4 transformation, projection, view;

out vec3 color;

void main(void) {
    gl_Position =
        projection *
        view *
        transformation *
        vec4(in_position, 1.0);
    color = in_position / 2 + vec3(0.5);
}