import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class PascalTriangleApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PascalFrame frame = new PascalFrame();
            frame.setVisible(true);
        });
    }
}

class PascalFrame extends JFrame {
    private final JTextField inputField;
    private final JTextArea outputArea;
    private static final int MAX_SAFE_ROW = 60;
    
    public PascalFrame() {
        setupWindow();
        inputField = createTextField();
        outputArea = createTextArea();
        JButton generateButton = createButton();
        
        JPanel controlPanel = createControlPanel(inputField, generateButton);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void setupWindow() {
        setTitle("Pascal's Triangle Generator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(10);
        field.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        return field;
    }

    private JTextArea createTextArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setBackground(Color.WHITE);
        return area;
    }

    private JButton createButton() {
        JButton button = new JButton("Generate");
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.addActionListener(this::handleGeneration);
        return button;
    }

    private JPanel createControlPanel(JTextField input, JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(173, 216, 230));
        panel.add(new JLabel("Enter rows (0-" + MAX_SAFE_ROW + "):"));
        panel.add(input);
        panel.add(button);
        return panel;
    }

    private void handleGeneration(ActionEvent event) {
        try {
            int rows = Integer.parseInt(inputField.getText().trim());
            validateInput(rows);
            outputArea.setForeground(new Color(0, 100, 0));
            outputArea.setText(TriangleBuilder.generate(rows));
        } catch (NumberFormatException ex) {
            showError("Invalid number format");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void validateInput(int value) {
        if (value < 0 || value > MAX_SAFE_ROW) {
            throw new IllegalArgumentException("Input must be between 0 and " + MAX_SAFE_ROW);
        }
    }

    private void showError(String message) {
        outputArea.setForeground(Color.RED);
        outputArea.setText("Error: " + message + "\n" + "Max safe row: " + MAX_SAFE_ROW + "\n" + "Long.MAX_VALUE: " + Long.MAX_VALUE);
    }
}

class TriangleBuilder {
    
    public static String generate(int rows) {
        List<String> allRows = new ArrayList<>();
        int maxRowWidth = calculateMaxRowWidth(rows);
        
        for (int i = 0; i <= rows; i++) {
            allRows.add(buildRow(i, rows, maxRowWidth));
        }
        return String.join("\n", allRows);
    }

    private static int calculateMaxRowWidth(int totalRows) {
        // Calculate width of the middle element in the last row
        long maxValue = 1L;
        int n = totalRows;
        for (int k = 0; k < n/2; k++) {
            maxValue = maxValue * (n - k) / (k + 1);
        }
        return String.valueOf(maxValue).length();
    }

    private static String buildRow(int rowNum, int maxRows, int numberWidth) {
        StringBuilder row = new StringBuilder();
        List<String> numbers = new ArrayList<>();
        
        long current = 1L;
        for (int col = 0; col <= rowNum; col++) {
            String numStr = centerAlign(String.valueOf(current), numberWidth);
            numbers.add(numStr);
            current = current * (rowNum - col) / (col + 1);
        }

        String joinedRow = String.join("  ", numbers);
        int totalPadding = (calculateMaxRowWidth(maxRows) + 2) * (maxRows - rowNum);
        row.append(" ".repeat(totalPadding / 2));
        row.append(joinedRow);
        return row.toString();
    }

    private static String centerAlign(String text, int width) {
        if (text.length() >= width) return text;
        int padding = width - text.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }
}
