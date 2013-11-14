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
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;
import static java.lang.System.out;

public class Game {
    public int screenWidth = 800;
    public int screenHeight = 600;
    long lastFrame;
    int fps;
    long lastFPS;
    boolean running = true;

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
         // init OpenGL

        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        initGame();
        initGL();

        while (!Display.isCloseRequested() && running) {
            int delta = getDelta();

            update(delta);
            renderGL();

            Display.update();
            Display.sync(60); // cap fps to 60fps
        }

        Display.destroy();
    }

    public void initGame() {
        player = new PlayerPlanet(screenWidth / 2 - 64, screenHeight / 2 - 64,
                this);
        entities.add(player);
        Weapon weapon = new Pistol(player.x,player.y,this,player);
        entities.add(weapon);
        player.setWeapon(weapon);
        map = new TileMap(player);
        /*
         * int [][] temp = {{0,0,1,0,2,2}, {1,1,1,1,2,2}, {2,2,2,2,2,2},
         * {3,3,3,3,2,2}, {3,2,1,0,2,2}, {1,1,1,1,2,2}, {1,1,1,1,2,2},
         * {1,1,1,1,2,2}};
         */
        int[][] temp = DiamondSquare.createMap3(64, 64);
        map.setMap(temp);

        // Mouse.setGrabbed(false);
    }

    public void update(int delta) {
        

        player.updateMousePos(Mouse.getX(), Mouse.getY());

        boolean wPressed = Keyboard.isKeyDown(Keyboard.KEY_W);
        boolean aPressed = Keyboard.isKeyDown(Keyboard.KEY_A);
        boolean sPressed = Keyboard.isKeyDown(Keyboard.KEY_S);
        boolean dPressed = Keyboard.isKeyDown(Keyboard.KEY_D);
            
        float speed = 50;
        if (wPressed || aPressed || sPressed || dPressed) {
            if (wPressed && !sPressed) {
                player.dx = -speed;
                player.dy = -speed;
            } else if (sPressed) {
                player.dx = speed;
                player.dy = speed;
            }
            if (aPressed && !dPressed) {
                player.dx = speed;
                player.dy = speed;
                player.dAngle = -90;
            } else if (dPressed) {
                player.dx = speed;
                player.dy = speed;
                player.dAngle = 90;
            } else
                player.dAngle = 0;
        } else {
            player.dx = 0;
            player.dy = 0;
        }

        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) {
                if (Mouse.getEventButton() == 0)
                    player.fire();
            }
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                running = false;
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_F) {
                if (!Display.isFullscreen()) {
                    screenHeight = 1080;
                    screenWidth = 1920;
                    initGL();
                    player.x = screenWidth / 2 - 64;
                    player.y = screenHeight / 2 - 64;
                    player.weapon.x = player.x;
                    player.weapon.y = player.y;
                    setDisplayMode(screenWidth, screenHeight,
                            !Display.isFullscreen());
                } else {
                    screenHeight = 600;
                    screenWidth = 800;
                    initGL();
                    player.x = screenWidth / 2 - 64;
                    player.y = screenHeight / 2 - 64;
                    player.weapon.x = player.x;
                    player.weapon.y = player.y;
                    setDisplayMode(screenWidth, screenHeight,
                            !Display.isFullscreen());
                }

            }

        }
        for (int i = 0; i < entities.size(); ++i){
            entities.get(i).update(delta);
            if(!entities.get(i).alive){
                entities.remove(i);
                out.println("dead");
            }
        }

        map.update(delta);
        updateFPS();
    }

    public void setDisplayMode(int width, int height, boolean fullscreen) {

        // return if requested DisplayMode is already set
        if ((Display.getDisplayMode().getWidth() == width)
                && (Display.getDisplayMode().getHeight() == height)
                && (Display.isFullscreen() == fullscreen)) {
            return;
        }

        try {
            DisplayMode targetDisplayMode = null;

            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (int i = 0; i < modes.length; i++) {
                    DisplayMode current = modes[i];

                    if ((current.getWidth() == width)
                            && (current.getHeight() == height)) {
                        if ((targetDisplayMode == null)
                                || (current.getFrequency() >= freq)) {
                            if ((targetDisplayMode == null)
                                    || (current.getBitsPerPixel() > targetDisplayMode
                                            .getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        // if we've found a match for bpp and frequence against
                        // the
                        // original display mode then it's probably best to go
                        // for this one
                        // since it's most likely compatible with the monitor
                        if ((current.getBitsPerPixel() == Display
                                .getDesktopDisplayMode().getBitsPerPixel())
                                && (current.getFrequency() == Display
                                        .getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width, height);
            }

            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: " + width + "x"
                        + height + " fs=" + fullscreen);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);

        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "x" + height
                    + " fullscreen=" + fullscreen + e);
        }
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
        GL11.glLoadIdentity();
        glTranslatef(-player.x+screenWidth/2-64,-player.y+screenHeight/2-64,0.0f);
        
        map.draw();

        for (Entity entity : entities)
            entity.draw();
    }

    public static void main(String[] argv) {
        Game game = new Game();
        game.start();
    }
}
