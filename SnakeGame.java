import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;

public class SnakeGame extends JPanel implements ActionListener {

    // Screen Settings
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT = 700;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS =
            (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;

    static final int DEFAULT_DELAY = 100;

    // Snake Arrays
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    // Snake Data
    int bodyParts = 6;
    int applesEaten;

    // High Score
    int highScore = 0;

    // Level
    int level = 1;

    // Speed
    int speed = DEFAULT_DELAY;

    // Food
    int appleX;
    int appleY;

    // Obstacles
    int obstacleX[] = {200, 300, 400};
    int obstacleY[] = {200, 350, 500};

    // Direction
    char direction = 'R';

    // Game States
    boolean running = false;
    boolean paused = false;
    boolean darkTheme = true;
    boolean wrapMode = false;

    // Player
    String playerName;

    // Utilities
    Timer timer;
    Random random;

    SnakeGame() {

        random = new Random();

        // Difficulty Selection
        String[] options = {"Easy", "Medium", "Hard"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Select Difficulty",
                "Snake Game",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[1]
        );

        if (choice == 0) {
            speed = 140;
        } else if (choice == 1) {
            speed = 100;
        } else {
            speed = 60;
        }

        // Player Name
        playerName =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Your Name:"
                );

        if (playerName == null ||
                playerName.trim().equals("")) {

            playerName = "Player";
        }

        loadHighScore();

        this.setPreferredSize(
                new Dimension(
                        SCREEN_WIDTH,
                        SCREEN_HEIGHT
                )
        );

        this.setFocusable(true);

        this.addKeyListener(new MyKeyAdapter());

        startGame();
    }

    // Start Game
    public void startGame() {

        newApple();

        running = true;

        timer = new Timer(speed, this);

        timer.start();
    }

    // Paint
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        draw(g);
    }

    // Draw Game
    public void draw(Graphics g) {

        // Theme
        if (darkTheme) {

            setBackground(Color.black);

        } else {

            setBackground(Color.white);
        }

        if (running) {

            // Grid
            for (int i = 0;
                 i < SCREEN_HEIGHT / UNIT_SIZE;
                 i++) {

                g.setColor(new Color(40, 40, 40));

                g.drawLine(
                        i * UNIT_SIZE,
                        0,
                        i * UNIT_SIZE,
                        SCREEN_HEIGHT
                );

                g.drawLine(
                        0,
                        i * UNIT_SIZE,
                        SCREEN_WIDTH,
                        i * UNIT_SIZE
                );
            }

            // Apple
            g.setColor(Color.red);

            g.fillOval(
                    appleX,
                    appleY,
                    UNIT_SIZE,
                    UNIT_SIZE
            );

            // Obstacles
            g.setColor(Color.gray);

            for (int i = 0;
                 i < obstacleX.length;
                 i++) {

                g.fillRect(
                        obstacleX[i],
                        obstacleY[i],
                        UNIT_SIZE,
                        UNIT_SIZE
                );
            }

            // Snake
            for (int i = 0;
                 i < bodyParts;
                 i++) {

                if (i == 0) {

                    // Head
                    g.setColor(new Color(0, 255, 100));

                    g.fillRoundRect(
                            x[i],
                            y[i],
                            UNIT_SIZE,
                            UNIT_SIZE,
                            10,
                            10
                    );

                    // Eyes
                    g.setColor(Color.black);

                    g.fillOval(
                            x[i] + 5,
                            y[i] + 5,
                            5,
                            5
                    );

                    g.fillOval(
                            x[i] + 15,
                            y[i] + 5,
                            5,
                            5
                    );

                } else {

                    // Body Animation
                    if (i % 2 == 0) {

                        g.setColor(
                                new Color(0, 180, 0)
                        );

                    } else {

                        g.setColor(
                                new Color(0, 220, 0)
                        );
                    }

                    g.fillRoundRect(
                            x[i],
                            y[i],
                            UNIT_SIZE,
                            UNIT_SIZE,
                            8,
                            8
                    );
                }
            }

            // Score
            g.setColor(Color.white);

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            25
                    )
            );

            g.drawString(
                    "Score: " + applesEaten,
                    500,
                    30
            );

            // High Score
            g.setColor(Color.orange);

            g.drawString(
                    "High Score: " + highScore,
                    20,
                    30
            );

            // Speed
            g.setColor(Color.cyan);

            g.drawString(
                    "Speed: " + (150 - speed),
                    500,
                    60
            );

            // Level
            g.setColor(Color.pink);

            g.drawString(
                    "Level: " + level,
                    500,
                    90
            );

            // Player Name
            g.setColor(Color.yellow);

            g.drawString(
                    "Player: " + playerName,
                    20,
                    60
            );

            // Pause Message
            if (paused) {

                g.setColor(Color.white);

                g.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                60
                        )
                );

                g.drawString(
                        "PAUSED",
                        220,
                        350
                );
            }

        } else {

            gameOver(g);
        }
    }

    // New Apple
    public void newApple() {

        appleX = random.nextInt(
                (int)(SCREEN_WIDTH / UNIT_SIZE)
        ) * UNIT_SIZE;

        appleY = random.nextInt(
                (int)(SCREEN_HEIGHT / UNIT_SIZE)
        ) * UNIT_SIZE;
    }

    // Move Snake
    public void move() {

        for (int i = bodyParts;
             i > 0;
             i--) {

            x[i] = x[i - 1];

            y[i] = y[i - 1];
        }

        switch (direction) {

            case 'U':
                y[0] -= UNIT_SIZE;
                break;

            case 'D':
                y[0] += UNIT_SIZE;
                break;

            case 'L':
                x[0] -= UNIT_SIZE;
                break;

            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }

        // Wrap Mode
        if (wrapMode) {

            if (x[0] < 0)
                x[0] = SCREEN_WIDTH - UNIT_SIZE;

            if (x[0] >= SCREEN_WIDTH)
                x[0] = 0;

            if (y[0] < 0)
                y[0] = SCREEN_HEIGHT - UNIT_SIZE;

            if (y[0] >= SCREEN_HEIGHT)
                y[0] = 0;
        }
    }

    // Apple Check
    public void checkApple() {

        if ((x[0] == appleX) &&
                (y[0] == appleY)) {

            bodyParts++;

            applesEaten++;

            // High Score
            if (applesEaten > highScore) {

                highScore = applesEaten;

                saveHighScore();
            }

            // Levels
            level = (applesEaten / 5) + 1;

            newApple();

            // Auto Speed Increase
            if (speed > 30) {

                speed -= 2;

                timer.setDelay(speed);
            }
        }
    }

    // Collision Check
    public void checkCollisions() {

        // Body Collision
        for (int i = bodyParts;
             i > 0;
             i--) {

            if ((x[0] == x[i]) &&
                    (y[0] == y[i])) {

                running = false;
            }
        }

        // Obstacle Collision
        for (int i = 0;
             i < obstacleX.length;
             i++) {

            if (x[0] == obstacleX[i] &&
                    y[0] == obstacleY[i]) {

                running = false;
            }
        }

        // Wall Collision
        if (!wrapMode) {

            if (x[0] < 0)
                running = false;

            if (x[0] >= SCREEN_WIDTH)
                running = false;

            if (y[0] < 0)
                running = false;

            if (y[0] >= SCREEN_HEIGHT)
                running = false;
        }

        if (!running) {

            timer.stop();
        }
    }

    // Restart Game
    public void restartGame() {

        bodyParts = 6;

        applesEaten = 0;

        level = 1;

        direction = 'R';

        x[0] = 0;

        y[0] = 0;

        speed = DEFAULT_DELAY;

        timer.setDelay(speed);

        running = true;

        paused = false;

        newApple();

        timer.start();

        repaint();
    }

    // Game Over
    public void gameOver(Graphics g) {

        g.setColor(Color.red);

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        60
                )
        );

        FontMetrics metrics =
                getFontMetrics(g.getFont());

        g.drawString(
                "GAME OVER",
                (SCREEN_WIDTH -
                        metrics.stringWidth(
                                "GAME OVER"
                        )) / 2,
                SCREEN_HEIGHT / 2
        );

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        g.setColor(Color.white);

        g.drawString(
                playerName +
                        "'s Score: " +
                        applesEaten,
                220,
                420
        );

        g.drawString(
                "Press ENTER to Restart",
                180,
                470
        );

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        20
                )
        );

        g.drawString(
                "P = Pause | T = Theme | W = Wrap Mode",
                140,
                520
        );
    }

    // Save High Score
    public void saveHighScore() {

        try {

            FileWriter writer =
                    new FileWriter(
                            "highscore.txt"
                    );

            writer.write(
                    "Player: " +
                            playerName +
                            "\nHigh Score: " +
                            highScore
            );

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    // Load High Score
    public void loadHighScore() {

        try {

            File file =
                    new File("highscore.txt");

            if (file.exists()) {

                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file)
                        );

                reader.readLine();

                String scoreLine =
                        reader.readLine();

                highScore =
                        Integer.parseInt(
                                scoreLine.replace(
                                        "High Score: ",
                                        ""
                                )
                        );

                reader.close();
            }

        } catch (Exception e) {

            highScore = 0;
        }
    }

    // Game Loop
    @Override
    public void actionPerformed(ActionEvent e) {

        if (running && !paused) {

            move();

            checkApple();

            checkCollisions();
        }

        repaint();
    }

    // Keyboard Controls
    public class MyKeyAdapter
            extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:

                    if (direction != 'R')
                        direction = 'L';

                    break;

                case KeyEvent.VK_RIGHT:

                    if (direction != 'L')
                        direction = 'R';

                    break;

                case KeyEvent.VK_UP:

                    if (direction != 'D')
                        direction = 'U';

                    break;

                case KeyEvent.VK_DOWN:

                    if (direction != 'U')
                        direction = 'D';

                    break;

                // Restart
                case KeyEvent.VK_ENTER:

                    if (!running) {

                        restartGame();
                    }

                    break;

                // Pause
                case KeyEvent.VK_P:

                    paused = !paused;

                    break;

                // Increase Speed
                case KeyEvent.VK_EQUALS:

                    if (speed > 30) {

                        speed -= 10;

                        timer.setDelay(speed);
                    }

                    break;

                // Decrease Speed
                case KeyEvent.VK_MINUS:

                    if (speed < 200) {

                        speed += 10;

                        timer.setDelay(speed);
                    }

                    break;

                // Theme Toggle
                case KeyEvent.VK_T:

                    darkTheme = !darkTheme;

                    repaint();

                    break;

                // Wrap Mode
                case KeyEvent.VK_W:

                    wrapMode = !wrapMode;

                    break;
            }
        }
    }

    // Main Method
    public static void main(String[] args) {

        JFrame frame = new JFrame();

        SnakeGame gamePanel =
                new SnakeGame();

        frame.add(gamePanel);

        frame.setTitle("Snake Game Deluxe");

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setResizable(false);

        frame.pack();

        frame.setVisible(true);

        frame.setLocationRelativeTo(null);
    }
}