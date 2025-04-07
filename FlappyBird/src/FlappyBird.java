import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    // image attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer pipesCooldown;

    int gravity = 1;

    // Game state
    boolean isGameOver = false;

    // Score tracking
    int score = 0;
    JLabel scoreLabel;

    // Track pipe pairs for scoring
    ArrayList<Integer> scoredPipePairs = new ArrayList<>();

    // Font for game over message
    Font gameOverFont = new Font("Arial", Font.BOLD, 30);
    Font restartFont = new Font("Arial", Font.PLAIN, 18);

    // constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);
        setLayout(null); // Using null layout for absolute positioning

        // load images
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        // Create score label
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(10, 10, 150, 30);
        add(scoreLabel);

        initGame();
    }

    private void initGame() {
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = getPipes();
        scoredPipePairs = new ArrayList<>();
        score = 0;
        isGameOver = false;
        updateScoreDisplay();

        if (pipesCooldown != null) {
            pipesCooldown.stop();
        }

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isGameOver) {
                    placePipes();
                }
            }
        });
        pipesCooldown.start();

        if (gameLoop != null) {
            gameLoop.stop();
        }

        gameLoop = new Timer(1000 / 60, e -> {
            if (!isGameOver) {
                move();
                checkCollision();
                checkScore();
            }
            repaint();
        });
        gameLoop.start();
    }

    private static ArrayList<Pipe> getPipes() {
        return new ArrayList<Pipe>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(),
                    pipe.getHeight(), null);
        }

        if (isGameOver) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, frameWidth, frameHeight);

            int centerY = frameHeight / 2;

            // Game Over text
            g.setFont(gameOverFont);
            FontMetrics fm = g.getFontMetrics();
            String gameOverText = "Game Over";
            int textWidth = fm.stringWidth(gameOverText);
            int textHeight = fm.getHeight();
            g.setColor(Color.WHITE);
            g.drawString(gameOverText, (frameWidth - textWidth) / 2, centerY - textHeight);

            // Final Score
            String finalScoreText = "Score: " + score;
            g.setFont(new Font("Arial", Font.BOLD, 24));
            fm = g.getFontMetrics();
            textWidth = fm.stringWidth(finalScoreText);
            g.drawString(finalScoreText, (frameWidth - textWidth) / 2, centerY - textHeight - 40);

            // Restart instruction
            String restartText = "Press 'R' to restart";
            g.setFont(restartFont);
            fm = g.getFontMetrics();
            textWidth = fm.stringWidth(restartText);
            g.drawString(restartText, (frameWidth - textWidth) / 2, centerY + textHeight);
        }
    }


    public void move() {
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // Check if player has fallen to the bottom
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            gameOver();
        }

        ArrayList<Pipe> pipesToRemove = new ArrayList<>();
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            // Mark pipes for removal if they're off-screen
            if (pipe.getPosX() + pipe.getWidth() < 0) {
                pipesToRemove.add(pipe);
            }
        }

        // Remove pipes that are off-screen
        pipes.removeAll(pipesToRemove);
    }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = frameHeight/4;

        // Generate unique ID for this pipe pair
        int pipeId = (int)(Math.random() * 10000);

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        upperPipe.setId(pipeId);
        upperPipe.setVelocityX(-4); // Make sure velocity is negative and consistent
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        lowerPipe.setId(pipeId);
        lowerPipe.setVelocityX(-4); // Make sure velocity is negative and consistent
        pipes.add(lowerPipe);
    }

    public void checkCollision() {
        // Create a Rectangle for the player for more accurate collision detection
        Rectangle playerRect = new Rectangle(
                player.getPosX(),
                player.getPosY(),
                player.getWidth(),
                player.getHeight()
        );

        // Check collision with pipes
        for (Pipe pipe : pipes) {
            // Create a Rectangle for the pipe
            Rectangle pipeRect = new Rectangle(
                    pipe.getPosX(),
                    pipe.getPosY(),
                    pipe.getWidth(),
                    pipe.getHeight()
            );

            // Check for intersection
            if (playerRect.intersects(pipeRect)) {
                gameOver();
                return;
            }
        }
    }

    public void checkScore() {
        for (Pipe pipe : pipes) {
            // Check if this is an upper pipe (to avoid counting twice)
            if (pipe.getPosY() < frameHeight / 2) {
                // Check if the pipe pair has been passed but not scored yet
                int pipeId = pipe.getId();
                if (!scoredPipePairs.contains(pipeId) && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                    scoredPipePairs.add(pipeId); // Mark this pipe pair as scored
                    score++;
                    updateScoreDisplay();
                }
            }
        }
    }

    private void updateScoreDisplay() {
        scoreLabel.setText("Score: " + score);
    }

    public void gameOver() {
        isGameOver = true;
        gameLoop.stop();
        pipesCooldown.stop();
    }

    public void restartGame() {
        initGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            move();
            checkCollision();
            checkScore();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isGameOver) {
            player.setVelocityY(-9); // Consistent jump height
        } else if (e.getKeyCode() == KeyEvent.VK_R && isGameOver) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}