import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    
    private int totalBricks = 28;
    
    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballX = 120;
    private int ballY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(4, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //map
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.white);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        //score
        g.setColor(Color.white);
        g.setFont(new Font("courier new", Font.BOLD, 20));
        g.drawString("Score : " + score, 550, 30);
        //paddle
        g.setColor(Color.gray);
        g.fillRect(playerX, 550, 100, 5);

        //ball
        g.setColor(Color.white);
        g.fillOval(ballX, ballY, 20, 20);

        //game complete
        if(totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.pink);
            g.setFont(new Font("consolas", Font.BOLD, 30));
            g.drawString("You Won!!! Score : " + score, 160, 300);

            g.setColor(Color.white);
            g.setFont(new Font("calibri", Font.PLAIN, 20));
            g.drawString("Press Enter to restart", 270, 350);
        }

        //game over
        if(ballY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("consolas", Font.BOLD, 30));
            g.drawString("Game Over... Score : " + score, 160, 300);

            g.setColor(Color.white);
            g.setFont(new Font("calibri", Font.PLAIN, 20));
            g.drawString("Press Enter to restart", 270, 350);

        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))
                ballYdir = -ballYdir;
            
            A: for(int i = 0; i < map.map.length; i++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 50;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if(ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballX += ballXdir;
            ballY += ballYdir;

            if(ballX < 0)
                ballXdir = -ballXdir;
            if(ballY < 0)
                ballYdir = -ballYdir;
            if(ballX > 670)
                ballXdir = -ballXdir;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600)
                playerX = 600;
            else
                moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10)
                playerX = 10;
            else
                moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                score = 0;
                totalBricks = 28;
                delay = 8;
                playerX = 310;
                ballX = 120;
                ballY = 350;
                ballXdir = -1;
                ballYdir = -2;
                map = new MapGenerator(4, 7);
                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 15;
    }

    public void moveLeft() {
        play = true;
        playerX -= 15;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}