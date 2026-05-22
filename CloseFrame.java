import java.awt.*;
import java.awt.event.*;

import javax.swing.JLabel;

public class CloseFrame extends Frame {

    public CloseFrame() {

         JLabel l = new JLabel("Close the window to exit");
            l.setBounds(80, 80, 200, 30);
            add(l);
        setTitle("Close Frame Example");
        setSize(400, 300);
        setVisible(true);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new CloseFrame();
    }
}