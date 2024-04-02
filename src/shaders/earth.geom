#version 400 core

layout (points) in;
layout (points, max_vertices = 1) out;

uniform sampler2D globeHeightmap;

#define PI 3.14159265358979323
vec2 pointOnSphereToUV(vec3 p) {
    p = normalize(p);
    float u = 1 - (atan(p.z, p.x) / PI + 1) / 2;
    float v = asin(p.y) / PI + 0.5;
    return vec2(u, v);
}

void main(void) {
    vec4 position = gl_in[0].gl_Position;
    vec2 uv = pointOnSphereToUV(position.xyz);
    gl_Position = position * (1 + texture(globeHeightmap, uv).a);

    EmitVertex();
    EndPrimitive();
}

#version 330 core

layout (points) in;
layout (points, max_vertices = 1) out;

void main() {
    gl_Position = gl_in[0].gl_Position;
    EmitVertex();
    EndPrimitive();
}