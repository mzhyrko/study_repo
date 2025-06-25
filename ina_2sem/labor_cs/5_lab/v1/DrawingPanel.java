// DrawingPanel.java
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.scene.Node;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends Pane {
    private List<CustomShape> shapes = new ArrayList<>();
    private ShapeMode mode = ShapeMode.NONE;
    private CustomShape selectedShape;
    private Polygon polygonInProgress;
    private ColorPicker colorPicker = new ColorPicker();

    public DrawingPanel() {
        initHandlers();
        colorPicker.setOnAction(e -> {
            if (selectedShape != null) selectedShape.setFill(colorPicker.getValue());
        });
    }

    public void setShapeMode(ShapeMode mode) {
        this.mode = mode;
        if (mode == ShapeMode.POLYGON) startPolygon();
    }

    private void initHandlers() {
        // Обработчик создания фигур
        setOnMousePressed(e -> {
            if (mode == ShapeMode.CIRCLE) {
                selectedShape = new CircleShape(e.getX(), e.getY());
                shapes.add(selectedShape);
                getChildren().add(selectedShape.getNode());
            } else if (mode == ShapeMode.RECTANGLE) {
                selectedShape = new RectShape(e.getX(), e.getY());
                shapes.add(selectedShape);
                getChildren().add(selectedShape.getNode());
            }
        });

        // Обработчик перемещения/вращения
        setOnMouseDragged(e -> {
            if (selectedShape != null) {
                if (e.isShiftDown()) {
                    double angle = Math.toDegrees(Math.atan2(
                        e.getY() - selectedShape.getCenterY(),
                        e.getX() - selectedShape.getCenterX()
                    ));
                    selectedShape.addTransform(new Rotate(angle, 
                        selectedShape.getCenterX(), 
                        selectedShape.getCenterY()));
                } else {
                    selectedShape.update(e.getX(), e.getY());
                }
            }
        });

        // Обработчик масштабирования
        setOnScroll(e -> {
            if (selectedShape != null) {
                double factor = 1 + e.getDeltaY() * 0.001;
                selectedShape.addTransform(new Scale(factor, factor, 
                    selectedShape.getCenterX(), 
                    selectedShape.getCenterY()));
            }
        });

        // Контекстное меню
        setOnContextMenuRequested(e -> colorPicker.show());
    }

    private void startPolygon() {
        polygonInProgress = new Polygon();
        getChildren().add(polygonInProgress);
        
        setOnMouseClicked(e -> {
            if (e.getButton().name().equals("PRIMARY")) {
                polygonInProgress.getPoints().addAll(e.getX(), e.getY());
            } else if (e.getButton().name().equals("SECONDARY")) {
                shapes.add(new PolyShape(polygonInProgress));
                getChildren().remove(polygonInProgress);
                mode = ShapeMode.NONE;
            }
        });
    }

    public void saveShapes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("shapes.dat"))) {
            oos.writeObject(shapes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadShapes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("shapes.dat"))) {
            shapes = (List<CustomShape>) ois.readObject();
            getChildren().clear();
            shapes.forEach(shape -> getChildren().add(shape.getNode()));
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}

enum ShapeMode { NONE, CIRCLE, RECTANGLE, POLYGON }

abstract class CustomShape implements Serializable {
    protected transient Color fillColor = Color.BLUE;
    public abstract void update(double x, double y);
    public abstract double getCenterX();
    public abstract double getCenterY();
    public abstract Node getNode();
    public abstract void setFill(Color color);
    public abstract void addTransform(Transform transform);
}

// Реализация CircleShape
class CircleShape extends CustomShape {
    private transient Circle circle = new Circle();
    private double centerX, centerY, radius;

    public CircleShape(double x, double y) {
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(1);
        circle.setFill(Color.BLUE);
    }

    @Override
    public void update(double x, double y) {
        double dx = x - circle.getCenterX();
        double dy = y - circle.getCenterY();
        circle.setRadius(Math.hypot(dx, dy));
    }

    @Override
    public Node getNode() { return circle; }

    @Override
    public double getCenterX() { return circle.getCenterX(); }

    @Override
    public double getCenterY() { return circle.getCenterY(); }

    @Override
    public void setFill(Color color) { circle.setFill(color); }

    @Override
    public void addTransform(Transform transform) {
        circle.getTransforms().add(transform);
    }

    // Сериализация
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeDouble(circle.getCenterX());
        oos.writeDouble(circle.getCenterY());
        oos.writeDouble(circle.getRadius());
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        circle = new Circle(
            ois.readDouble(), 
            ois.readDouble(), 
            ois.readDouble()
        );
        circle.setFill(Color.BLUE);
    }
}

// Реализация RectShape
class RectShape extends CustomShape {
    private transient Rectangle rect = new Rectangle();
    private double startX, startY;

    public RectShape(double x, double y) {
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(1);
        rect.setHeight(1);
        rect.setFill(Color.BLUE);
    }

    @Override
    public void update(double x, double y) {
        rect.setWidth(Math.abs(x - rect.getX()));
        rect.setHeight(Math.abs(y - rect.getY()));
    }

    @Override
    public Node getNode() { return rect; }

    @Override
    public double getCenterX() { return rect.getX() + rect.getWidth()/2; }

    @Override
    public double getCenterY() { return rect.getY() + rect.getHeight()/2; }

    @Override
    public void setFill(Color color) { rect.setFill(color); }

    @Override
    public void addTransform(Transform transform) {
        rect.getTransforms().add(transform);
    }

    // Сериализация
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeDouble(rect.getX());
        oos.writeDouble(rect.getY());
        oos.writeDouble(rect.getWidth());
        oos.writeDouble(rect.getHeight());
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        rect = new Rectangle(
            ois.readDouble(), 
            ois.readDouble(), 
            ois.readDouble(), 
            ois.readDouble()
        );
        rect.setFill(Color.BLUE);
    }
}

// Реализация PolyShape
class PolyShape extends CustomShape {
    private transient Polygon polygon = new Polygon();

    public PolyShape(Polygon poly) {
        polygon.getPoints().addAll(poly.getPoints());
        polygon.setFill(Color.BLUE);
    }

    @Override
    public void update(double x, double y) {}

    @Override
    public Node getNode() { return polygon; }

    @Override
    public double getCenterX() { 
        return polygon.getBoundsInLocal().getCenterX(); 
    }

    @Override
    public double getCenterY() { 
        return polygon.getBoundsInLocal().getCenterY(); 
    }

    @Override
    public void setFill(Color color) { polygon.setFill(color); }

    @Override
    public void addTransform(Transform transform) {
        polygon.getTransforms().add(transform);
    }

    // Сериализация
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(new ArrayList<>(polygon.getPoints()));
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        polygon = new Polygon();
        polygon.getPoints().addAll((List<Double>) ois.readObject());
        polygon.setFill(Color.BLUE);
    }
}
