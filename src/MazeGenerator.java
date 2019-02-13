import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class MazeGenerator extends JComponent implements KeyListener {
    private static int roomsPlaced = 0;
    static int mazeHeight = 250;
    static int mazeWidth = 250;
    static int[][] field = new int[mazeHeight][mazeWidth];
    static int[][] color = new int[mazeHeight][mazeWidth];
    static Player player = new Player(30, 30);
    static ArrayList<Monster> monsterList = new ArrayList<Monster>();
    static ArrayList<Room> roomList = new ArrayList<Room>();
    private static Connector roomConnector = new Connector(field, 1, 1);
    static List<Fireball> fireballList = new CopyOnWriteArrayList<>();
    static JFrame frame = new JFrame("Maze");
    public static String currentMessage = "";
    public static int displayTimer;


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            player.keyPressed(e, field);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            player.keyPressed(e, field);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.out.println("keyReleasedMain=" + KeyEvent.getKeyText(e.getKeyCode()));
    }

    public static void main(String[] args) throws Exception {

        frame.setSize(field[0].length, field.length);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().add(new MazeGenerator());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        KeyListener listener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_G) {
                        currentMessage="test";
                        displayTimer=5;
                        frame.repaint();
                }
                try {
                    player.keyPressed(e, field);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("keyReleased=" + KeyEvent.getKeyText(e.getKeyCode()));
            }
        };
        frame.addKeyListener(listener);
        init();
        frame.repaint();
        while (true) {
            while (player.turnTaken == false) {
                player.update();
                TimeUnit.MILLISECONDS.sleep(100);
                frame.repaint();
            }
            Iterator<Fireball> iter = fireballList.iterator();
            while(iter.hasNext()){
                Fireball fireball = iter.next();
                fireball.update();
            }

            for (Monster monster: monsterList
            ) {
                monster.update(player,field);
            }
            System.out.println("Logic processed");
            frame.repaint();
            if(displayTimer>0) {
                displayTimer--;
            }
            player.turnTaken = false;
        }
    }

    private static void init() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Customize? (Y/N)");
        boolean validInput = false;
        char input = 'n';
        while (!validInput) {
            //input = scanner.nextLine().charAt(0);
            if (input == 'y' || input == 'Y' || input == 'n' || input == 'N') {
                validInput = true;
            } else {
                System.out.println("Please select a valid option.");
            }
        }
        if (input == 'y' || input == 'Y') {
            customize();
        }
        field = new int[mazeHeight][mazeWidth];
        color = new int[mazeHeight][mazeWidth];
        Room[] rooms = generateRooms();
        placeRooms(rooms);
        Crawler first = new Crawler(3, 3, 0, 1);
        //Crawler second = new Crawler(3, mazeHeight-3, 0, 1);
        //Crawler third = new Crawler(mazeWidth-3, 3, 0, 1);
        //Crawler fourth = new Crawler(mazeWidth-3, mazeHeight-3, 0, 1);
        //Crawler fifth = new Crawler(1, 1, 0, 1);
        //Crawler sixth = new Crawler(1, (mazeHeight-2)/2, 0, 1);
        //Crawler seventh = new Crawler((mazeWidth-2)/2, 1, 0, 1);
        //Crawler eight = new Crawler((mazeWidth-2)/2, (mazeHeight-2)/2, 0, 1);
        boolean filled = false;
        int safetyPasses = 0;
        while (!filled) {
            for (int i = 0; i < 500000; i++) {
                first.move(field, color);
                //second.move(field, color);
                //third.move(field, color);
                //fourth.move(field, color);
                //fifth.move(field, color);
                //sixth.move(field, color);
                //seventh.move(field, color);
                //eight.move(field, color);
            }
            if (first.furthestX == mazeWidth - 3 && first.furthestY == mazeHeight - 2) {
                System.out.println("Pass number: " + (safetyPasses + 1));
                safetyPasses++;
            }
            if (safetyPasses >= 25) {
                filled = true;
            }
        }
        System.out.println("Filled");
        player.xPosition = rooms[0].xPosition;
        player.yPosition = rooms[0].yPosition;
        Monster monster = new Monster(rooms[0].xPosition+20, rooms[0].yPosition+20);
        Monster monster2 = new Monster(rooms[0].xPosition+25, rooms[0].yPosition+25);
        Monster monster3 = new Monster(rooms[0].xPosition+15, rooms[0].yPosition+15);
        monsterList.add(monster);
        monsterList.add(monster2);
        monsterList.add(monster3);
        roomConnector.xPosition = 3;
        roomConnector.yPosition = 3;
        roomConnector.roomsConnected = 0;
        roomConnector.setRooms(roomsPlaced);
        while (roomConnector.roomsConnected != roomConnector.roomsToConnect) {
            field = roomConnector.decideWhichWay(field, color, rooms[0].xPosition, rooms[0].yPosition);
        }
        System.out.println("connected");
        connector(field, color);
        System.out.println("connected alt");
        for (int i = 1; i < mazeHeight - 1; i++) {
            for (int cnt = 1; cnt < mazeWidth - 1; cnt++) {
                first.removeDeadEnds(field, i, cnt);
            }
        }
        int x = 0;
        while (x++<10){
            System.out.println(x);
        }
        System.out.println("Initialized");
    }

    private static void customize() {
        Scanner scanner = new Scanner(System.in);
        boolean exited = false;
        while (!exited) {
            String[] options = {"Please select an option:", "1. Maze Dimensions", "2. Amount of rooms", "3. Exit"};
            for (String option : options
            ) {
                System.out.println(option);
            }
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    System.out.println("Height:");
                    int newHeight = scanner.nextInt();
                    if (newHeight <= 0) {
                        System.out.println("Invalid height.");
                        break;
                    } else {
                        mazeHeight = newHeight;
                        System.out.println("Width:");
                        int newWidth = scanner.nextInt();
                        if (newWidth <= 0) {
                            System.out.println("Invalid width.");
                            break;
                        } else {
                            mazeWidth = newWidth;
                            field = new int[mazeHeight][mazeWidth];
                            color = new int[mazeHeight][mazeWidth];
                            break;
                        }
                    }
                case 3:
                    exited = true;
                    break;
                default:
                    break;

            }
        }
    }

    public static void repaintWindow() {
        frame.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int currentColumn = 0;
        int size = 1;
        int xRatio = 1920 / field[0].length;
        int yRatio = 1080 / field.length;
        if (xRatio > yRatio && yRatio != 0) {
            size = yRatio;
        } else {
            if (xRatio != 0) {
                size = xRatio;
            }
        }
        Color brown = new Color(209, 145, 56);
        for (int[] row : field
        ) {

            for (int i = 0; i < row.length; i++) {
                if(color[currentColumn][i]==1){
                    g.setColor(Color.white);
                }else if(color[currentColumn][i]>=10){
                    g.setColor(Color.lightGray);
                }else if(color[currentColumn][i]==5){
                    g.setColor(brown);
                }
                if (player.yPosition == currentColumn && player.xPosition == i) {
                    g.setColor(Color.BLUE);
                    g.fillRect((i * size), currentColumn * size, size*10, size*10);
                } else {
                        if (roomConnector.yPosition == currentColumn && roomConnector.xPosition == i) {
                            g.setColor(Color.yellow);
                            g.fillRect((i * size), currentColumn * size, size * 5, size * 5);
                        } else {
                            if (row[i] == 1) {
                                g.fillRect((i * size), currentColumn * size, size, size);
                            } else {
                                if (row[i] == 6) {
                                    g.setColor(Color.red);
                                    g.fillRect((i * size), currentColumn * size, size, size);
                                } else {
                                    g.setColor(Color.black);
                                    g.fillRect((i * size), currentColumn * size, size, size);
                                }
                            }
                        }
                    }

                for(Fireball fireball:fireballList){
                    if(fireball.yPosition == currentColumn && fireball.xPosition == i){
                        g.setColor(Color.ORANGE);
                        g.fillRect((i * size), currentColumn * size, size*10, size*10);
                    }
                }
                for(Monster monster:monsterList){
                    if(monster.yPosition == currentColumn && monster.xPosition == i){
                        g.setColor(Color.RED);
                        g.fillRect((i * size), currentColumn * size, size, size);
                    }
                }
            }
            currentColumn++;
        }
        int currentMonster = 0;
        for(Monster monster : monsterList) {
            int monsterHealth = monster.health;
            for (int i = 0; i < monsterHealth; i++) {
                g.setColor(Color.RED);
                g.fillRect((i + 1200), 50*(currentMonster+1), size, size);
            }
            currentMonster++;
        }
        if(displayTimer>0){
            g.drawString(currentMessage,1200,200);
        }
    }

    private static void placeRooms(Room[] rooms) {
        for (Room currentRoom : rooms) {
            if (currentRoom.checkRoom(field)) {
                currentRoom.placeRoom(field, color);
                Monster monster = new Monster(currentRoom.xPosition+15,currentRoom.yPosition+15);
                monsterList.add(monster);
                roomsPlaced++;
            }
        }
    }

    private static Room[] generateRooms() {
        int roomAmount = 50;
        Room[] rooms = new Room[roomAmount];
        for (int i = 0; i < roomAmount; i++) {
            int dimensions = (int) (Math.random() * (mazeWidth / 75)) + 40;
            int roomWidth = (int) (Math.random() * mazeWidth);
            int roomHeight = (int) (Math.random() * mazeHeight);
            if (roomWidth < 2) {
                roomWidth += 3;
            } else if (roomWidth > mazeWidth - 2) {
                roomWidth -= 3;
            }
            if (roomHeight < 2) {
                roomHeight += 3;
            } else if (roomHeight > mazeHeight - 2) {
                roomHeight -= 3;
            }
            rooms[i] = new Room(roomWidth, roomHeight, dimensions, dimensions, 1, i + 10);

        }
        return rooms;
    }

    private static void connector(int[][] field, int[][] color) {
        for (int i = 0; i < field.length; i++) {
            for (int cnt = 0; cnt < field[0].length; cnt++) {
                if (cnt + 1 < field[0].length && cnt - 1 >= 0 && field[i][cnt] == 0 && field[i][cnt + 1] == 1 && field[i][cnt - 1] == 1 && color[i][cnt - 1] != color[i][cnt + 1] && (color[i][cnt - 1] > 1 && color[i][cnt + 1] > 1)) {
                    int rand = (int) Math.floor(Math.random() * Math.floor(1010));
                    if (rand > 999) {
                        field[i][cnt] = 1;
                    }
                }
            }
        }
    }
}

