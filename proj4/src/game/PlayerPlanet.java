package game;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL43;
import org.lwjgl.util.glu.*;

import static java.lang.System.out;

public class PlayerPlanet extends Entity{
    
    int count = 0;
    float x_gl = 0;
    float y_gl = 0;
    Weapon weapon;
    
    PlayerPlanet(int posX, int posY, Game inGame){
        super(posX, posY, inGame);
        addSprite("human_soldier_all.png", 0, 0, 1, 8);

        angle = -90;
        dAngle = 0;
    }
    
    public void setWeapon(Weapon inWeapon){
        weapon = inWeapon;
    }
    
    public void draw(){
        
       
        
        
        sprite.draw();
            
    }
    
    public void updateMousePos(int inX, int inY){
        angle = Math.toDegrees(Math.atan2((inY - game.screenHeight/2) , (inX - game.screenWidth/2)));
    } 
    
    public void move(int delta){
        dx *= -Math.cos(Math.toRadians(angle + dAngle));
        dy *= Math.sin(Math.toRadians(angle + dAngle));
        super.move(delta);
    }
    
    public void fire(){
        out.println("fire!");
        weapon.fire(angle);
    }
}
