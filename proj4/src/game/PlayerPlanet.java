package game;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class PlayerPlanet extends Entity{
    
    int count = 0;
    
    PlayerPlanet(int posX, int posY){
        super(posX, posY);
        addSprite("human_soldier_all.png", 0, 0, 1, 8);
         
       // Color.white.bind();
       // sprite.texture.bind();

        //GL11.glTranslatef(x+64, y+64, 0);
        //GL11.glRotatef(-90, 0f, 0f, 1f);
        //GL11.glTranslatef(-x-64, -y-64, 0);

        angle = -90;
        dAngle = 0;
    }
    
    public void draw(){
        //GL11.glMatrixMode(GL11.GL_MODELVIEW);
        //GL11.glLoadIdentity();
        GL11.glPushMatrix();
        sprite.draw();
        GL11.glPopMatrix();
        //GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
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
            
        
        /*double angleDiff = (mouseAngle - angle);
        if (angleDiff < 5 && angleDiff > -5)
            dAngle = 0;
        else if(mouseAngle > angle){
            if(angleDiff < 180)
                dAngle = 5;
            else
                dAngle = -5;
        }
        else if(mouseAngle < angle){
            if(angleDiff < 180)
                dAngle = -5;
            else
                dAngle = 5;
        }
        else{
            dAngle = 0;
            angle = mouseAngle;
        }*/
        //angle = mouseAngle;
        //count++;
        //if(count > 30){
            //System.out.println(angle + " " + mouseAngle + " " + angleDiff + " " + dAngle);
          //  count = 0;
        //}
    }
    
    
}
