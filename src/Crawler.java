import java.util.concurrent.TimeUnit;

public class Crawler {
    int xPos;
    int yPos;
    int dir;
    int uID;
    int hasChangedDir = 0;
    int maxSteps = 150;
    int stepsTaken = 0;
    int furthestX = 0;
    int furthestY = 0;
    public Crawler(int x, int y, int dir, int id){
        this.xPos=x;
        this.yPos=y;
        this.dir=dir;
        this.uID = id;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }
    public void increaseuID(){
        this.uID++;
    }
    public int getuID(){
        return this.uID;
    }

    public void move(int[][] field, int[][]color){
        int rand = (int)Math.floor(Math.random() * Math.floor(4));
        int doesItChange = (int)Math.floor(Math.random() * Math.floor(11));
        if(doesItChange>3){
            dir=rand;
        }
        if(hasChangedDir==20){
            boolean validBranch = false;
            while(!validBranch) {
                this.xPos = ((int) Math.floor(Math.random() * (Math.floor(MazeGenerator.mazeWidth-2)+1)));
                this.yPos = ((int) Math.floor(Math.random() * (Math.floor(MazeGenerator.mazeHeight-2)+1)));
                hasChangedDir = 0;
                if(field[this.yPos][this.xPos]==1&&color[this.yPos][this.xPos]==1){
                    validBranch=true;
                    this.move(field,color);
                }
            }
            stepsTaken=0;
        }else {
            switch (dir) {
                case 0:
                    if (xPos + 3 <= field[0].length-1 && yPos - 3 >= 0 && yPos + 3 <= field.length) {
                        if (field[yPos][xPos + 1] == 0 && field[yPos][xPos + 2] != 1 && field[yPos + 1][xPos + 1] != 1 && field[yPos - 1][xPos + 1] != 1 && field[yPos + 1][xPos + 2] != 1 && field[yPos - 1][xPos + 2] != 1) {
                            field[yPos][xPos + 1] = 1;
                            if(this.xPos+1<field[0].length) {
                                this.xPos++;
                            }
                            if(this.xPos>furthestX){
                                furthestX=this.xPos;
                            }
                            if(this.yPos>furthestY){
                                furthestY=this.yPos;
                            }
                            color[yPos][xPos]=this.uID;
                            hasChangedDir = 0;
                            break;
                        } else {
                            hasChangedDir++;
                            dir = 1;
                            break;
                        }
                    }
                case 1:
                    if (yPos + 3 <= field.length && xPos + 3 <= field[0].length) {
                        if (field[yPos + 1][xPos] == 0 && field[yPos + 2][xPos] != 1 && field[yPos + 1][xPos + 1] != 1 && field[yPos + 1][xPos - 1] != 1 && field[yPos + 2][xPos + 1] != 1 && field[yPos + 2][xPos - 1] != 1) {
                            field[yPos + 1][xPos] = 1;
                            this.yPos++;
                            if(this.xPos>furthestX){
                                furthestX=this.xPos;
                            }
                            if(this.yPos>furthestY){
                                furthestY=this.yPos;
                            }
                            color[yPos][xPos]=this.uID;
                            hasChangedDir = 0;
                            break;

                        } else {
                            dir = 2;
                            hasChangedDir++;
                            break;
                        }
                    }
                case 2:
                    if (xPos - 3 >= 1&&yPos-3>=1&&yPos + 3 <= field.length) {
                        if (field[yPos][xPos - 1] == 0 && field[yPos][xPos - 2] != 1 && field[yPos + 1][xPos - 1] != 1 && field[yPos - 1][xPos - 1] != 1 && field[yPos + 1][xPos - 2] != 1 && field[yPos - 1][xPos - 2] != 1) {
                            field[yPos][xPos - 1] = 1;
                            this.xPos--;
                            if(this.xPos>furthestX){
                                furthestX=this.xPos;
                            }
                            if(this.yPos>furthestY){
                                furthestY=this.yPos;
                            }
                            color[yPos][xPos]=this.uID;
                            hasChangedDir = 0;
                            break;

                        } else {
                            dir = 3;
                            hasChangedDir++;
                            break;
                        }
                    }
                case 3:
                    if (yPos - 3 >= 0&&xPos-3>=0) {
                        if (field[yPos - 1][xPos] == 0 && field[yPos - 2][xPos] != 1 && field[yPos - 1][xPos + 1] != 1 && field[yPos - 1][xPos - 1] != 1 && field[yPos - 2][xPos + 1] != 1 && field[yPos - 2][xPos - 1] != 1) {
                            field[yPos - 1][xPos] = 1;
                            this.yPos--;
                            if(this.xPos>furthestX){
                                furthestX=this.xPos;
                            }
                            if(this.yPos>furthestY){
                                furthestY=this.yPos;
                            }
                            color[yPos][xPos]=this.uID;
                            hasChangedDir = 0;
                            break;
                        } else {
                            dir = 0;
                            hasChangedDir++;
                            break;
                        }
                    }

            }
        }
        if(stepsTaken==maxSteps){
            hasChangedDir=20;
            stepsTaken=0;
        }else{
            stepsTaken++;
        }
    }
    public void removeLoops (int[][] field,int[][] color, int y, int x,int startUID){
        boolean sameRoom = false;
        boolean differentRoom = false;
        while(color[y][x]!=startUID) {
            if (field[y - 1][x] == 1) {
                y--;
            } else {
                if (field[y + 1][x] == 1) {
                    y++;
                } else {
                    if (field[y][x + 1] == 1) {
                        x++;
                    } else {
                        if (field[y][x - 1] == 1) {
                            x--;
                        }
                    }
                }
            }
        }
    }
    public void removeDeadEnds(int[][]field, int y, int x) throws InterruptedException {
        while(checkWalls(field,y,x)==3) {
            if (field[y - 1][x] == 1) {
                field[y][x] = 0;
                y--;
            } else {
                if (field[y + 1][x] == 1) {
                    field[y][x] = 0;
                    y++;
                } else {
                    if (field[y][x + 1] == 1) {
                        field[y][x] = 0;
                        x++;
                    } else {
                        if (field[y][x - 1] == 1) {
                            field[y][x] = 0;
                            x--;
                        }
                    }
                }
            }
        }
    }
    public int checkWalls(int[][] field,int y, int x){
        int walls = 0;
        if (field[y - 1][x] == 0 ){
            walls++;
        }
        if (field[y + 1][x] == 0 ){
            walls++;
        }
        if (field[y][x+1] == 0 ){
            walls++;
        }
        if (field[y][x-1] == 0 ){
            walls++;
        }
        return walls;
    }
}
