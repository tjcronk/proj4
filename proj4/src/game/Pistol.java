package game;

public class Pistol extends Weapon{
    
    
    Pistol(int posX, int posY, Game inGame, PlayerPlanet player){
        super(posX, posY, inGame);
        owner = player;
        angle = player.angle;
        addSprite("pistol.png", 0, 0, 1, 1);
    }
    void fire(double angle){
        Bullet bullet = new Bullet(x+32,y+32,game,angle,1000);
        bullet.addSprite("bullet.png", 0, 0, 1, 1);

        game.entities.add(bullet);
    }
    void reload(){
        
    }
    
    public void update(int delta){
        move(delta);
        angle = owner.angle;
    }
}
