import javax.swing.*;
import java.awt.*;

public class Game extends JComponent {
    public static int[][] field;

    static {
        try {
            field = MazeGenerator.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void game() {

    }

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        JFrame frame = new JFrame("Maze");
        frame.setSize(field[0].length, field.length);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().add(new MazeGenerator());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.repaint();
        long endTime = System.nanoTime();
        System.out.println("Took " + (endTime - startTime) + " ns");
    }

    public void paintComponent(Graphics g) {
        int currentColumn = 0;
        int size = 1;
        int xRatio = (int) Math.floor(1920 / field[0].length);
        int yRatio = (int) Math.floor(1080 / field.length);
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
}

