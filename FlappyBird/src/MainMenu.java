import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private JButton startButton;

    public MainMenu() {
        setTitle("Flappy Bird");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 640);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource("assets/background.png")));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);

                g.setFont(new Font("Arial", Font.BOLD, 48));
                g.setColor(Color.WHITE);
                FontMetrics fm = g.getFontMetrics();
                String title = "FLAPPY BIRD";
                int x = (getWidth() - fm.stringWidth(title)) / 2;
                g.drawString(title, x, 150);
            }
        };
        mainPanel.setLayout(null);

        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setFocusPainted(false);
        startButton.setBackground(new Color(76, 175, 80));
        startButton.setForeground(Color.WHITE);
        startButton.setBounds(90, 200, 180, 50);

        startButton.addActionListener(e -> startGame());

        mainPanel.add(startButton);
        add(mainPanel);
        setVisible(true);
    }

    private void startGame() {
        this.dispose();
        new App();
    }
}
