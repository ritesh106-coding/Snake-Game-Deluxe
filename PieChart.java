import java.awt.*;
import javax.swing.*;

public class PieChart extends JFrame {

    public PieChart() {

        setTitle("Pie Chart");
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {

        g.setColor(Color.RED);
        g.fillArc(100, 100, 200, 200, 0, 90);

        g.setColor(Color.BLUE);
        g.fillArc(100, 100, 200, 200, 90, 120);

        g.setColor(Color.GREEN);
        g.fillArc(100, 100, 200, 200, 210, 150);
    }

    public static void main(String[] args) {
        new PieChart();
    }
}