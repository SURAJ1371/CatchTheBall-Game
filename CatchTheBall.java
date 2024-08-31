import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class CatchTheBall extends JPanel implements ActionListener {

    private Timer timer;
    private ArrayList<Rectangle> balls;
    private Rectangle paddle;
    private final int PADDLE_WIDTH = 100;
    private final int PADDLE_HEIGHT = 20;
    private final int BALL_SIZE = 20;
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 600;
    private Random random = new Random();

    public CatchTheBall() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    paddle.x -= 20;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    paddle.x += 20;
                }
                repaint();
            }
        });

        paddle = new Rectangle(SCREEN_WIDTH / 2 - PADDLE_WIDTH / 2, SCREEN_HEIGHT - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
        balls = new ArrayList<>();
        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);

        for (Rectangle ball : balls) {
            g.setColor(Color.RED);
            g.fillOval(ball.x, ball.y, BALL_SIZE, BALL_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add new ball at random intervals
        if (random.nextInt(100) < 5) {
            balls.add(new Rectangle(random.nextInt(SCREEN_WIDTH - BALL_SIZE), 0, BALL_SIZE, BALL_SIZE));
        }

        // Move balls
        ArrayList<Rectangle> ballsToRemove = new ArrayList<>();
        for (Rectangle ball : balls) {
            ball.y += 5;
            if (ball.y > SCREEN_HEIGHT) {
                ballsToRemove.add(ball);
            } else if (ball.intersects(paddle)) {
                ballsToRemove.add(ball);
            }
        }
        balls.removeAll(ballsToRemove);

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Catch the Ball");
        CatchTheBall game = new CatchTheBall();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
