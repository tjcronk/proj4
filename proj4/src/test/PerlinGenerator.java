package test;

public class PerlinGenerator {
    static float Noise(int x, int y) {
        int n = x + y * 57;
        n = (int) Math.pow((n << 13), n);
        return (float) (1.0 - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
    }

    static float SmoothNoise(int x, int y) {
        float corners = (Noise(x - 1, y - 1) + Noise(x + 1, y - 1)
                + Noise(x - 1, y + 1) + Noise(x + 1, y + 1)) / 16;
        float sides = (Noise(x - 1, y) + Noise(x + 1, y) + Noise(x, y - 1) + Noise(
                x, y + 1)) / 8;
        float center = Noise(x, y) / 4;
        return corners + sides + center;
    }

    static float Interpolate(float a, float b, float x) {
        double ft = x * 3.1415927;
        float f = (float) (1 - Math.cos(ft)) * .5f;

        return a * (1 - f) + b * f;
    }

    static float InterpoltedNoise(float x, float y) {
        int integer_X = (int) x;
        float fractional_X = x - integer_X;

        int integer_Y = (int) y;
        float fractional_Y = y - integer_Y;

        float v1 = SmoothNoise(integer_X, integer_Y);
        float v2 = SmoothNoise(integer_X + 1, integer_Y);
        float v3 = SmoothNoise(integer_X, integer_Y + 1);
        float v4 = SmoothNoise(integer_X + 1, integer_Y + 1);

        float i1 = Interpolate(v1, v2, fractional_X);
        float i2 = Interpolate(v3, v4, fractional_X);

        return Interpolate(i1, i2, fractional_Y);

    }

    static float PerlinNoise(float x, float y) {

        float total = 0;
        float p = .25f;
        int n = 10 - 1;
        float frequency = 2;
        float amplitude = 1;
        for (int i = 0; i < n; i++) {
            total = total + InterpoltedNoise(x * frequency, y * frequency) * amplitude;
            frequency = frequency * 2;
            amplitude = amplitude * p;
        }

        return total;
    }
}