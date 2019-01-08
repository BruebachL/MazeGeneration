public class Cell {
    boolean visited = false;
    String[] walls = {"up","down","left","right"};
    int xPos;
    int yPos;

    public Cell(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public void move(){
        int randDirection = (int) Math.random()*4;

    }
}
