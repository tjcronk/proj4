package game;

import java.util.ArrayList;
import test.DiamondSquare;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
//import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

//Dalton Test
//Testing commiting
//Test comment again
public class Game {
    int screenWidth = 800;
    int screenHeight = 600;
    long lastFrame;
    int fps;
    long lastFPS;

    ArrayList<Entity> entities = new ArrayList<Entity>();
    PlayerPlanet player;
    TileMap map;

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
         Display.setVSyncEnabled(true);
        initGL(); // init OpenGL

        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        initGame();

        while (!Display.isCloseRequested()) {
            int delta = getDelta();

            update(delta);
            renderGL();

            Display.update();
            Display.sync(60); // cap fps to 60fps
        }

        Display.destroy();
    }

    public void initGame() {
        player = new PlayerPlanet(screenWidth / 2 - 64, screenHeight / 2 - 64);
        entities.add(player);
        
        map = new TileMap();
        /*int [][] temp = {{0,0,1,0,2,2},
                         {1,1,1,1,2,2},
                         {2,2,2,2,2,2},
                         {3,3,3,3,2,2},
                         {3,2,1,0,2,2},
                         {1,1,1,1,2,2},
                         {1,1,1,1,2,2},
                         {1,1,1,1,2,2}};*/
        int [][] temp = DiamondSquare.createMap3(64,64);
        map.setMap(temp);
    }

    public void update(int delta) {
        for (Entity entity : entities)
            entity.update(delta);
        
        map.update(delta);
        
        player.updateMousePos(Mouse.getX(),Mouse.getY());
        
        boolean wPressed = Keyboard.isKeyDown(Keyboard.KEY_W);
        boolean aPressed = Keyboard.isKeyDown(Keyboard.KEY_A);
        boolean sPressed = Keyboard.isKeyDown(Keyboard.KEY_S);
        boolean dPressed = Keyboard.isKeyDown(Keyboard.KEY_D);
        
        if (wPressed && !sPressed) 
            map.dy = 40;
        else if (sPressed)
            map.dy = -40;
        else
            map.dy = 0;
        if (aPressed && !dPressed) 
            map.dx = 40;
        else if (dPressed)
            map.dx = -40;
        else
            map.dx = 0;


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

    public void initGL() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glViewport(0, 0, screenWidth, screenHeight);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        glOrtho(0, screenWidth, screenHeight, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

    }

    public void renderGL() {
        glClear(GL_COLOR_BUFFER_BIT);

        map.draw();

        for (Entity entity : entities)
            entity.draw(); 
    }

    public static void main(String[] argv) {
        Game game = new Game();
        game.start();
    }
}
