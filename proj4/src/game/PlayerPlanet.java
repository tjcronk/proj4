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
        double mouseAngle = Math.toDegrees(Math.atan2((inY - 300) , (inX - 400)));
        
        float angleDiff = (float) Math.abs(mouseAngle - angle);
        if(angleDiff > 180){
            if(mouseAngle > 0)
                dAngle = -(360 - angleDiff);
            else
                dAngle = (360 - angleDiff);
        }
        else if (angleDiff < 1.5 && angleDiff > 0)
            dAngle = 0;
        else if(angle > 0){
            if(mouseAngle > 0){
                if(mouseAngle > angle)
                    dAngle = angleDiff;
                else
                    dAngle = -angleDiff;    
            }
            else if(mouseAngle < 0){
                if(mouseAngle > angle-180)
                    dAngle = -angleDiff;
                else
                    dAngle = angleDiff;
            }
        }
        else if(angle <= 0){
            if(mouseAngle > 0){
                if(mouseAngle > angle+180)
                    dAngle = -angleDiff;
                else
                    dAngle = angleDiff;    
            }
            else if(mouseAngle < 0){
                if(mouseAngle > angle)
                    dAngle = angleDiff;
                else
                    dAngle = -angleDiff;
            }
        }
        else
            dAngle = 0;
    }
    
    
}
