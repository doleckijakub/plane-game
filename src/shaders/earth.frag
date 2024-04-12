#version 400 core

in vec3 position;

uniform sampler2D globeHeightmap;
uniform sampler2D globeColormap;
uniform vec3 lightPosition;

#define PI 3.14159265358979323
vec2 pointOnSphereToUV(vec3 p) {
    p = normalize(p);
    float u = 1 - (atan(p.z, p.x) / PI + 1) / 2;
    float v = asin(p.y) / PI + 0.5;
    return vec2(u, v);
}

void main(void) {
    vec2 uv = pointOnSphereToUV(position);

    vec3 lightDir = normalize(lightPosition - position);
    vec3 normalPos = normalize(position);
    float diffuse = dot(normalPos, lightDir);
    if (diffuse > 0) {
        vec3 up = vec3(0, 1, 0);
        vec3 tangent = normalize(cross(normalPos, up));
        vec3 bitangent = normalize(cross(tangent, normalPos));

        vec3 normalTex = texture(globeHeightmap, uv).xyz * 2 - 1;
        vec3 normal = normalTex.g * tangent + normalTex.r * bitangent + normalTex.b * normalPos;
        diffuse *= max(dot(normal, lightDir), 0);

        vec3 color = texture(globeColormap, uv).xyz;
        if (texture(globeHeightmap, uv).a < 0.01) {
//            color = vec3(0.17, 0.8, 1);
//
//            vec3 normal = normalize(position);
//
//            vec3 viewDir = normalize(-position);
//            vec3 reflected = reflect(-lightDir, normal);
//            float specular = pow(max(dot(viewDir, reflected), 0), 0.5);
//            color *= 1 + specular;
        }

        gl_FragColor = vec4(color * diffuse, 1.0);
    } else {
        gl_FragColor = vec4(0, 0.0, 0.0, 1.0);
    }

}