package test;

import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
//import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public class AnimationExample {
    int screenWidth = 800;
    int screenHeight = 600;
    long lastFrame;
    int fps;
    long lastFPS;
    Texture texture;
    int counter = 0;
    int counterDelta = 0;
    int pos = 0;
    
    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        //Display.setVSyncEnabled(true);
        initGL(); // init OpenGL
        initTexture();
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer
        
        while (!Display.isCloseRequested()) {
            int delta = getDelta();

            update(delta);
            renderGL();

            Display.update();
            Display.sync(60); // cap fps to 60fps
        }
        
        Display.destroy();
    }
    
    public void update(int delta) {
        counterDelta += delta;
        if(counterDelta > 100){
            counter++;
            counterDelta = 0;
        }
        if (counter == 8)
            counter = 0;
        
        pos += 5;
        if(pos > 800)
            pos = -32;
        
        updateFPS();
    }
    
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
    
    public void initTexture() {
        try {
            // load texture from PNG file
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/DaltonWalk.png"));
        
            System.out.println("Texture loaded: "+texture);
            System.out.println(">> Image width: "+texture.getImageWidth());
            System.out.println(">> Image height: "+texture.getImageHeight());
            System.out.println(">> Texture width: "+texture.getTextureWidth());
            System.out.println(">> Texture height: "+texture.getTextureHeight());
            System.out.println(">> Texture ID: "+texture.getTextureID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void initGL() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);               
        
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
        
            // enable alpha blending
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
            GL11.glViewport(0,0,screenWidth,screenHeight);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        
        
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        glOrtho(0, screenWidth, screenHeight, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
    }
    
    public void renderGL() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        Color.white.bind();
        texture.bind();
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        // draw quad
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0.125f*counter,0);
            GL11.glVertex2f(pos,100);
            
            GL11.glTexCoord2f(0.125f + 0.125f*counter,0);
            GL11.glVertex2f(pos+texture.getTextureWidth()*0.125f*5,100);
            
            GL11.glTexCoord2f(0.125f + 0.125f*counter,1);
            GL11.glVertex2f(pos+texture.getTextureWidth()*0.125f*5,100+texture.getTextureHeight()*5);
            
            GL11.glTexCoord2f(0.125f*counter,1);
            GL11.glVertex2f(pos,100+texture.getTextureHeight()*5);
            
        GL11.glEnd();
    }
    
    public static void main(String[] argv) {
        AnimationExample AnimationExample = new AnimationExample();
        AnimationExample.start();
    }
}
