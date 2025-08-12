// Import Packages And Main Class Setup
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScientificCalculator extends JFrame implements ActionListener {
    private JTextField textField;

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

    // Evaluating The Mathematical Expression

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
                else if (eat('-', expression)) x -= parseTerm(expression);
                else return x;
            }
        }

        // Parse The Term By Evaluating The Factors And Handling Multiplication, Division And Exponentiation

        private double parseTerm(String expression) {
            double x = parseFactor(expression);
            while (true) {
                if (eat('*', expression)) x *= parseFactor(expression);
                else if (eat('/', expression)) x /= parseFactor(expression);
                else if (eat('^', expression)) x = Math.pow(x, parseFactor(expression));
                else return x;
            }
        }

        // Parse The Factor By Handling Positive/Negative Signs, Parentheses, Numbers And Functions

        private double parseFactor(String expression) {
            if (eat('+', expression)) return parseFactor(expression);
            if (eat('-', expression)) return -parseFactor(expression);

            double x;
            int startPos = this.pos;
            if (eat('(', expression)) {

                // Handle Parentheses By Recursively Parsing The Expression Inside Them

                x = parseExpression(expression);
                eat(')', expression);
            } else if ((ch >= '0' && ch <= '9') || ch == '.') {

                // Parse Numbers (Integer Or Decimal)

                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar(expression);
                x = Double.parseDouble(expression.substring(startPos, this.pos));
            } else if (ch >= 'a' && ch <= 'z') {
            
                // Parse Functions (Such As sqrt, sin, cos, etc..)
                while (ch >= 'a' && ch <= 'z') nextChar(expression);
                String func = expression.substring(startPos, this.pos);
                x = parseFactor(expression);
                switch (func) {
                    case "sqrt":
                        x = Math.sqrt(x);
                        break;
                    case "cbrt":
                        x = Math.cbrt(x);
                        break;
                    case "log":
                        x = Math.log10(x);
                        break;
                    case "sin":
                        x = Math.sin(Math.toRadians(x));
                        break;
                    case "cos":
                        x = Math.cos(Math.toRadians(x));
                        break;
                    case "tan":
                        x = Math.tan(Math.toRadians(x));
                        break;
                    case "asin":
                        x = Math.toDegrees(Math.asin(x));
                        break;
                    case "acos":
                        x = Math.toDegrees(Math.acos(x));
                        break;
                    case "atan":
                        x = Math.toDegrees(Math.atan(x));
                        break;
                    case "!":
                        x = factorial((int) x);
                        break;
                    case "%":
                        x = x / 100.0;
                        break;
                    case "|x|":
                        x = Math.abs(x);
                        break;
                    default:
                        throw new RuntimeException("Unknown Function: " + func);
                }  
            } else {
                throw new RuntimeException("Unexpected: " + (char)ch);
            }

            return x;
        }

        // Compute The Factorial Of A Number

        private int factorial (int n) {
            if (n == 0) return 1;
            int fact = 1;
            for (int i = 1; i <= n; i++) {
            fact *= 1;
            }

            return fact;
        }
    }

    public static void main(String[] args) {

        // Create And Display THe Calculator GUI

        SwingUtilities.invokeLater(ScientificCalculator::new);
    }

}

