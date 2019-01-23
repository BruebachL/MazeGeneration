import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class MazeGenerator extends JComponent implements KeyListener{
    private static int roomsPlaced = 0;
    static int mazeHeight = 250;
    static int mazeWidth = 250;
    private static int[][] field = new int[mazeHeight][mazeWidth];
    private static int[][] color = new int[mazeHeight][mazeWidth];
    private static Player player = new Player(field, 30,30);
    private static Connector roomConnector = new Connector(field, 1, 1);

    private static void init() throws InterruptedException {
        Room[] rooms = generateRooms();
        placeRooms(rooms);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Customize? (Y/N)");
        boolean validInput = false;
        char input = 'n';
        while (!validInput) {
            input = scanner.nextLine().charAt(0);
            if (input == 'y' || input == 'Y' || input == 'n' || input == 'N') {
                validInput = true;
            } else {
                System.out.println("Please select a valid option.");
            }
        }
        if (input == 'y' || input == 'Y') {
            customize();
        }
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
        roomConnector.xPosition = 3;
        roomConnector.yPosition = 3;
        roomConnector.setRooms(roomsPlaced);
        while (roomConnector.roomsConnected != roomConnector.roomsToConnect) {
            field = roomConnector.decideWhichWay(field, color, rooms[0].xPosition, rooms[0].yPosition);
        }
        connector(field, color);
        for (int i = 1; i < mazeHeight - 1; i++) {
            for (int cnt = 1; cnt < mazeWidth - 1; cnt++) {
                first.removeDeadEnds(field, i, cnt);
            }
        }
        System.out.println("Initialized");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e, field);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
    }
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Maze");
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
                player.keyPressed(e, field);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
            }
        };
        frame.addKeyListener(listener);
        init();
        while(true){
            System.out.println(player.heading);
            frame.repaint();
        }
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

    public void paintComponent(Graphics g) {
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

        for (int[] row : field
        ) {

            for (int i = 0; i < row.length; i++) {
                if (player.yPosition == currentColumn && player.xPosition == i) {
                    g.setColor(Color.MAGENTA);
                    g.fillRect((i * size), currentColumn * size, size, size);
                } else {
                    if (roomConnector.yPosition == currentColumn && roomConnector.xPosition == i) {
                        g.setColor(Color.yellow);
                        g.fillRect((i * size), currentColumn * size, size * 5, size * 5);
                    } else {
                        if (row[i] == 1) {
                            g.setColor(Color.white);
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
            }
            currentColumn++;
        }

    }

    private static void placeRooms(Room[] rooms) {
        for (Room currentRoom : rooms) {
            if (currentRoom.checkRoom(field, color)) {
                currentRoom.placeRoom(field, color);
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
                roomWidth = +2;
            } else if (roomWidth > mazeWidth - 2) {
                roomWidth = -2;
            }
            if (roomHeight < 2) {
                roomHeight = +2;
            } else if (roomHeight > mazeHeight - 2) {
                roomHeight = +2;
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

