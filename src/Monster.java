import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

class Monster {
    int xPosition;
    int yPosition;
    String heading = "north";
    int energy=0;
    int health = 100;
    private Deque<Tile> stack = new ArrayDeque<>();
    public List<Tile> open = new CopyOnWriteArrayList<>(){
        @Override
        public boolean contains(Object o) {
            Tile toBeChecked = (Tile) o;
            for(Tile current : closed){
                if(current.xPosition==toBeChecked.xPosition&&current.yPosition==toBeChecked.yPosition){
                    return true;
                }
            }
            return false;
        }
    };
    public List<Tile> closed = new CopyOnWriteArrayList<>(){
        @Override
        public boolean contains(Object o) {
            Tile toBeChecked = (Tile) o;
            for(Tile current : closed){
                if(current.xPosition==toBeChecked.xPosition&&current.yPosition==toBeChecked.yPosition){
                    return true;
                }
            }
            return false;
        }
    };

    Monster(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    void update(Player player, int[][] maze){
        if(health<=0){
            this.yPosition=998;
            this.xPosition=998;
        }else
        if(playerInRange(player)){
            if(energy>100) {
                stack.clear();
                this.aStar();
                Tile moveTo = stack.getLast();
                this.yPosition=moveTo.yPosition;
                this.xPosition=moveTo.xPosition;
                MazeGenerator.path[this.yPosition][this.xPosition]=0;
                energy=0;
            }else{
                energy+=50;
            }
        }
    }
    boolean axisCheck(Tile destination){
        int xDistance = 0;
        if(destination.xPosition>this.xPosition){
            xDistance = destination.xPosition-this.xPosition;
        }else{
            xDistance = this.xPosition-destination.xPosition;
        }
        int yDistance = 0;
        if(destination.yPosition>this.yPosition){
            yDistance = destination.yPosition-this.yPosition;
        }else{
            yDistance = this.yPosition-destination.yPosition;
        }
        if(xDistance>yDistance){
            return false;
        }
        return true;
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
    Tile[] getNeighbors(Tile end, Tile current){
        Tile north = new Tile(end, current, current.xPosition, current.yPosition - 1);
        Tile south = new Tile(end, current, current.xPosition, current.yPosition + 1);
        Tile east = new Tile(end, current, current.xPosition + 1, current.yPosition);
        Tile west = new Tile(end, current, current.xPosition - 1, current.yPosition);
        if(axisCheck(end)) {
            Tile[] neighbors = {east,west,north,south};
            return neighbors;
        }else{
            Tile[] neighbors = {north,south,east,west};
            return neighbors;
        }
    }
    void aStar(){
        open = new CopyOnWriteArrayList<>(){
            @Override
            public boolean contains(Object o) {
                Tile toBeChecked = (Tile) o;
                for(Tile current : open){
                    if(current.xPosition==toBeChecked.xPosition&&current.yPosition==toBeChecked.yPosition){
                        return true;
                    }
                }
                return false;
            }
        };
        closed = new CopyOnWriteArrayList<>(){
            @Override
            public boolean contains(Object o) {
                Tile toBeChecked = (Tile) o;
                for(Tile current : closed){
                    if(current.xPosition==toBeChecked.xPosition&&current.yPosition==toBeChecked.yPosition){
                        return true;
                    }
                }
                return false;
            }
        };
        Tile start = new Tile(this.xPosition,this.yPosition);
        Tile end = new Tile(MazeGenerator.player.xPosition,MazeGenerator.player.yPosition);
        start.setH(end);
        Tile current=start;
        open.add(start);
        do{
            System.out.println(open.size());
            int lowestScore = 200;
            for(Tile checked : open){
                if(checked.f<lowestScore){
                    current=checked;
                    lowestScore = checked.f;
                }
            }
            closed.add(current);
            open.remove(current);

            if(closed.contains(end)){
                end.parent = current.parent;
                MazeGenerator.path = new int[MazeGenerator.mazeHeight][MazeGenerator.mazeWidth];
                while(current.parent!=null){
                    stack.add(current);
                    MazeGenerator.path[current.yPosition][current.xPosition]=1;
                    System.out.println("X: " + current.xPosition + " Y: " + current.yPosition);
                    current = current.parent;

                }

                MazeGenerator.path[end.yPosition][end.xPosition]=0;
                break;
            }
            Tile[] neighbors = getNeighbors(end,current);
            for(Tile neighbor : neighbors) {
                if (MazeGenerator.field[neighbor.yPosition][neighbor.xPosition] != 0) {
                    if (closed.contains(neighbor)) {

                    } else {
                        if (!open.contains(neighbor)) {
                            open.add(neighbor);
                        }
                    }
                }
            }
        }while(!open.isEmpty());
    }
}


