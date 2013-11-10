package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.Sys;

import Voronoi.GraphEdge;
import Voronoi.Voronoi;
import static java.lang.System.out;

public class VoronoiTest {
    public static void main(String[] argv) {
        int w = 1000;
        int h = 1000;
        BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphic = image.createGraphics();
        Random generator = new Random(Sys.getTime());
        
        Voronoi gen = new Voronoi(0.000001f);
        
        int numOfPoints = 300;
        
        double []x = new double[numOfPoints];
        double []y = new double[numOfPoints];
        for(int i = 0; i < numOfPoints; i++){
            x[i] = generator.nextInt(w);
            y[i] = generator.nextInt(h);
        }
        
        List<GraphEdge> map = gen.generateVoronoi(x,y,0,w,0,h);
        
        out.println(map.size());
        graphic.setColor(Color.WHITE);
        for(GraphEdge e : map){
            out.println("x1: " + e.x1 + " y1: " + e.y1);
            out.println("x2: " + e.x2 + " y2: " + e.y2);
            
            graphic.drawLine((int)e.x1,(int)e.y1,(int)e.x2,(int)e.y2);
            //image.setRGB(e.site1*5,e.site2*5,0xffffffff);
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
