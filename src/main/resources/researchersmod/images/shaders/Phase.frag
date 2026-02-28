#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
varying vec4 v_pos;

uniform sampler2D u_texture;
uniform float x_time;
uniform vec2 u_resolution;

vec4 rgba(vec2 offset) {
    return v_color * texture2D(u_texture, v_texCoords + offset);
}

//Returns 0-1
float rand(float seed)
{
    return fract(tan(distance(vec2(7205.0,9545.0)*1.61803398874989484820459,vec2(7205.0,9545.0))*seed)*43758.5453);
}

//Seed 0-1
float randomRange(float seed, float shift_max) {
    return (2.0 * shift_max * rand(seed)) - shift_max;
}

float SHIFT_MAX = 0.005;
float Y_SHIFT_MAX = 0.01;
float COLOUR_MAX = 0.001;

void main()
{
    vec4 outputColor = rgba(vec2(0,0));

    for (float i = 0.0; i < 12.0; i += 1.0) {
        float yinitialBound = rand(x_time + i);
        float yboundHeight = rand(x_time + i + 7.0) * 0.6;
        float xShift = randomRange(x_time + i + 13.0, SHIFT_MAX);
        float yShift = randomRange(x_time + i + 13.0, Y_SHIFT_MAX);

        vec2 offSetCoords = v_texCoords;
        offSetCoords.x += xShift;
        offSetCoords.y += yShift;

        float upperBound = yinitialBound + yboundHeight;
        float lowerBound = yinitialBound - yboundHeight;

        if (v_texCoords.y < upperBound && v_texCoords.y > lowerBound) {
            outputColor = rgba(vec2(xShift,yShift));
        }
    }
    float rnd = rand(x_time + 9035.0);
    vec2 colorOffset = vec2(randomRange(x_time + 2345.0, COLOUR_MAX),
                          randomRange(x_time + 9545.0, COLOUR_MAX));

    if (rnd < 0.33) {
        outputColor.r = rgba(colorOffset).r;
    }
    else if (rnd < 0.66) {
        outputColor.g = rgba(colorOffset).g;
    }
    else {
        outputColor.b = rgba(colorOffset).b;
    }

    gl_FragColor = outputColor;
}