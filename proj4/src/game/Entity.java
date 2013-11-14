package game;

public class Entity {
    Sprite sprite;
    int x;
    int y;
    float dx;
    float dy;
    double angle;
    float dAngle;
    int delta;
    Game game;
    PlayerPlanet owner;
    boolean alive = true;
    
    Entity(int posX, int posY, Game inGame){
        x = posX;
        y = posY;
        dx = dy = 0;
        game = inGame;
    }
    
    public void addSprite(String fileName, int inRow, int inCol, int numRows, int numCols){
        sprite = new Sprite(this, fileName, inRow, inCol, numRows, numCols);
    }
    
    public void draw(){
        sprite.draw();
    }
    
    public void update(int delta){
        move(delta);
    }
    
    public void move(int delta){
        dx = (delta * dx) / 100;
        dy = (delta * dy) / 100;
        x += dx;
        y += dy;
    }
}
