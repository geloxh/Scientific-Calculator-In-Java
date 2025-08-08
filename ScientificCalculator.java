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
        "^", "sqrt", "cbrt", "log", "sin", "cos", "tan",
        "asin", "acos", "atan", "!", "%", "|x|"
    };

    for (String label : buttonLabels) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        buttonPanel.add(button);
    }

    add(buttonPanel, BorderLayout.CENTER);

    pack();
    setVisible(true);

}

// Implementing ActionListener Interface

public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        String expression = textField.getText();

        switch (command) {
            case "=":
                try {
                    double result = evaluateExpression (expression);
                    textField.setText(Double.toString(result));
                } catch (ArithmeticException e) {
                    textField.setText("Error: " + e.getMessage());
                } 
                break;
            case "Clear":
                textField.setText("");
                break;
            case "<=":
                if (!expression.isEmpty()) {
                    String newExpression = expression.substring(0, expression.length() - 1);
                    textField.setText(newExpression);
                }
                break;
            default:
                textField.setText(expression + command);
                break;
        }
}

// Evaluating The Expression

private double evaluateExpression (String expression) {
    return new ExpressionParser().parse(expression);
}

// ExpressionParser Handles Parsing And Evaluating Mathematical Expressions

private static class ExpressionParser {
    private int pos = -1;
    private int ch;

    // Move To The Next Character In The Expression

    private void nextChar(String expression) {
        ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
    }

    // Skip Whitespace Characters And Check If The Current Character Matches The One To Be Eaten

    private boolean eat(int charToEat, String expression) {
        while (ch == ' ') nextChar(expression);
        if (ch == charToEat) {
            nextChar(expression);
            return true;
        }
        return false;
    }

    // Parse The Entire Expression And Return The Result

    private double parse(String expression) {
        nextChar(expression);
        double x = parseExpression(expression);
        if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
        return x;
    }

    // Parse The Expression By Evaluating The Terms And Handling Addition And Subtraction

    private double parseExpression(String expression) {
        double x = parseTerm(expression);
        while (true) {
            if (eat('+', expression)) x += parseTerm(expression);
            else if (eat('=', expression)) x -= parseTerm(expression);
            else return x;
        }
    }

    // Parse The Term By Evaluating The Factors And Handling Multiplication, Division And Exponentiation

    private double parseTerm(String expression) {
        double x = parseFactor(expression);
        while (true) {
            if (eat('*' ))
        }
    }
}