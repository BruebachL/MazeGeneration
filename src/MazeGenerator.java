import java.util.concurrent.TimeUnit;

public class MazeGenerator {
    public static void main(String[] args) throws Exception{
        int[][] field = new int[20][50];
        int[][] color = new int[20][50];
        Room test = new Room(3,3,3,3,1);
        Room tester = new Room(20,10,7,7,1);
        //test.placeRoom(field);
        //tester.placeRoom(field);
        Crawler first = new Crawler(1,1,0,1);
        Crawler second = new Crawler(40,10,0,2);
        Crawler third = new Crawler(25,18,0,3);
        Crawler fourth = new Crawler(48,1,0,4);
        for(int i =0;i<500;i++) {
            first.move(field,color);
            second.move(field,color);
            third.move(field,color);
            fourth.move(field,color);


            //TimeUnit.MILLISECONDS.sleep(500);
            //TimeUnit.SECONDS.sleep(1);

        }
        printField(field,color);
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
    public static void colorPicker(int[][] color, int i, int cnt){
        switch (color[i][cnt]){
            case 0: System.out.print(ANSI_RESET);
            break;
            case 1: System.out.print(ANSI_RED);
            break;
            case 2: System.out.print(ANSI_YELLOW);
            break;
            case 3: System.out.print(ANSI_GREEN);
            break;
            case 4: System.out.print(ANSI_CYAN);
            break;
        }
    }
    public static void printField(int[][] field,int[][] color){
        for (int i=0; i<field.length;i++) {
            for(int cnt=0;cnt<field[0].length;cnt++){
                colorPicker(color, i, cnt);
                if(field[i][cnt]==1){
                    System.out.print("O ");
                }else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
