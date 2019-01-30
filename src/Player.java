import java.util.ArrayDeque;
import java.util.Deque;

import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

class Player {
    int xPosition;
    int yPosition;
    String heading = "north";
    boolean turnTaken = false;
    private Deque<String> stack = new ArrayDeque<>();
    int health = 100;
    int energy = 0;
    void update() throws InterruptedException {
        if(energy<100){
            energy+=25;
        }
    }

    void keyPressed(KeyEvent e, int[][] maze) throws InterruptedException {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            if(energy>=100) {
                this.heading = "west";
                if (!collisionDetection()) {
                    this.decideWhichWay(maze);
                } else {
                    this.swingSword();
                }
                MazeGenerator.repaintWindow();
                this.energy=0;
                turnTaken = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if(energy>=100) {
                this.heading = "east";
                if (!collisionDetection()) {
                    this.decideWhichWay(maze);
                } else {
                    this.swingSword();
                }
                MazeGenerator.repaintWindow();
                this.energy=0;
                turnTaken = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if(energy>=100) {
                this.heading = "north";
                if (!collisionDetection()) {
                    this.decideWhichWay(maze);
                } else {
                    this.swingSword();
                }
                MazeGenerator.repaintWindow();
                this.energy=0;
                turnTaken = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if(energy>=100) {
                this.heading = "south";
                if (!collisionDetection()) {
                    this.decideWhichWay(maze);
                } else {
                    this.swingSword();
                }
                MazeGenerator.repaintWindow();
                this.energy = 0;
                turnTaken = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_T) {
            switch (heading) {
                case "north":
                    Fireball fireball = new Fireball(this.xPosition, this.yPosition - 1,false);
                    fireball.heading = this.heading;
                    MazeGenerator.fireballList.add(fireball);
                    break;
                case "east":
                    Fireball fireball2 = new Fireball(this.xPosition + 1, this.yPosition,false);
                    fireball2.heading = this.heading;
                    MazeGenerator.fireballList.add(fireball2);
                    break;
                case "south":
                    Fireball fireball3 = new Fireball(this.xPosition, this.yPosition + 1,false);
                    fireball3.heading = this.heading;
                    MazeGenerator.fireballList.add(fireball3);
                    break;
                case "west":
                    Fireball fireball4 = new Fireball(this.xPosition - 1, this.yPosition,false);
                    fireball4.heading = this.heading;
                    MazeGenerator.fireballList.add(fireball4);
                    break;
            }
            turnTaken = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            switch (heading) {
                case "north":
                    Fireball fireball = new Fireball(this.xPosition, this.yPosition - 1,true);
                    fireball.heading = this.heading;
                    MazeGenerator.fireballList.add(fireball);
                    break;
                case "east":
                    Fireball fireball2 = new Fireball(this.xPosition + 1, this.yPosition,true);
                    fireball2.heading = this.heading;
                    MazeGenerator.fireballList.add(fireball2);
                    break;
                case "south":
                    Fireball fireball3 = new Fireball(this.xPosition, this.yPosition + 1,true);
                    fireball3.heading = this.heading;
                    MazeGenerator.fireballList.add(fireball3);
                    break;
                case "west":
                    Fireball fireball4 = new Fireball(this.xPosition - 1, this.yPosition,true);
                    fireball4.heading = this.heading;
                    MazeGenerator.fireballList.add(fireball4);
                    break;
            }
            turnTaken = true;
        }
    }

    private boolean collisionDetection(){
        for(Monster monster : MazeGenerator.monsterList){
            switch (heading){
                case "north":
                    if(monster.xPosition==this.xPosition&&monster.yPosition==this.yPosition-1){
                        return true;
                    }
                    break;
                case "south":
                    if(monster.xPosition==this.xPosition&&monster.yPosition==this.yPosition+1){
                        return true;
                    }
                case "east":
                    if(monster.xPosition==this.xPosition+1&&monster.yPosition==this.yPosition){
                        return true;
                    }
                    break;
                case "west":
                    if(monster.xPosition==this.xPosition-1&&monster.yPosition==this.yPosition){
                        return true;
                    }
            }
        }
            return false;
        }
    private void swingSword(){
        for(Monster monster : MazeGenerator.monsterList){
            switch (heading){
                case "north":
                    if(monster.xPosition==this.xPosition&&monster.yPosition==this.yPosition-1){
                        monster.health-=50;
                        monster.yPosition-=2;
                    }
                    break;
                case "south":
                    if(monster.xPosition==this.xPosition&&monster.yPosition==this.yPosition+1){
                        monster.health-=50;
                        monster.yPosition+=2;
                    }
                case "east":
                    if(monster.xPosition==this.xPosition+1&&monster.yPosition==this.yPosition){
                        monster.health-=50;
                        monster.xPosition+=2;
                    }
                    break;
                case "west":
                    if(monster.xPosition==this.xPosition-1&&monster.yPosition==this.yPosition){
                        monster.health-=50;
                        monster.xPosition-=2;
                    }
            }
        }
    }
    private void decideWhichWay(int[][] maze) {
        switch (heading) {
            case "north":
                if (maze[yPosition - 1][xPosition] == 1) {
                    stack.add(this.xPosition + " " + this.yPosition);
                    this.yPosition--;
                }
                break;
            case "east":
                if (maze[yPosition][xPosition + 1] == 1) {
                    stack.add(this.xPosition + " " + this.yPosition);
                    this.xPosition++;
                }
                break;
            case "south":
                if (maze[yPosition + 1][xPosition] == 1) {
                    stack.add(this.xPosition + " " + this.yPosition);
                    this.yPosition++;
                }
                break;

            case "west":
                if (maze[yPosition][xPosition - 1] == 1) {
                    stack.add(this.xPosition + " " + this.yPosition);
                    this.xPosition--;
                }
                break;
        }
    }

    Player(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}


