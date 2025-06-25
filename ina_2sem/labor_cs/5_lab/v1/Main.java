import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private DrawingPanel drawingPanel = new DrawingPanel();

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());
        root.setCenter(drawingPanel);

        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Graphic Editor");
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        
        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> drawingPanel.saveShapes());
        MenuItem loadItem = new MenuItem("Load");
        loadItem.setOnAction(e -> drawingPanel.loadShapes());
        fileMenu.getItems().addAll(saveItem, loadItem);

        // Shapes Menu
        Menu shapeMenu = new Menu("Shapes");
        MenuItem circleItem = new MenuItem("Circle");
        circleItem.setOnAction(e -> drawingPanel.setShapeMode(ShapeMode.CIRCLE));
        MenuItem rectItem = new MenuItem("Rectangle");
        rectItem.setOnAction(e -> drawingPanel.setShapeMode(ShapeMode.RECTANGLE));
        MenuItem polyItem = new MenuItem("Polygon");
        polyItem.setOnAction(e -> drawingPanel.setShapeMode(ShapeMode.POLYGON));
        shapeMenu.getItems().addAll(circleItem, rectItem, polyItem);

        // Help Menu
        Menu helpMenu = new Menu("Help");
        MenuItem infoItem = new MenuItem("Info");
        infoItem.setOnAction(e -> showInfo());
        helpMenu.getItems().add(infoItem);

        menuBar.getMenus().addAll(fileMenu, shapeMenu, helpMenu);
        return menuBar;
    }

    private void showInfo() {
        String instructions = """
            1. Creating Shapes:
               - Circle/Rectangle: LMB to start, drag to adjust
               - Polygon: LMB to add vertices, RMB to finalize

            2. Modifications:
               - Select: LMB click
               - Move: Drag with LMB
               - Scale: Mouse scroll
               - Rotate: Shift + drag

            3. Fill Color: RMB → ColorPicker

            4. Save/Load: File menu""";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Graphic Editor v2.0\nAuthor: Your Name");
        alert.setContentText(instructions);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
