package test;

import static java.lang.System.out;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;

public class GenerationTest {
    public static void main(String[] argv) {
        int w = 512;
        int h = 512;
        int blackandwhite = BufferedImage.TYPE_BYTE_GRAY;
        int colored = BufferedImage.TYPE_4BYTE_ABGR;
        BufferedImage image = new BufferedImage(w,h,colored);
        
        int result;
        double sub;
        
        int allColors[] = { 0xff191970, 0xff0000cd,0xddF0E68C,0xff98FB98,0xff98FB98,0xff228B22,0xff006400}; // etc
        int min= -1;
        int max= 1;
        int color = 0;
        //SimplexNoise noise = new SimplexNoise();
        DiamondSquare gen = new DiamondSquare(w,h,16);
        double [] values = gen.values;//DiamondSquare.createMap1(w,h);
        for(int i = 0; i < h; i += 1){
            for(int j = 0; j < w; j+= 1){
                sub = (values[i*w + j]);
                result = (int)((sub + 1)*128);
                //int index = (int)(6 * (sub - min) / (max - min));
                //int color = allColors[index];
                //if(result > 200)
                /* //   color = 0xff000000 | (result << 16) | (result << 8) | result;
                if(result >  100){
                    //result = 255-result+64;
                    color = 0xff220022 | (result << 8);
                }
                else if(result > 96)
                    color = 0xffF0E68C;
                else{// if(result < 96)
                    result = result+20;
                    color = 0xff000000 | result;
                }*/
                if(values[i*w + j] < -.1)
                    color = 0xff2222ff;
                else
                    color = 0xff22ff22;    
                
                
                //else
                 //   color = 0xff000000 | (result << 16) | (result << 8) | result; 
                image.setRGB(j,i,color);
                
            }
            //out.println();
        }  
        File outputfile = new File("image.png");
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
