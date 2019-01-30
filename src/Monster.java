import java.util.ArrayDeque;
import java.util.Deque;

import java.awt.event.KeyEvent;

class Monster {
    int xPosition;
    int yPosition;
    String heading = "north";
    int energy=0;
    int health = 100;
    private Deque<String> stack = new ArrayDeque<>();

    void update(Player player, int[][] maze){
        if(health<=0){
            this.yPosition=998;
            this.xPosition=998;
        }
        if(playerInRange(player)){
            if(energy>100) {
                if (player.xPosition > this.xPosition) {
                    this.xPosition++;
                } else if (player.xPosition < this.xPosition) {
                    this.xPosition--;
                }
                if (player.yPosition > this.yPosition) {
                    this.yPosition++;
                } else if (player.yPosition < this.yPosition) {
                    this.yPosition--;
                }
                energy=0;
            }else{
                energy+=50;
            }
            //decideWhichWay(maze);
        }
    }
    boolean playerInRange(Player player){
        for(int i = this.xPosition-10;i<this.xPosition+10;i++){
            for(int cnt = this.yPosition-10; cnt<this.yPosition+10;cnt++){
                if(i==player.xPosition&&cnt==player.yPosition){
                    return true;
                }
            }
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

    Monster(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}


