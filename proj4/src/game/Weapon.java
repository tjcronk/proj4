package game;

import static java.lang.System.out;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public abstract class Weapon extends Entity{
    //PlayerPlanet owner;
    
    Weapon(int posX, int posY, Game inGame){
        super(posX, posY, inGame);
    }
    abstract void fire(double angle);
    abstract void reload();
    
    public void move(int delta){
        x += owner.dx;
        y += owner.dy;
    }
    public void draw(){
        sprite.drawOffset();
    }
    
}   
