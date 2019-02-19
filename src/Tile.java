public class Tile {
    int xPosition;
    int yPosition;
    int f;
    int g;
    int h;
    Tile parent;

    public Tile (Tile end, Tile p, int x, int y){
        this.xPosition=x;
        this.yPosition=y;
        this.parent=p;
        this.g=p.g+1;
        this.h=manhattanDistance(end);
        this.f=g+h;
    }
    public Tile (int x, int y){
        this.xPosition=x;
        this.yPosition=y;
        this.f=0;
        this.g=0;
    }
    public void setH(Tile end){
        this.h=manhattanDistance(end);
        this.f=this.g+this.h;
    }
    public int manhattanDistance(Tile end){
        int xDistance = 0;
        if(end.xPosition>this.xPosition){
            xDistance = end.xPosition-this.xPosition;
        }else{
            xDistance = this.xPosition-end.xPosition;
        }
        int yDistance = 0;
        if(end.yPosition>this.yPosition){
            yDistance = end.yPosition-this.yPosition;
        }else{
            yDistance = this.yPosition-end.yPosition;
        }
        return xDistance+yDistance;
    }
}