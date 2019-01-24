import java.util.ArrayDeque;
import java.util.Deque;

    class Fireball {
        int xPosition;
        int yPosition;
        String heading = "north";
        int energy=0;
        private Deque<String> stack = new ArrayDeque<>();

        void update(){
                decideWhichWay(MazeGenerator.field);
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

        Fireball(int xPosition, int yPosition) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
        }
    }


