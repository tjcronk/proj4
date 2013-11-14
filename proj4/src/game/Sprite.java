package game;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sprite {
    Texture texture;
    int counterDelta = 0;
    int counter = 0;
    float rowRatio;
    float colRatio;
    float rowIncrement;
    float colIncrement;
    Entity myEntity;

    Sprite(Entity inEntity, String fileName, int inRow, int inCol, int numRows, int numCols) {
        try {
            // load texture from PNG file
            texture = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("res/" + fileName));

            System.out.println("Texture loaded: " + texture);
            System.out.println(">> Image width: " + texture.getImageWidth());
            System.out.println(">> Image height: " + texture.getImageHeight());
            System.out.println(">> Texture width: " + texture.getTextureWidth());
            System.out.println(">> Texture height: "+ texture.getTextureHeight());
            System.out.println(">> Texture ID: " + texture.getTextureID());
        } catch (IOException e) {
            e.printStackTrace();
        }
        texture.setTextureFilter(GL_NEAREST);
        
        rowRatio = (float)inRow/numRows;
        colRatio = (float)inCol/numCols;
        rowIncrement = (float)1/numRows;
        colIncrement = (float)1/numCols;
        
        myEntity = inEntity;
    }
    
    public void update(int delta) {
        counterDelta += delta;
        if(counterDelta > 100){
            counter++;
            counterDelta = 0;
        }
        if (counter == 8)
            counter = 0;
    }
    
    public void draw() {
        GL11.glPushMatrix();
        Color.white.bind();
        texture.bind();
        GL11.glTranslatef(myEntity.x+64, myEntity.y+64, 0);
        GL11.glRotatef((float)-myEntity.angle, 0f, 0f, 1f);
        GL11.glTranslatef(-myEntity.x-64, -myEntity.y-64, 0);
        
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(colRatio, rowRatio);
        GL11.glVertex2f(myEntity.x, myEntity.y);

        GL11.glTexCoord2f(colRatio + colIncrement, rowRatio);
        GL11.glVertex2f(myEntity.x + texture.getTextureWidth() * colIncrement * 2, myEntity.y);

        GL11.glTexCoord2f(colRatio + colIncrement, rowRatio + rowIncrement);
        GL11.glVertex2f(myEntity.x + texture.getTextureWidth() * colIncrement * 2, myEntity.y + texture.getTextureHeight() * rowIncrement * 2);

        GL11.glTexCoord2f(colRatio, rowRatio + rowIncrement);
        GL11.glVertex2f(myEntity.x, myEntity.y + texture.getTextureHeight() * rowIncrement * 2);

        GL11.glEnd();
        GL11.glPopMatrix(); 
    }
    
    public void drawOffset(){
        GL11.glPushMatrix();
        Color.white.bind();
        texture.bind();
        GL11.glTranslatef(myEntity.owner.x+64, myEntity.owner.y+64, 0);
        GL11.glRotatef((float)-myEntity.angle, 0f, 0f, 1f);
        GL11.glTranslatef(-myEntity.x+17, -myEntity.y+12, 0);
        
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(colRatio, rowRatio);
        GL11.glVertex2f(myEntity.x, myEntity.y);

        GL11.glTexCoord2f(colRatio + colIncrement, rowRatio);
        GL11.glVertex2f(myEntity.x + texture.getTextureWidth() * colIncrement * 2, myEntity.y);

        GL11.glTexCoord2f(colRatio + colIncrement, rowRatio + rowIncrement);
        GL11.glVertex2f(myEntity.x + texture.getTextureWidth() * colIncrement * 2, myEntity.y + texture.getTextureHeight() * rowIncrement * 2);

        GL11.glTexCoord2f(colRatio, rowRatio + rowIncrement);
        GL11.glVertex2f(myEntity.x, myEntity.y + texture.getTextureHeight() * rowIncrement * 2);

        GL11.glEnd();
        GL11.glPopMatrix(); 
    }
}
