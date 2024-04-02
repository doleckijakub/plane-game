#version 400 core

in vec3 position;

uniform sampler2D globeColormap;

#define PI 3.14159265358979323
vec2 pointOnSphereToUV(vec3 p) {
    p = normalize(p);
    float u = 1 - (atan(p.z, p.x) / PI + 1) / 2;
    float v = asin(p.y) / PI + 0.5;
    return vec2(u, v);
}

void main(void) {
    vec2 uv = pointOnSphereToUV(position);
    gl_FragColor = texture(globeColormap, uv);
}