public class Crawler {
    int xPos;
    int yPos;
    int dir;
    int uID;
    int hasChangedDir = 0;
    public Crawler(int x, int y, int dir, int id){
        this.xPos=x;
        this.yPos=y;
        this.dir=dir;
        this.uID = id;
    }
    public void move(int[][] field,int[][]color){
        int rand = (int)Math.floor(Math.random() * Math.floor(4));
        int doesItChange = (int)Math.floor(Math.random() * Math.floor(11));
        if(doesItChange>1){
            dir=rand;
        }
        if(hasChangedDir==20){
            boolean validBranch = false;
            while(!validBranch) {
                this.xPos = (int) Math.floor(Math.random() * Math.floor(40));
                this.yPos = (int) Math.floor(Math.random() * Math.floor(18));
                hasChangedDir = 0;
                if(field[this.yPos][this.xPos]==1){
                    validBranch=true;
                }
            }
        }else {
            switch (dir) {
                case 0:
                    if (xPos + 2 <= field.length - 1 && yPos - 1 >= 0) {
                        if (field[yPos][xPos + 1] == 0 && field[yPos][xPos + 2] != 1 && field[yPos + 1][xPos + 1] != 1 && field[yPos - 1][xPos + 1] != 1 && field[yPos + 1][xPos + 2] != 1 && field[yPos - 1][xPos + 2] != 1) {
                            field[yPos][xPos + 1] = 1;
                            this.xPos++;
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
                    if (yPos + 2 <= field.length - 1 && xPos - 1 >= 0) {
                        if (field[yPos + 1][xPos] == 0 && field[yPos + 2][xPos] != 1 && field[yPos + 1][xPos + 1] != 1 && field[yPos + 1][xPos - 1] != 1 && field[yPos + 2][xPos + 1] != 1 && field[yPos + 2][xPos - 1] != 1) {
                            field[yPos + 1][xPos] = 1;
                            this.yPos++;
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
                    if (xPos - 1 >= 1&&yPos-1>=0) {
                        if (field[yPos][xPos - 1] == 0 && field[yPos][xPos - 2] != 1 && field[yPos + 1][xPos - 1] != 1 && field[yPos - 1][xPos - 1] != 1 && field[yPos + 1][xPos - 2] != 1 && field[yPos - 1][xPos - 2] != 1) {
                            field[yPos][xPos - 1] = 1;
                            this.xPos--;
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
                    if (yPos - 2 >= 1&&xPos-1>=0) {
                        if (field[yPos - 1][xPos] == 0 && field[yPos - 2][xPos] != 1 && field[yPos - 1][xPos + 1] != 1 && field[yPos - 1][xPos - 1] != 1 && field[yPos - 2][xPos + 1] != 1 && field[yPos - 2][xPos - 1] != 1) {
                            field[yPos - 1][xPos] = 1;
                            this.yPos--;
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
    }
}
