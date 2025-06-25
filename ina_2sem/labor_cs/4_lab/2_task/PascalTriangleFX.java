import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PascalTriangleFX extends Application {

    private final int MAX_ROWS = 30;
    private TextField inputField = new TextField();
    private TextArea outputArea = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Centered Pascal's Triangle");
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        
        HBox controlPanel = new HBox(10);
        controlPanel.getChildren().addAll(
            new Label("Rows (0-" + MAX_ROWS + "):"),inputField,createGenerateButton());
        
        outputArea.setEditable(false);
        outputArea.setFont(Font.font("Monospaced", 14));
        outputArea.setStyle("-fx-text-alignment: center;");
        outputArea.setWrapText(false);
        
        StackPane centerPane = new StackPane(outputArea);
        centerPane.setPadding(new Insets(5));
        
        root.getChildren().addAll(controlPanel, centerPane);
        VBox.setVgrow(centerPane, Priority.ALWAYS);
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createGenerateButton() {
        Button btn = new Button("Generate");
        btn.setOnAction(e -> {
            try {
                int rows = Integer.parseInt(inputField.getText());
                if (rows < 0 || rows > MAX_ROWS) throw new IllegalArgumentException();
                outputArea.setText(generateCenteredTriangle(rows));
            } 
            catch (Exception ex) {
                outputArea.setText("Error: Invalid input. Please enter number 0-" + MAX_ROWS);
            }
        });
        return btn;
    }

    private String generateCenteredTriangle(int rows) {
        StringBuilder result = new StringBuilder();
        
        String[] triangleRows = new String[rows + 1];
        int maxLength = 0;
        
        for (int i = 0; i <= rows; i++) {
            StringBuilder row = new StringBuilder();
            long value = 1;
            for (int j = 0; j <= i; j++) {
                row.append(String.format("%8d", value));
                value = value * (i - j) / (j + 1);
            }
            triangleRows[i] = row.toString();
            maxLength = Math.max(maxLength, triangleRows[i].length());
        }
        
        for (int i = 0; i <= rows; i++) {
            String row = triangleRows[i];
            int padding = (maxLength - row.length()) / 2;
            result.append(" ".repeat(padding)).append(row).append("\n");
        }
        
        return result.toString();
    }
}
