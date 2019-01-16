import java.util.ArrayDeque;
import java.util.Deque;

public class Connector {
    int xPosition;
    int yPosition;
    String heading = "north";
    int[][] visited;
    Deque stack = new ArrayDeque();

    public void recurse(int[][] maze) {
        while (!unvisitedNearby(maze)) {
            String coordinates = stack.getLast().toString();
            String[] coordinateSys = coordinates.split(" ");
            this.xPosition = Integer.parseInt(coordinateSys[0]);
            this.yPosition = Integer.parseInt(coordinateSys[1]);
            stack.removeLast();
        }
    }

    public boolean unvisitedNearby(int[][] maze) {
        if (maze[yPosition - 1][xPosition] == 1 && visited[yPosition - 1][xPosition] != 1) {
            return true;
        } else {
            if (maze[yPosition][xPosition + 1] == 1 && visited[yPosition][xPosition + 1] != 1) {

                return true;
            } else {
                if (maze[yPosition + 1][xPosition] == 1 && visited[yPosition + 1][xPosition] != 1) {
                    return true;
                } else {
                    return maze[yPosition][xPosition - 1] == 1 && visited[yPosition][xPosition - 1] != 1;
                }
            }
        }
    }

    public int[][] resolve(int[][] maze) {
        while (!stack.isEmpty()) {
            String coordinates = stack.getLast().toString();
            String[] coordinateSys = coordinates.split(" ");
            maze[Integer.parseInt(coordinateSys[1])][Integer.parseInt(coordinateSys[0])] = 6;
            stack.removeLast();
        }
        return maze;
    }

    public void decideWhichWay(int[][] maze, int[][] color) {
        if (unvisitedNearby(maze)) {
            switch (heading) {
                case "north":
                    if (maze[yPosition - 1][xPosition] == 1 && visited[yPosition - 1][xPosition] != 1) {
                        visited[yPosition - 1][xPosition] = 1;
                        stack.add(new String(this.xPosition + " " + this.yPosition));
                        this.yPosition--;
                        break;
                    } else {
                        if (color[yPosition - 2][xPosition] > 10 && maze[yPosition - 1][xPosition] != 1) {
                            visited[yPosition - 1][xPosition] = 1;
                            stack.add(new String(this.xPosition + " " + this.yPosition));
                            maze[yPosition - 1][xPosition] = 1;
                            this.yPosition--;
                            break;
                        } else {
                            heading = "east";
                        }
                    }
                case "east":
                    if (heading == "east") {
                        if (maze[yPosition][xPosition + 1] == 1 && visited[yPosition][xPosition + 1] != 1) {
                            visited[yPosition][xPosition + 1] = 1;
                            stack.add(new String(this.xPosition + " " + this.yPosition));
                            this.xPosition++;
                            break;
                        } else {
                            if (color[yPosition][xPosition + 2] > 10 && maze[yPosition][xPosition + 1] != 1) {
                                visited[yPosition][xPosition + 1] = 1;
                                stack.add(new String(this.xPosition + " " + this.yPosition));
                                maze[yPosition][xPosition + 1] = 1;
                                this.xPosition++;
                                break;
                            } else {
                                heading = "south";
                            }
                        }
                    }
                case "south":
                    if (maze[yPosition + 1][xPosition] == 1 && visited[yPosition + 1][xPosition] != 1) {
                        visited[yPosition + 1][xPosition] = 1;
                        stack.add(new String(this.xPosition + " " + this.yPosition));
                        this.yPosition++;
                        break;
                    } else {
                        if (color[yPosition + 2][xPosition] > 10 && maze[yPosition + 1][xPosition] != 1) {
                            visited[yPosition + 1][xPosition] = 1;
                            stack.add(new String(this.xPosition + " " + this.yPosition));
                            maze[yPosition + 1][xPosition] = 1;
                            this.yPosition++;
                            break;
                        } else {
                            heading = "west";
                        }
                    }
                case "west":
                    if (maze[yPosition][xPosition - 1] == 1 && visited[yPosition][xPosition - 1] != 1) {
                        visited[yPosition][xPosition - 1] = 1;
                        stack.add(new String(this.xPosition + " " + this.yPosition));
                        this.xPosition--;
                        break;
                    } else {
                        if (color[yPosition][xPosition-2] > 10 && maze[yPosition][xPosition-1] != 1) {
                            visited[yPosition][xPosition-1] = 1;
                            stack.add(new String(this.xPosition + " " + this.yPosition));
                            maze[yPosition][xPosition-1] = 1;
                            this.xPosition--;
                            break;
                        } else {
                            heading = "north";
                        }
                    }
            }
        } else {
            recurse(maze);
        }
    }

    public Connector(int[][] field, int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.visited = new int[field.length][field[0].length];
    }
}
