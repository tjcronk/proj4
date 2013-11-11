package game;

import org.lwjgl.opengl.GL11;

public class PlayerPlanet extends Entity{
    
    int count = 0;
    
    PlayerPlanet(int posX, int posY){
        super(posX, posY);
        addSprite("human_soldier_all.png", 0, 0, 1, 8);

        angle = -90;
        dAngle = 0;
    }
    
    public void draw(){
        GL11.glPushMatrix();
        sprite.draw();
        GL11.glPopMatrix();     
    }
    
    public void updateMousePos(int inX, int inY){
        angle = Math.toDegrees(Math.atan2((inY - 300) , (inX - 400)));
    }   
}
