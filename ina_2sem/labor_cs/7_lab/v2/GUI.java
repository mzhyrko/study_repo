import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;

public class GUI extends Application {
    private TextArea outputArea = new TextArea();
    private ComboBox<String> typeSelector = new ComboBox<>();
    private TextField inputField = new TextField();

    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        connectToServer();

        typeSelector.getItems().addAll("Integer", "Double", "String");
        typeSelector.setValue("Integer");

        inputField.setPromptText("Value");

        Button insertBtn = new Button("Insert");
        Button deleteBtn = new Button("Delete");
        Button searchBtn = new Button("Search");
        Button drawBtn = new Button("Draw");

        HBox controlPanel = new HBox(10, typeSelector, inputField, insertBtn, deleteBtn, searchBtn, drawBtn);
        controlPanel.setAlignment(Pos.CENTER);
        outputArea.setEditable(false);
        outputArea.setFont(javafx.scene.text.Font.font("monospaced", 14));

        insertBtn.setOnAction(e -> send("insert"));
        deleteBtn.setOnAction(e -> send("delete"));
        searchBtn.setOnAction(e -> send("search"));
        drawBtn.setOnAction(e -> send("draw"));

        VBox root = new VBox(10, controlPanel, outputArea);
        VBox.setVgrow(outputArea, Priority.ALWAYS);
        root.setStyle("-fx-padding: 15");

        stage.setScene(new Scene(root, 700, 500));
        stage.setTitle("Binary Tree Client");
        stage.show();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 8888);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            outputArea.setText("Connection error.");
        }
    }

    private void send(String operation) {
        String treeType = typeSelector.getValue();
        String value = inputField.getText().trim();
        if (!operation.equals("draw") && value.isEmpty()) {
            outputArea.setText("Please enter a value.");
            return;
        }
        out.println(treeType + ";" + operation + (operation.equals("draw") ? "" : ";" + value));
        try {
            String line, result = "";
            while ((line = in.readLine()) != null) {
                result += line + "\n";
                if (!in.ready()) break;
            }
            outputArea.setText(result);
        } catch (IOException e) {
            outputArea.setText("Communication error.");
        }
    }
}

