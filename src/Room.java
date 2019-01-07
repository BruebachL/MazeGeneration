public class Room {
    int xPosition;
    int yPosition;
    int height;
    int width;
    int doors;
    boolean placed=false;

    public Room(int xPos, int yPos, int h, int w, int d){
        this.xPosition=xPos;
        this.yPosition=yPos;
        this.height=h;
        this.width=w;
        this.doors=d;
    }
    public void placeRoom(int[][] field){
        for(int i=0;i<width+1;i++){
            field[yPosition][xPosition+i] = 1;
            field[yPosition+height][xPosition+i] = 1;
        }
        for(int i=0;i<height+1;i++){
            field[yPosition][xPosition] = 1;
            field[yPosition][xPosition+width] = 1;
            field[yPosition+i][xPosition] = 1;
            field[yPosition+i][xPosition+width] = 1;
        }
        placed=true;
    }
}
