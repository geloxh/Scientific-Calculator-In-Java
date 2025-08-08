// Import Packages And Main Class Setup
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScientificCalculator extends Jframe implements ActionListener;

public ScientificCalculator() {
    setTitle("Scientific Calculator In Java");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    textField = new JTextField();
    textField.setEditable(false);
    textField.setPreferredSize(new Dimension(300, 40));
    add(textField, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(9, 4));
    buttonPanel.setBackground(Color.ORANGE);
    buttonPanel.setForeground(Color.PINK);

    // Button Labels For The Calculator
    String[] buttonLabels = {
        "1", "2", "3", "/", "4", "5", "6", "*", "7", "8",
        "9", "-", "0", ".", "=", "+", "Clear", "(", ")",
        "^", "sqrt", 

    }
}