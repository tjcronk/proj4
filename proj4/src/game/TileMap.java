package game;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import static java.lang.System.out;

public class TileMap {
    Texture texture;
    int x;
    int y;
    int dx;
    int dy;
    float numRows;
    float numCols;
    float rowIncrement;
    float colIncrement;
    int numTiles;

    int[][] map;

    TileMap() {
        try {
            // load texture from PNG file
            texture = TextureLoader.getTexture("PNG",
                    ResourceLoader.getResourceAsStream("res/terrain.png"));

            System.out.println("Texture loaded: " + texture);
            System.out.println(">> Image width: " + texture.getImageWidth());
            System.out.println(">> Image height: " + texture.getImageHeight());
            System.out
                    .println(">> Texture width: " + texture.getTextureWidth());
            System.out.println(">> Texture height: "
                    + texture.getTextureHeight());
            System.out.println(">> Texture ID: " + texture.getTextureID());
        } catch (IOException e) {
            e.printStackTrace();
        }
        texture.setTextureFilter(GL_NEAREST);
        numRows = texture.getTextureHeight() / 64;
        numCols = texture.getTextureWidth() / 64;
        rowIncrement = 1f / numRows;
        colIncrement = 1f / numCols;

        x = 0;
        y = 0;
    }

    void setMap(int[][] inMap) {
        map = inMap;
    }

    public void update(int delta) {
        move(delta);
    }

    public void move(int delta) {
        x += (delta * dx) / 100;
        y += (delta * dy) / 100;
    }

    public void draw() {
        Color.white.bind();
        texture.bind();
        float row = 0;
        float col = 0;
        for(int i = 0; i < map.length; ++i){
            for(int j = 0; j < map[0].length; ++j){
                switch(map[i][j]){
                    case 0: row = 0;
                            col = 0;
                            break;
                    case 1: row = 0;
                            col = 1;
                            break;
                    case 2: row = 1;
                            col = 0;
                            break;
                    case 3: row = 1;
                            col = 1;
                            break;
                }
                float factor = 2f;
                float x1 = (x + 64 * i) * factor;
                float y1 = (y + 64 * j) * factor;
                float x2 = x1 + texture.getTextureWidth() * colIncrement * factor;
                float y2 = y1 + texture.getTextureHeight() * rowIncrement * factor;
                float colCoord = col / numCols;
                float rowCoord = row / numRows;
                
                
                GL11.glBegin(GL11.GL_QUADS);

                GL11.glTexCoord2f(colCoord, rowCoord);
                GL11.glVertex2f(x1, y1);
        
                GL11.glTexCoord2f(colCoord + colIncrement, rowCoord);
                GL11.glVertex2f(x2, y1);
        
                GL11.glTexCoord2f(colCoord + colIncrement, rowCoord + rowIncrement);
                GL11.glVertex2f(x2, y2);
        
                GL11.glTexCoord2f(colCoord, rowCoord + rowIncrement);
                GL11.glVertex2f(x1, y2);
        
                GL11.glEnd();
            }
        }
        
        

    }

}
