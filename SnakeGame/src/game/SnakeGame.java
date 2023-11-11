package game;

//Swing, AWT, Graphics, Exceptional Handling, OOPS, Abstract classes, Interfaces
import javax.swing.JFrame;

public class SnakeGame extends JFrame {

    SnakeGame() {
        super("Snake Game"); // Title of Frame
        add(new Board());
        pack(); // Refreshes Frame

        // setSize(300, 300); // Sets Frame Size
        setLocationRelativeTo(null);// Sets Frame Location at Center
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}
