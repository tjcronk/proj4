package game;

public class Bullet extends Entity{
    double angle;
    double range;
    double dis;
    public Bullet (int posX, int posY, Game inGame, double inAngle, double inRange){
        super(posX, posY, inGame);
        angle = inAngle;
        range = inRange;
        dis = 0;
    }
    
    public void update(int delta){
        dx = (float) (-Math.cos(Math.toRadians(angle))*-150);
        dy = (float) (Math.sin(Math.toRadians(angle))*-150);
        move(delta);
        dis += Math.sqrt(dx*dx + dy*dy);
        if(dis > range)
            alive = false;
    }
    public void draw(){
        sprite.draw();
    }
}
