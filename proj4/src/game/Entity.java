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
    
    Entity(int posX, int posY){
        x = posX;
        y = posY;
        dx = dy = 0;
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
        x += (delta * dx) / 100;
        y += (delta * dy) / 100;
        rotate(delta);
    }
    
    public void rotate(int delta){
        angle += dAngle;
        
        if(angle > 180)
            angle -= 360;
        else if(angle < -180)
            angle += 360;
        
    }
}
