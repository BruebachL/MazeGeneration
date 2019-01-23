import java.util.ArrayDeque;
import java.util.Deque;

import java.awt.event.KeyEvent;

 class Player {
    int xPosition;
    int yPosition;
    String heading = "north";
    private Deque<String> stack = new ArrayDeque<>();


    void keyPressed(KeyEvent e, int[][] maze) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            this.heading = "west";
            this.decideWhichWay(maze);
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            this.heading = "east";
            this.decideWhichWay(maze);
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            this.heading = "north";
            this.decideWhichWay(maze);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            this.heading = "south";
            this.decideWhichWay(maze);
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


