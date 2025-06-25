import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PascalTriangleGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PascalFrame frame = new PascalFrame();
            frame.setVisible(true);
        });
    }
}

class PascalFrame extends JFrame {
    private final JTextField rowField;
    private final JTextField colField;
    private final JTextArea outputArea;
    private static final String CPP_PROGRAM = "./PascalTriangle";

    public PascalFrame() {
        setupWindow();

        rowField = createTextField();
        colField = createTextField();
        outputArea = createTextArea();
        JButton findButton = createButton();

        JPanel controlPanel = createControlPanel(findButton);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void setupWindow() {
        setTitle("Pascal Triangle Value");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(5);
        field.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        return field;
    }

    private JTextArea createTextArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        area.setBackground(Color.WHITE);
        return area;
    }

    private JButton createButton() {
        JButton button = new JButton("Get Value");
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.addActionListener(this::handleFindValue);
        return button;
    }

    private JPanel createControlPanel(JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(173, 216, 230));

        panel.add(new JLabel("Row:"));
        panel.add(rowField);
        panel.add(new JLabel("Column:"));
        panel.add(colField);
        panel.add(button);

        return panel;
    }

    private void handleFindValue(ActionEvent event) {
        try {
            int row = Integer.parseInt(rowField.getText().trim());
            int col = Integer.parseInt(colField.getText().trim());

            Process process = Runtime.getRuntime().exec(new String[]{CPP_PROGRAM, String.valueOf(row), String.valueOf(col)});

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(process.getErrorStream()));

            String result = reader.readLine();
            String error = errorReader.readLine();

            if (error != null && !error.isEmpty()) {
                showError("C++ error: " + error);
                return;
            }

            if (result != null) {
                outputArea.setForeground(new Color(0, 100, 0));
                outputArea.setText("Value at (" + row + ", " + col + ") = " + result);
            } else {
                showError("No result returned.");
            }

        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers");
        } catch (IOException ex) {
            showError("Error executing C++ program: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        outputArea.setForeground(Color.RED);
        outputArea.setText("Error: " + message);
    }
}

