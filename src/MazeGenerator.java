import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MazeGenerator extends JComponent {
    private static int roomAmount = 250;
    static int roomsPlaced=0;
    static int mazeHeight = 600;
    static int mazeWidth = 1000;
    private static int[][] field = new int[mazeHeight][mazeWidth];
    private static int[][] color = new int[mazeHeight][mazeWidth];
    private static Runner runner = new Runner(field, 1, 1);
    private static Connector test = new Connector(field, 1, 1);

    public static void main(String[] args) throws Exception {
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
        long startTime = System.nanoTime();
        JFrame frame = new JFrame("Maze");
        frame.setSize(field[0].length, field.length);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().add(new MazeGenerator());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Room[] rooms = generateRooms();
        placeRooms(rooms);

        Crawler first = new Crawler(3, 3, 0, 1);
        //Crawler second = new Crawler(1, mazeHeight-2, 0, 1);
        //Crawler third = new Crawler(mazeWidth-2, 1, 0, 1);
        //Crawler fourth = new Crawler(mazeWidth-2, mazeHeight-2, 0, 1);
        //Crawler fifth = new Crawler(1, 1, 0, 1);
        //Crawler sixth = new Crawler(1, (mazeHeight-2)/2, 0, 1);
        //Crawler seventh = new Crawler((mazeWidth-2)/2, 1, 0, 1);
        //Crawler eigth = new Crawler((mazeWidth-2)/2, (mazeHeight-2)/2, 0, 1);
        boolean filled = false;
        int debug = 0;
        int safetyPasses = 0;
        while (!filled) {
            System.out.println("Pass number: " + (debug + 1));
            for (int i = 0; i < 2000000; i++) {
                first.move(field, color);
                //second.move(field, color);
                //third.move(field, color);
                //fourth.move(field, color);
                //fifth.move(field, color);
                //sixth.move(field, color);
                //seventh.move(field, color);
                //eigth.move(field, color);
                //frame.repaint();
                System.out.println("subpass number: " + (i + 1));
            }
            debug++;
            frame.repaint();
            System.out.println(first.furthestX + " " + first.furthestY);
            if (first.furthestX == mazeWidth - 2 && first.furthestY == mazeHeight - 2) {
                safetyPasses++;
            }
            safetyPasses++;
            if (safetyPasses >= 4) {
                filled = true;
            }
        }
        //connector(field, color);

        test.xPosition = 3;
        test.yPosition = 3;
        test.setRooms(roomsPlaced);
        while (test.roomsConnected != test.roomsToConnect) {
            field = test.decideWhichWay(field, color,rooms[0].xPosition,rooms[0].yPosition);
            //TimeUnit.MILLISECONDS.sleep(150);
            frame.repaint();
        }


        System.out.println("Done connecting");
        System.out.println("Connected:");
        test.printConnections();

        for (int i = 1; i < mazeHeight - 1; i++) {
            for (int cnt = 1; cnt < mazeWidth - 1; cnt++) {
                first.removeDeadEnds(field, i, cnt);
                //TimeUnit.NANOSECONDS.sleep(1);
                frame.repaint();
            }
        }
        System.out.println("Generated");
        int exit = (int) Math.floor(Math.random() * Math.floor(field.length));
        boolean validExit = false;
        while (!validExit) {
            if (field[exit][field[0].length - 2] == 1) {
                field[exit][field[0].length - 1] = 1;
                validExit = true;
            }
            exit = (int) Math.floor(Math.random() * Math.floor(field.length));
        }
        frame.repaint();
        while (runner.xPosition <= field[0].length - 2) {
            runner.decideWhichWay(field);
            frame.repaint();
            //TimeUnit.MILLISECONDS.sleep(100);
        }
        field = runner.resolve(field);
        frame.repaint();
        System.out.println("Solved");
        long endTime = System.nanoTime();
        System.out.println("Took " + (endTime - startTime) + " ns");
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
        int xRatio = (int) Math.floor(1366 / field[0].length);
        int yRatio = (int) Math.floor(768 / field.length);
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
                if (runner.yPosition == currentColumn && runner.xPosition == i) {
                    g.setColor(Color.MAGENTA);
                    g.fillRect((i * size), currentColumn * size, size, size);
                } else {
                    if (test.yPosition == currentColumn && test.xPosition == i) {
                        g.setColor(Color.yellow);
                        g.fillRect((i * size), currentColumn * size, size*5, size*5);
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
        for (int i = 0; i < rooms.length; i++) {
            System.out.println("Placing Room " + i + " at " + rooms[i].xPosition + " and " + rooms[i].yPosition + " with height " + rooms[i].height + " and width " + rooms[i].width);
            if (rooms[i].checkRoom(field, color)) {
                rooms[i].placeRoom(field, color);
                roomsPlaced++;
            }
        }
    }

    private static Room[] generateRooms() {
        Room[] rooms = new Room[roomAmount];
        for (int i = 0; i < roomAmount; i++) {
            int dimensions = (int) (Math.random() * (mazeWidth / 75)) + 40;
            int roomWidth = (int) (Math.random()* mazeWidth);
            int roomHeight = (int) (Math.random() * mazeHeight);
            if(roomWidth<2){
                roomWidth=+2;
            }else if(roomWidth>mazeWidth-2){
                roomWidth=-2;
            }
            if(roomHeight<2){
                roomHeight=+2;
            }else if(roomHeight>mazeHeight-2){
                roomHeight=+2;
            }
            rooms[i] = new Room(roomWidth, roomHeight, dimensions, dimensions, 1, i + 10);
        }
        return rooms;
    }

    public static void connector(int[][] field, int[][] color) {
        //int exit = (int) Math.floor(Math.random() * Math.floor(field.length));
        //field[exit][field[0].length] = 1;
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
        System.out.println("Connected");
    }
}

