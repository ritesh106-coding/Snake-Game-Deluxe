import java.awt.*;
import javax.swing.*;

public class DrawLine extends JFrame {

    public DrawLine() {

        setTitle("Draw Line");
        setSize(400, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {

        g.drawLine(50, 100, 300, 100);
    }

    public static void main(String[] args) {

        new DrawLine();
    }
}