#version 400 core

layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_uv;

uniform mat4 transformation, projection, view;
uniform sampler2D globeHeightmap;

out vec3 position;

#define HEIGHT_MULTIPLIER 0.1

#define PI 3.14159265358979323
vec2 pointOnSphereToUV(vec3 p) {
    p = normalize(p);
    float u = 1 - (atan(p.z, p.x) / PI + 1) / 2;
    float v = asin(p.y) / PI + 0.5;
    return vec2(u, v);
}

void main(void) {
    vec2 uv = pointOnSphereToUV(in_position);
    gl_Position =
        projection *
        view *
//        transformation *
        vec4(in_position * (1 + HEIGHT_MULTIPLIER * texture(globeHeightmap, uv).a), 1.0);
    position = in_position;
}