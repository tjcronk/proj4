package test;

import java.util.Random;

public class Generator {
    
    private static final Random gen = new Random();
    
    public double[][] genMap(int h, int w, double freq, double zoom, double octaves) {      
        return perlinNoise(h, w, gen.nextDouble() * freq, zoom, octaves);
    }
    
    private double[][] perlinNoise(int height, int width, double freq, double zoom, double octaves) {
        
        double[][] array = new double[height][width];
        for(int i=0; i<height; i++) {
            array[i]= new double[width];
            for(int j=0; j<width; j++) {
                array[i][j]=0;
            }
        }

        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                for(int p=0; p<octaves; p++) {

                      double ofreq=Math.pow(freq, p);
                      int x=(int) (i*ofreq/zoom);
                      int y=(int) (j*ofreq/zoom);
    
                      double v1=smooth(x, y);
                      double v2=smooth(x+1, y);
                      double v3=smooth(x, y+1);
                      double v4=smooth(x+1, y+1);
    
                      double i1=cosineInterpolate(v1, v2, x-(int)x);
                      double i2=cosineInterpolate(v3, v4, x-(int)x);
                      array[i][j]+=cosineInterpolate(i1, i2, y-(int)y);
                }
            array[i][j]/=octaves;
            }
        }
        return array;
    }
    
    private double noise(int x, int y) {
        int n=(int)x*331+(int)y*337;
        n=(n<<13)^n;
        int nn=(n*(n*n*41333 +53307781)+1376312589)&0x7fffffff;
        
        return ((1.0-((double)nn/1073741824.0))+1)/2.0;
        
    }
    
    private double cosineInterpolate(double a, double b, double x) {
        double ft = x * 3.1415927;
        double f = ((1.0 - Math.cos(ft)) * 0.5);

        return  (a*(1.0-f) + b*f);
    }
    
    private double smooth(int i, int j) {
        
        return (noise(i,j)+noise(i-1, j)+noise(i+1, j)+noise(i,j+1)+noise(i,j-1))/5.0;
        
        /* - THIS PRODuCES SMALLER LINES BuT LESS DEFINITION
        double corners = (noise((int)x - 1, (int)y - 1) + noise((int)x + 1, (int)y - 1) + noise((int)x - 1, (int) y + 1) + noise((int)x + 1, (int)y + 1)) / 16;
        double sides = (noise((int) x - 1, (int)y) + noise((int)x + 1, (int)y) + noise((int)x, (int)y - 1) + noise((int)x, (int)y + 1)) / 8;
        double centre = noise((int)x, (int)y) / 4;
        
        return corners + sides + centre;
        */
    }
}