package test;

import java.util.Random;

import org.lwjgl.Sys;

public class DiamondSquare {
    private static final Random random = new Random(Sys.getTime());
    public double[] values;
    private int w, h;
    
    public DiamondSquare(int w, int h, int featureSize) {
        this.w = w;
        this.h = h;

        values = new double[w * h];

        for (int y = 0; y < w; y += featureSize) {
            for (int x = 0; x < w; x += featureSize) {
                setSample(x, y, random.nextFloat() * 2 - 1);
            }
        }
        
        int stepSize = featureSize;
        double scale = 1.0 / w;
        double scaleMod = 1;
        do {
            int halfStep = stepSize / 2;
            for (int y = 0; y < w; y += stepSize) {
                for (int x = 0; x < w; x += stepSize) {
                    double a = sample(x, y);
                    double b = sample(x + stepSize, y);
                    double c = sample(x, y + stepSize);
                    double d = sample(x + stepSize, y + stepSize);

                    double e = (a + b + c + d) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale;
                    setSample(x + halfStep, y + halfStep, e);
                }
            }
            for (int y = 0; y < w; y += stepSize) {
                for (int x = 0; x < w; x += stepSize) {
                    double a = sample(x, y);
                    double b = sample(x + stepSize, y);
                    double c = sample(x, y + stepSize);
                    double d = sample(x + halfStep, y + halfStep);
                    double e = sample(x + halfStep, y - halfStep);
                    double f = sample(x - halfStep, y + halfStep);

                    double H = (a + b + d + e) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
                    double g = (a + c + d + f) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
                    setSample(x + halfStep, y, H);
                    setSample(x, y + halfStep, g);
                }
            }
            stepSize /= 2;
            scale *= (scaleMod + 0.8);
            scaleMod *= 0.3;
        } while (stepSize > 1);
    }
    
    private double sample(int x, int y) {
        return values[(x & (w - 1)) + (y & (h - 1)) * w];
    }

    private void setSample(int x, int y, double value) {
        values[(x & (w - 1)) + (y & (h - 1)) * w] = value;
    }
    
    static double[] createMap2(int w, int h) {
        DiamondSquare mnoise1 = new DiamondSquare(w, h, 16);
        DiamondSquare mnoise2 = new DiamondSquare(w, h, 16);
        DiamondSquare mnoise3 = new DiamondSquare(w, h, 16);
    
        DiamondSquare noise1 = new DiamondSquare(w, h, 32);
        DiamondSquare noise2 = new DiamondSquare(w, h, 32);
    
        double [] finalValues = new double[w * h];
        
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = x + y * w;
    
                double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;
                double mval = Math.abs(mnoise1.values[i] - mnoise2.values[i]);
                mval = Math.abs(mval - mnoise3.values[i]) * 3 - 2;
    
                double xd = x / (w - 1.0) * 2 - 1;
                double yd = y / (h - 1.0) * 2 - 1;
                if (xd < 0) xd = -xd;
                if (yd < 0) yd = -yd;
                double dist = xd >= yd ? xd : yd;
                dist = dist * dist * dist * dist;
                dist = dist * dist * dist * dist;
                val = val + 1 - dist * 20;
                
                if (val < -0.5) {
                    finalValues[i] = 90;
                } else if (val > 0.5 && mval < -1.5) {
                    finalValues[i] = 126;
                } else {
                    finalValues[i] = 140;
                }
                finalValues[i] = val;
            }
        }
        return finalValues;
    }
    static double[] createMap1(int w, int h) {
        DiamondSquare noise1 = new DiamondSquare(w, h, 16);
        DiamondSquare noise2 = new DiamondSquare(w, h, 16);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = x + y * w;
                
                double val = (noise1.values[i] - noise2.values[i]) * 3;
                noise1.values[i] = val;
            }
        }
        return noise1.values;
    }
    static public int[][] createMap3(int w, int h) {
        DiamondSquare noise1 = new DiamondSquare(w, h, 16);
        int[][] map = new int[w][h];
        
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = x + y * w;
                int tile;
                double val = noise1.values[i];
                
                if(val < -0.25)
                    tile = 2;
                else
                    tile = 0;
                
                map[x][y] = tile;
            }
        }
        return map;
    }
}
