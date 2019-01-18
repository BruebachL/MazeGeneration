class Room {
    int xPosition;
    int yPosition;
    int height;
    int width;
    private int doors;
    private int uID;
    private boolean placed=false;

    Room(int xPos, int yPos, int h, int w, int d, int uID){
        this.xPosition=xPos;
        this.yPosition=yPos;
        this.height=h;
        this.width=w;
        this.doors=d;
        this.uID = uID;
    }
    void placeRoom(int[][] field, int[][] color){
        for(int i=0;i<width+1;i++){
            field[yPosition][xPosition+i] = 1;
            field[yPosition+height][xPosition+i] = 1;
            color[yPosition][xPosition+i] = uID;
            color[yPosition+height][xPosition+i] = uID;
        }
        for(int i=0;i<height+1;i++){
            field[yPosition][xPosition] = 1;
            field[yPosition][xPosition+width] = 1;
            field[yPosition+i][xPosition] = 1;
            field[yPosition+i][xPosition+width] = 1;
            color[yPosition][xPosition] = uID;
            color[yPosition][xPosition+width] = uID;
            color[yPosition+i][xPosition] = uID;
            color[yPosition+i][xPosition+width] = uID;
            for(int cnt=0;cnt<width+1;cnt++){
                field[yPosition+i][xPosition+cnt] = 1;
                color[yPosition+i][xPosition+cnt] = uID;
            }
        }
        placed=true;
    }
    boolean checkRoom(int[][] field, int[][] color){
        if(xPosition+width>field[0].length-2||yPosition+height>field.length-2){
            return false;
        }
        for(int i=0;i<width+1;i++){
            if(field[yPosition][xPosition+i] == 1&&field[yPosition+height][xPosition+i] ==1){
                return false;
            }
        }
        for(int i=0;i<height+1;i++){
            if(field[yPosition][xPosition] == 1&&field[yPosition][xPosition+width] == 1&&field[yPosition+i][xPosition+width] == 1) {
                return false;
            }
            for(int cnt=0;cnt<width+1;cnt++){
                if(field[yPosition+i][xPosition+cnt] == 1){
                    return false;
                }
            }
        }
        return true;
    }
}
