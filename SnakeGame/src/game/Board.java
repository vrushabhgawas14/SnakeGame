package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Board extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;
    private int dots;

    private int width = 400;
    private int height = width; // Change Random var while chnaging dimension.
    private int Apple_x;
    private int Apple_y;
    private Timer timer;
    private final int RANDOM = (width - 10) / 10;

    private final int ALL_DOT = ((int) Math.pow((width / 100), 2)) * 100;
    private final int DOT_SIZE = 10;
    private final int[] x = new int[ALL_DOT];
    private final int[] y = new int[ALL_DOT];

    private boolean rightDirection = true;
    private boolean leftDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    Board() {
        addKeyListener(new onKeyPress());

        setBackground(new Color(22, 2, 33));
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);

        loadImages();
        initializeGame();
    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("game/icons/apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("game/icons/dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("game/icons/head.png"));
        head = i3.getImage();
    }

    public void initializeGame() {
        dots = 3;

        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * DOT_SIZE;
        }

        locateApple();
        timer = new Timer(100, this);
        timer.start();
    }

    public void locateApple() {
        int random = (int) (Math.random() * RANDOM);
        Apple_x = random * DOT_SIZE; // Multiplying by 10, image is of 10 X 10 px
        // Apple_x = 200;

        random = (int) (Math.random() * RANDOM);
        Apple_y = random * DOT_SIZE; // Multiplying by 10, image is of 10 X 10 px
        // Apple_y = 150;

        // Debuging part (ignore)
        // System.out.println(Apple_x);
        // System.out.println(Apple_y);
        // System.out.println("Values " + x[0] + y[0]);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(apple, Apple_x, Apple_y, this);

            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }

                Toolkit.getDefaultToolkit().sync();
            }
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        String finalMsg = "Game Over!";
        Font font = new Font("Fira Code", Font.BOLD, 18);
        g.setColor(Color.WHITE);
        g.setFont(font);

        FontMetrics metrics = getFontMetrics(font);

        g.drawString(finalMsg, (width - metrics.stringWidth(finalMsg)) / 2, height / 2);
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkMyApple() {
        // System.out.println("Values " + x[0] + " " + y[0]);
        if ((x[0] == Apple_x) && (y[0] == Apple_y)) {
            System.out.println("Apple Hit!");
            dots++;
            locateApple();
        }
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }

        if (x[0] > width - 10 || x[0] < 0) {
            inGame = false;
        }

        if (y[0] > height - 10 || y[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (inGame) {
            checkMyApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public class onKeyPress extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && (!downDirection)) {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

}
