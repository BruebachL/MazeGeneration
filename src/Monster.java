import java.util.*;

import java.awt.event.KeyEvent;

class Monster {
    int xPosition;
    int yPosition;
    String heading = "north";
    int energy=0;
    int health = 100;
    private Deque<String> stack = new ArrayDeque<>();
    List<Tile> open = new List<Tile>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Tile> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Tile tile) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Tile> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Tile> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Tile get(int index) {
            return null;
        }

        @Override
        public Tile set(int index, Tile element) {
            return null;
        }

        @Override
        public void add(int index, Tile element) {

        }

        @Override
        public Tile remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<Tile> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Tile> listIterator(int index) {
            return null;
        }

        @Override
        public List<Tile> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    List<Tile> closed = new List<Tile>(){
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Tile> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Tile tile) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Tile> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Tile> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Tile get(int index) {
            return null;
        }

        @Override
        public Tile set(int index, Tile element) {
            return null;
        }

        @Override
        public void add(int index, Tile element) {

        }

        @Override
        public Tile remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<Tile> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Tile> listIterator(int index) {
            return null;
        }

        @Override
        public List<Tile> subList(int fromIndex, int toIndex) {
            return null;
        }
    };

    void aStar(){
        Tile start = new Tile();
    }

    void update(Player player, int[][] maze){
        if(health<=0){
            this.yPosition=998;
            this.xPosition=998;
        }else
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


