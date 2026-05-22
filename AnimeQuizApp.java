import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimeQuizApp extends JFrame implements ActionListener {

    // Questions
    String[] questions = {

            "Who is known as the Pirate King candidate in One Piece?",
            "Who is Naruto's rival?",
            "What is the name of Goku's strongest transformation?",
            "Which anime features Titans?",
            "Who is the captain of Team 7 in Naruto?",
            "What is Luffy's dream?",
            "Who killed Itachi Uchiha?",
            "Which anime has a notebook that kills people?",
            "Who is the main character of Demon Slayer?",
            "What is the name of Tanjiro's sister?",
            "Who uses Rasengan?",
            "What is Ichigo's sword called?",
            "Who trained Gon in Hunter x Hunter?",
            "Which anime features Edward Elric?",
            "Who is the strongest hero in One Punch Man?",
            "Who is known as the Copy Ninja?",
            "Which anime has Dragon Balls?",
            "Who is Light Yagami's rival detective?",
            "What is Naruto's fox spirit called?",
            "Who wants to become Wizard King in Black Clover?"
    };

    // Options
    String[][] options = {

            {"Zoro", "Luffy", "Shanks", "Ace"},
            {"Sasuke", "Gaara", "Rock Lee", "Kakashi"},
            {"Super Saiyan", "Ultra Instinct", "Kaioken", "Super Saiyan 2"},
            {"Bleach", "Death Note", "Attack on Titan", "Tokyo Ghoul"},
            {"Jiraiya", "Kakashi", "Iruka", "Minato"},
            {"Become Hokage", "Find One Piece", "Become Marine", "Defeat Naruto"},
            {"Naruto", "Obito", "Sasuke", "Madara"},
            {"Death Note", "Naruto", "Bleach", "One Piece"},
            {"Zenitsu", "Tanjiro", "Inosuke", "Rengoku"},
            {"Hinata", "Nezuko", "Sakura", "Mitsuri"},
            {"Pain", "Naruto", "Sasuke", "Madara"},
            {"Zangetsu", "Ryujin Jakka", "Senbonzakura", "Nozarashi"},
            {"Killua", "Hisoka", "Wing", "Biscuit"},
            {"Jujutsu Kaisen", "Naruto", "Fullmetal Alchemist", "Bleach"},
            {"Genos", "Garou", "Saitama", "Bang"},
            {"Naruto", "Kakashi", "Might Guy", "Minato"},
            {"Naruto", "Dragon Ball", "Bleach", "One Piece"},
            {"Near", "Ryuk", "L", "Mello"},
            {"Shukaku", "Kurama", "Gyuki", "Matatabi"},
            {"Yami", "Asta", "Noelle", "Yuno"}
    };

    // Correct Answers
    char[] answers = {

            'B',
            'A',
            'B',
            'C',
            'B',
            'B',
            'C',
            'A',
            'B',
            'B',
            'B',
            'A',
            'C',
            'C',
            'C',
            'B',
            'B',
            'C',
            'B',
            'B'
    };

    // User Answers
    char[] userAnswers = new char[20];

    JLabel titleLabel;
    JLabel questionLabel;
    JLabel timerLabel;
    JLabel scoreLabel;

    JRadioButton option1;
    JRadioButton option2;
    JRadioButton option3;
    JRadioButton option4;

    ButtonGroup group;

    JButton nextButton;

    Timer timer;

    int currentQuestion = 0;
    int score = 0;
    int timeLeft = 15;

    AnimeQuizApp() {

        setTitle("Anime Quiz Game");

        setSize(900, 650);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(20, 20, 40));

        // Title
        titleLabel = new JLabel("🔥 ANIME QUIZ GAME 🔥");

        titleLabel.setBounds(250, 20, 500, 40);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        titleLabel.setForeground(Color.ORANGE);

        add(titleLabel);

        // Question Label
        questionLabel = new JLabel();

        questionLabel.setBounds(50, 100, 800, 40);

        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));

        questionLabel.setForeground(Color.WHITE);

        add(questionLabel);

        // Options
        option1 = createOptionButton();
        option2 = createOptionButton();
        option3 = createOptionButton();
        option4 = createOptionButton();

        option1.setBounds(100, 180, 500, 40);
        option2.setBounds(100, 250, 500, 40);
        option3.setBounds(100, 320, 500, 40);
        option4.setBounds(100, 390, 500, 40);

        add(option1);
        add(option2);
        add(option3);
        add(option4);

        // Group
        group = new ButtonGroup();

        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        // Next Button
        nextButton = new JButton("NEXT ➜");

        nextButton.setBounds(650, 500, 150, 50);

        nextButton.setFont(new Font("Arial", Font.BOLD, 18));

        nextButton.setBackground(Color.ORANGE);

        nextButton.setForeground(Color.BLACK);

        nextButton.setFocusPainted(false);

        nextButton.addActionListener(this);

        add(nextButton);

        // Timer Label
        timerLabel = new JLabel("⏰ Time Left: 15");

        timerLabel.setBounds(650, 120, 200, 40);

        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));

        timerLabel.setForeground(Color.CYAN);

        add(timerLabel);

        // Score Label
        scoreLabel = new JLabel("⭐ Score: 0");

        scoreLabel.setBounds(650, 180, 200, 40);

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        scoreLabel.setForeground(Color.GREEN);

        add(scoreLabel);

        // Load first question
        loadQuestion();

        // Timer
        timer = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                timeLeft--;

                timerLabel.setText("⏰ Time Left: " + timeLeft);

                if (timeLeft == 0) {

                    saveAnswer();

                    nextQuestion();
                }
            }
        });

        timer.start();

        setVisible(true);
    }

    // Stylish Radio Button
    JRadioButton createOptionButton() {

        JRadioButton button = new JRadioButton();

        button.setFont(new Font("Arial", Font.BOLD, 18));

        button.setBackground(new Color(40, 40, 70));

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        return button;
    }

    // Load Question
    void loadQuestion() {

        timeLeft = 15;

        timerLabel.setText("⏰ Time Left: " + timeLeft);

        questionLabel.setText((currentQuestion + 1)
                + ". " + questions[currentQuestion]);

        option1.setText("A. " + options[currentQuestion][0]);

        option2.setText("B. " + options[currentQuestion][1]);

        option3.setText("C. " + options[currentQuestion][2]);

        option4.setText("D. " + options[currentQuestion][3]);

        group.clearSelection();
    }

    // Save Answer
    void saveAnswer() {

        if (option1.isSelected())
            userAnswers[currentQuestion] = 'A';

        else if (option2.isSelected())
            userAnswers[currentQuestion] = 'B';

        else if (option3.isSelected())
            userAnswers[currentQuestion] = 'C';

        else if (option4.isSelected())
            userAnswers[currentQuestion] = 'D';
    }

    // Next Question
    void nextQuestion() {

        if (userAnswers[currentQuestion]
                == answers[currentQuestion]) {

            score++;

            scoreLabel.setText("⭐ Score: " + score);
        }

        currentQuestion++;

        if (currentQuestion < questions.length) {

            loadQuestion();
        }

        else {

            showResult();
        }
    }

    // Show Result
    void showResult() {

        timer.stop();

        int percentage =
                (score * 100) / questions.length;

        String result =

                "🔥 QUIZ FINISHED 🔥\n\n" +
                "⭐ Correct Answers: " + score + "\n" +
                "📚 Total Questions: " + questions.length + "\n" +
                "🏆 Percentage: " + percentage + "%";

        JOptionPane.showMessageDialog(this, result);

        System.exit(0);
    }

    // Button Click
    public void actionPerformed(ActionEvent e) {

        saveAnswer();

        nextQuestion();
    }

    // We Call main method
    public static void main(String[] args) {

        new AnimeQuizApp();
    }
}