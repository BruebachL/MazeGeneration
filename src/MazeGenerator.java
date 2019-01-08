import java.util.concurrent.TimeUnit;

public class MazeGenerator {
    public static void main(String[] args) throws Exception {
        int[][] field = new int[30][100];
        int[][] color = new int[30][100];
        Room test = new Room(50, 19, 10, 30, 1);
        Room tester = new Room(20, 10, 7, 7, 1);
        //test.placeRoom(field);
        //  tester.placeRoom(field);
        Runner runner = new Runner(field, 1, 1);
        Crawler first = new Crawler(1, 1, 0, 1);
        Crawler second = new Crawler(48, 38, 0, 2);
        Crawler third = new Crawler(1, 38, 0, 3);
        Crawler fourth = new Crawler(48, 1, 0, 4);
        boolean displayGeneration = false;
        for (int i = 0; i < 500000; i++) {
            first.move(field, color);
            //second.move(field, color);
            //third.move(field, color);
            //fourth.move(field, color);
            //TimeUnit.SECONDS.sleep(1);
            if(displayGeneration) {
                if (i < 1000) {
                    if (i % 50 == 0) {
                        printField(field, color, runner);
                        TimeUnit.MILLISECONDS.sleep(500);
                    }
                } else {
                    if (i % 1000 == 0) {
                        printField(field, color, runner);
                        TimeUnit.MILLISECONDS.sleep(500);
                    }
                }
            }
        }
        connector(field, color);
        printField(field, color, runner);
        while (runner.xPosition < 80 && runner.yPosition < 29) {
            runner.decideWhichWay(field);
            /*printField(field, color, runner);
            TimeUnit.MILLISECONDS.sleep(500);*/
        }
        field = runner.resolve(field);
        printField(field, color, runner);


    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void connector(int[][] field, int[][] color) {
        for (int i = 0; i < field.length; i++) {
            for (int cnt = 0; cnt < field[0].length; cnt++) {
                if (cnt + 1 <= field.length && cnt - 1 >= 0 && field[i][cnt] == 0 && field[i][cnt + 1] == 1 && field[i][cnt - 1] == 1 && color[i][cnt - 1] != color[i][cnt + 1]) {
                    int rand = (int) Math.floor(Math.random() * Math.floor(101));
                    if (rand > 75) {
                        field[i][cnt] = 1;
                    }
                }
            }
        }
        System.out.println("Done");
    }

    public static void colorPicker(int[][] color, int i, int cnt) {
        switch (color[i][cnt]) {
            case 0:
                System.out.print(ANSI_RESET);
                break;
            case 1:
                System.out.print(ANSI_RED);
                break;
            case 2:
                System.out.print(ANSI_BLUE);
                break;
            case 3:
                System.out.print(ANSI_GREEN);
                break;
            case 4:
                System.out.print(ANSI_CYAN);
                break;
        }
    }

    public static void printField(int[][] field, int[][] color, Runner runner) {
        for (int i = 0; i < field.length; i++) {
            for (int cnt = 0; cnt < field[0].length; cnt++) {
                if (runner.yPosition == i && runner.xPosition == cnt) {
                    System.out.print("O ");
                } else {
                    colorPicker(color, i, cnt);
                    if (field[i][cnt] == 1) {
                        System.out.print("  ");
                    } else {
                        if (field[i][cnt] == 6) {
                            System.out.print("o ");
                        } else {
                            System.out.print("+ ");
                        }

                    }
                }
            }
            System.out.println();
        }
    }
}

