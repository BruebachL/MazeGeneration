import java.util.ArrayDeque;
import java.util.Deque;

public class Connector {
    int xPosition;
    int yPosition;
    int roomsConnected;
    int roomsToConnect;
    int[] roomUIDS;
    String heading = "north";
    int[][] visited;
    Deque stack = new ArrayDeque();
    private boolean unvisitedNearby(int[][] maze) {
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
    public void recurse(int[][] maze) {
        while (!stack.isEmpty()&&!unvisitedNearby(maze)) {
            String coordinates = stack.getLast().toString();
            String[] coordinateSys = coordinates.split(" ");
            this.xPosition = Integer.parseInt(coordinateSys[0]);
            this.yPosition = Integer.parseInt(coordinateSys[1]);
            stack.removeLast();
        }
    }

    public int[][] decideWhichWay(int[][] maze, int[][] color, int startX, int startY) {
        //System.out.println("connector moving from: " + this.xPosition + " " + this.yPosition);
        if(!unvisitedNearby(maze)){
            recurse(maze);
        }else {
            switch (heading) {
                case "north":
                    if (maze[yPosition - 1][xPosition] == 1&& visited[yPosition - 1][xPosition] != 1) {
                        stack.add(new String(this.xPosition + " " + this.yPosition));
                        visited[yPosition-1][xPosition] = 1;
                        this.yPosition--;
                        break;
                    } else if (((color[yPosition - 2][xPosition] >= 10 && color[yPosition][xPosition] == 1) || (color[yPosition - 2][xPosition] == 1 && (color[yPosition][xPosition] >= 9))) && maze[yPosition - 1][xPosition] != 1) {
                        if(!UIDConnected(color[yPosition-2][xPosition]) && color[yPosition-2][xPosition]>=10){
                            maze[yPosition - 1][xPosition] = 1;
                            roomUIDS[roomsConnected]=color[yPosition-2][xPosition];
                            System.out.println("Connected a " + heading + "ern passageway.");
                            roomsConnected++;
                            break;
                        }else if (!UIDConnected(color[yPosition][xPosition]) && color[yPosition][xPosition]>=9) {
                            maze[yPosition - 1][xPosition] = 1;
                            roomUIDS[roomsConnected] = color[yPosition][xPosition];
                            System.out.println("Connected a " + heading + "ern passageway.");
                            roomsConnected++;
                            break;
                        }else{
                            heading = "east";
                            //System.out.println("from north going next to:" + heading);
                            break;
                        }
                    } else {
                        heading = "east";
                        //System.out.println("from north going next to:" + heading);
                        break;
                    }
                case "east":
                    if (maze[yPosition][xPosition + 1] == 1&& visited[yPosition][xPosition+1] != 1) {
                        stack.add(new String(this.xPosition + " " + this.yPosition));
                        visited[yPosition][xPosition+1] = 1;
                        this.xPosition++;
                        break;

                    } else if (((color[yPosition][xPosition+2] >= 10 && color[yPosition][xPosition] == 1) || (color[yPosition][xPosition+2] == 1 && (color[yPosition][xPosition] >= 9))) && maze[yPosition][xPosition+1] != 1) {
                        if(!UIDConnected(color[yPosition][xPosition+2]) && color[yPosition][xPosition+2]>=10){
                            maze[yPosition][xPosition+1] = 1;
                            roomUIDS[roomsConnected]=color[yPosition][xPosition+2];
                            System.out.println("Connected a " + heading + "ern passageway.");
                            roomsConnected++;
                            break;
                        }else if (!UIDConnected(color[yPosition][xPosition]) && color[yPosition][xPosition]>=9) {
                            maze[yPosition][xPosition+1] = 1;
                            roomUIDS[roomsConnected] = color[yPosition][xPosition];
                            System.out.println("Connected a " + heading + "ern passageway.");
                            roomsConnected++;
                            break;
                        }else{
                            heading = "south";
                            //System.out.println("from south going next to:" + heading);
                            break;
                        }
                    } else {
                        heading = "south";
                        //System.out.println("from east going next to: " + heading);
                        break;
                    }
                case "south":
                    if (maze[yPosition + 1][xPosition] == 1&& visited[yPosition + 1][xPosition] != 1) {
                        stack.add(new String(this.xPosition + " " + this.yPosition));
                        visited[yPosition+1][xPosition] = 1;
                        this.yPosition++;
                        break;

                    } else if (((color[yPosition + 2][xPosition] >= 10 && color[yPosition][xPosition] == 1) || (color[yPosition + 2][xPosition] == 1 && (color[yPosition][xPosition] >= 9))) && maze[yPosition + 1][xPosition] != 1) {
                        if(!UIDConnected(color[yPosition+2][xPosition]) && color[yPosition+2][xPosition]>=10){
                            maze[yPosition + 1][xPosition] = 1;
                            roomUIDS[roomsConnected]=color[yPosition+2][xPosition];
                            System.out.println("Connected a " + heading + "ern passageway.");
                            roomsConnected++;
                            break;
                        }else if (!UIDConnected(color[yPosition][xPosition]) && color[yPosition][xPosition]>=9) {
                            maze[yPosition + 1][xPosition] = 1;
                            roomUIDS[roomsConnected] = color[yPosition][xPosition];
                            System.out.println("Connected a " + heading + "ern passageway.");
                            roomsConnected++;
                            break;
                        }else{
                            heading = "west";
                            //System.out.println("from south going next to:" + heading);
                            break;
                        }
                    } else {
                        heading = "west";
                        //System.out.println("from south going next to: " + heading);
                        break;
                    }
                case "west":
                    if (maze[yPosition][xPosition - 1] == 1&& visited[yPosition][xPosition-1] != 1) {
                        stack.add(new String(this.xPosition + " " + this.yPosition));
                        visited[yPosition][xPosition-1] = 1;
                        this.xPosition--;
                        break;
                    } else if (((color[yPosition][xPosition-2] >= 10 && color[yPosition][xPosition] == 1) || (color[yPosition][xPosition-2] == 1 && (color[yPosition][xPosition] >= 9))) && maze[yPosition][xPosition-2] != 1) {
                        if(!UIDConnected(color[yPosition][xPosition-2]) && color[yPosition][xPosition-2]>=10){
                            maze[yPosition][xPosition-1] = 1;
                            roomUIDS[roomsConnected]=color[yPosition][xPosition-2];
                            System.out.println("Connected a " + heading + "ern passageway.");
                            roomsConnected++;
                            break;
                        }else if (!UIDConnected(color[yPosition][xPosition]) && color[yPosition][xPosition]>=9) {
                            maze[yPosition][xPosition-1] = 1;
                            roomUIDS[roomsConnected] = color[yPosition][xPosition];
                            System.out.println("Connected a " + heading + "ern passageway.");
                            roomsConnected++;
                            break;
                        }else{
                            System.out.println("Got else");
                            heading = "north";
                            //System.out.println("from west going next to:" + heading);
                            break;
                        }
                    } else {
                        heading = "north";
                        //System.out.println("from west going next to: " + heading);
                        break;
                    }
            }
        }
        return maze;
    }

    public Connector(int[][] field, int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.visited = new int[field.length][field[0].length];
    }
    public void setRooms(int roomsTotal){
        this.roomsToConnect=roomsTotal;
        this.roomUIDS=new int[roomsTotal];
    }
    boolean UIDConnected(int newUID){
        for(int uID : roomUIDS){
            if(uID==newUID){
                System.out.println(uID + " is already present " + newUID);
                return true;
            }
        }
        System.out.println(newUID + " is unique");
        return false;
    }
    public void printConnections(){
        for(int uID : roomUIDS){
            System.out.print(uID + " ");
        }
    }
}
