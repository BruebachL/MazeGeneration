import java.util.ArrayDeque;
import java.util.Deque;

    class Fireball {
        int xPosition;
        int yPosition;
        String heading = "north";
        boolean collided=false;
        private Deque<String> stack = new ArrayDeque<>();
        boolean exploding = false;

        void update(){
            if(!collided) {
                if (!collisionDetection()) {
                    decideWhichWay(MazeGenerator.field);
                } else {
                    this.xPosition = 999;
                    this.yPosition = 999;
                    collided=true;
                }
            }
        }
        private boolean collisionDetection(){
            for(Monster monster : MazeGenerator.monsterList){
                if(monster.xPosition==this.xPosition&&monster.yPosition==this.yPosition){
                    monster.health-=50;
                    if(exploding){
                        Fireball fireball = new Fireball(this.xPosition, this.yPosition-1, false);
                        fireball.heading="north";
                        MazeGenerator.fireballList.add(fireball);
                        Fireball fireball2 = new Fireball(this.xPosition, this.yPosition+1,false);
                        fireball2.heading="south";
                        MazeGenerator.fireballList.add(fireball2);
                        Fireball fireball3 = new Fireball(this.xPosition+1, this.yPosition,false);
                        fireball3.heading="east";
                        MazeGenerator.fireballList.add(fireball3);
                       Fireball fireball4 = new Fireball(this.xPosition-1, this.yPosition,false);
                        fireball4.heading="west";
                        MazeGenerator.fireballList.add(fireball4);
                    }
                    return true;
                }
            }
            if(MazeGenerator.player.xPosition==this.xPosition&&MazeGenerator.player.yPosition==this.yPosition){
                MazeGenerator.player.health-=50;
                if(exploding){
                    Fireball fireball = new Fireball(this.xPosition, this.yPosition-1, false);
                    fireball.heading="north";
                    MazeGenerator.fireballList.add(fireball);
                    Fireball fireball2 = new Fireball(this.xPosition, this.yPosition+1,false);
                    fireball2.heading="south";
                    MazeGenerator.fireballList.add(fireball2);
                    Fireball fireball3 = new Fireball(this.xPosition+1, this.yPosition,false);
                    fireball3.heading="east";
                    MazeGenerator.fireballList.add(fireball3);
                    Fireball fireball4 = new Fireball(this.xPosition-1, this.yPosition,false);
                    fireball4.heading="west";
                    MazeGenerator.fireballList.add(fireball4);
                }
                return true;
            }
            return false;
        }
        private void decideWhichWay(int[][] maze) {
            switch (heading) {
                case "north":
                    if (maze[yPosition - 1][xPosition] == 1) {
                        stack.add(this.xPosition + " " + this.yPosition);
                        this.yPosition--;
                    }
                    if (maze[yPosition - 1][xPosition] == 0) {
                        this.xPosition=999;
                        this.yPosition=999;
                        collided=true;
                    }
                    break;
                case "east":
                    if (maze[yPosition][xPosition + 1] == 1) {
                        stack.add(this.xPosition + " " + this.yPosition);
                        this.xPosition++;
                    }
                    if (maze[yPosition][xPosition + 1] == 0) {
                        this.xPosition=999;
                        this.yPosition=999;
                        collided=true;
                    }
                    break;
                case "south":
                    if (maze[yPosition + 1][xPosition] == 1) {
                        stack.add(this.xPosition + " " + this.yPosition);
                        this.yPosition++;
                    }
                    if (maze[yPosition + 1][xPosition] == 0) {
                        this.xPosition=999;
                        this.yPosition=999;
                        collided=true;
                    }
                    break;

                case "west":
                    if (maze[yPosition][xPosition - 1] == 1) {
                        stack.add(this.xPosition + " " + this.yPosition);
                        this.xPosition--;
                    }
                    if (maze[yPosition][xPosition -1] == 0) {
                        this.xPosition=999;
                        this.yPosition=999;
                        collided=true;
                    }
                    break;
            }
        }

        Fireball(int xPosition, int yPosition, boolean exploding) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.exploding=exploding;
        }
    }


