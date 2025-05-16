import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.ArrayList;

/**
 * Main application class for Graphic Editor.
 * Provides GUI for creating and editing geometric shapes.
 */
public class GraphicEditorApp extends JFrame {

    /** Enum representing available shape types */
    private enum ShapeType { RECTANGLE, CIRCLE, POLYGON }

    private ShapeType selectedShape = ShapeType.RECTANGLE;
    private final DrawingPanel drawingPanel;

    /**
     * Constructs the main application window and initializes components.
     */
    public GraphicEditorApp() {
        super("Graphic Editor");

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem instructions = new JMenuItem("Instructions");
        JMenuItem info = new JMenuItem("Info");

        save.addActionListener(e -> drawingPanel.saveDrawing());
        load.addActionListener(e -> drawingPanel.loadDrawing());
        instructions.addActionListener(e -> showInstructions());
        info.addActionListener(e -> showInfo());

        fileMenu.add(save);
        fileMenu.add(load);
        fileMenu.add(instructions);
        fileMenu.addSeparator();
        fileMenu.add(info);

        JMenu shapesMenu = new JMenu("Shapes");
        String[] shapes = { "Rectangle", "Circle", "Polygon" };
        for (String s : shapes) {
            JMenuItem item = new JMenuItem(s);
            item.addActionListener(e -> {
                switch (s) {
                    case "Rectangle" -> selectedShape = ShapeType.RECTANGLE;
                    case "Circle" -> selectedShape = ShapeType.CIRCLE;
                    case "Polygon" -> selectedShape = ShapeType.POLYGON;
                }
            });
            shapesMenu.add(item);
        }

        menuBar.add(fileMenu);
        menuBar.add(shapesMenu);
        setJMenuBar(menuBar);

        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Displays user instructions dialog.
     */
    private void showInstructions() {
        JOptionPane.showMessageDialog(this,
                """
                Left Click — draw or drag a shape.
                For Polygon: click multiple points with Left Click, finish with Right Click.
                Right Click — finish polygon or change shape color.
                Mouse Wheel — resize shape.
                Arrow Keys — rotate selected shape.
                """,
                "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays program information dialog.
     */
    private void showInfo() {
        JOptionPane.showMessageDialog(this,
                "Graphic Editor\nVersion 2.4\nAuthor: Mikhail Zhyrko\nPurpose: Create and edit geometric shapes",
                "Program Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Custom JPanel for drawing shapes and handling user interactions.
     */
    private class DrawingPanel extends JPanel {
        private final ArrayList<ColoredShape> shapes = new ArrayList<>();
        private ArrayList<Point> polygonPoints = new ArrayList<>();
        private ColoredShape selectedShapeForEdit = null;
        private Point dragOffset = null;

        public DrawingPanel() {
            setBackground(Color.WHITE);
            setFocusable(true);
            requestFocusInWindow();

            MouseAdapter handler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    requestFocusInWindow();

                    if (SwingUtilities.isLeftMouseButton(e)) {
                        for (ColoredShape cs : shapes) {
                            if (cs.shape.contains(e.getPoint())) {
                                selectedShapeForEdit = cs;
                                Rectangle bounds = cs.shape.getBounds();
                                dragOffset = new Point(e.getX() - bounds.x, e.getY() - bounds.y);
                                return;
                            }
                        }

                        if (selectedShape == ShapeType.POLYGON) {
                            polygonPoints.add(e.getPoint());
                            repaint();
                        } else {
                            Color color = JColorChooser.showDialog(null, "Choose Color", Color.BLACK);
                            Shape shape = switch (selectedShape) {
                                case RECTANGLE -> new Rectangle(e.getX() - 25, e.getY() - 25, 50, 50);
                                case CIRCLE -> new Ellipse2D.Float(e.getX() - 25, e.getY() - 25, 50, 50);
                                default -> null;
                            };
                            if (shape != null) {
                                ColoredShape cs = new ColoredShape(shape, color);
                                shapes.add(cs);
                                selectedShapeForEdit = cs;
                                repaint();
                            }
                        }
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        if (selectedShape == ShapeType.POLYGON && polygonPoints.size() > 2) {
                            Polygon poly = new Polygon();
                            polygonPoints.forEach(p -> poly.addPoint(p.x, p.y));
                            Color color = JColorChooser.showDialog(null, "Choose Polygon Color", Color.BLACK);
                            ColoredShape cs = new ColoredShape(poly, color);
                            shapes.add(cs);
                            selectedShapeForEdit = cs;
                            polygonPoints.clear();
                            repaint();
                        } else {
                            for (ColoredShape cs : shapes) {
                                if (cs.shape.contains(e.getPoint())) {
                                    Color color = JColorChooser.showDialog(null, "Choose New Color", cs.color);
                                    if (color != null) {
                                        cs.color = color;
                                        repaint();
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (selectedShapeForEdit != null && dragOffset != null) {
                        Rectangle bounds = selectedShapeForEdit.shape.getBounds();
                        int newX = e.getX() - dragOffset.x;
                        int newY = e.getY() - dragOffset.y;

                        Shape shape = selectedShapeForEdit.shape;
                        if (shape instanceof Rectangle r) {
                            selectedShapeForEdit.shape = new Rectangle(newX, newY, r.width, r.height);
                        } else if (shape instanceof Ellipse2D.Float ell) {
                            selectedShapeForEdit.shape = new Ellipse2D.Float(newX, newY, ell.width, ell.height);
                        } else if (shape instanceof Polygon poly) {
                            int dx = newX - bounds.x;
                            int dy = newY - bounds.y;
                            for (int i = 0; i < poly.npoints; i++) {
                                poly.xpoints[i] += dx;
                                poly.ypoints[i] += dy;
                            }
                            poly.invalidate();
                        }
                        repaint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    selectedShapeForEdit = null;
                    dragOffset = null;
                }

                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    for (ColoredShape cs : shapes) {
                        if (cs.shape.contains(e.getPoint())) {
                            double scale = e.getWheelRotation() < 0 ? 1.1 : 0.9;
                            Rectangle bounds = cs.shape.getBounds();
                            int newW = (int)(bounds.width * scale);
                            int newH = (int)(bounds.height * scale);
                            int newX = bounds.x + (bounds.width - newW) / 2;
                            int newY = bounds.y + (bounds.height - newH) / 2;

                            if (cs.shape instanceof Rectangle) {
                                cs.shape = new Rectangle(newX, newY, newW, newH);
                            } else if (cs.shape instanceof Ellipse2D.Float) {
                                cs.shape = new Ellipse2D.Float(newX, newY, newW, newH);
                            } else if (cs.shape instanceof Polygon poly) {
                                for (int i = 0; i < poly.npoints; i++) {
                                    poly.xpoints[i] = (int)((poly.xpoints[i] - bounds.getCenterX()) * scale + bounds.getCenterX());
                                    poly.ypoints[i] = (int)((poly.ypoints[i] - bounds.getCenterY()) * scale + bounds.getCenterY());
                                }
                                poly.invalidate();
                            }

                            repaint();
                            break;
                        }
                    }
                }
            };

            addMouseListener(handler);
            addMouseMotionListener(handler);
            addMouseWheelListener(handler);

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (selectedShapeForEdit != null) {
                        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                            selectedShapeForEdit.rotationAngle -= 10;
                        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                            selectedShapeForEdit.rotationAngle += 10;
                        }
                        repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            for (ColoredShape cs : shapes) {
                Graphics2D g2Copy = (Graphics2D) g2.create();
                g2Copy.setColor(cs.color);
                Rectangle bounds = cs.shape.getBounds();
                g2Copy.rotate(Math.toRadians(cs.rotationAngle), bounds.getCenterX(), bounds.getCenterY());
                g2Copy.fill(cs.shape);
                g2Copy.dispose();
            }

            if (!polygonPoints.isEmpty()) {
                g2.setColor(Color.GRAY);
                for (int i = 0; i < polygonPoints.size() - 1; i++) {
                    Point p1 = polygonPoints.get(i);
                    Point p2 = polygonPoints.get(i + 1);
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        public void saveDrawing() {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()))) {
                    out.writeObject(shapes);
                    JOptionPane.showMessageDialog(this, "Saved successfully.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to save: " + ex.getMessage());
                }
            }
        }

        public void loadDrawing() {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()))) {
                    shapes.clear();
                    shapes.addAll((ArrayList<ColoredShape>) in.readObject());
                    repaint();
                    JOptionPane.showMessageDialog(this, "Loaded successfully.");
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to load: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Represents a shape with color and rotation properties.
     */
    private static class ColoredShape implements Serializable {
        transient Shape shape;
        Color color;
        double rotationAngle = 0;

        public ColoredShape(Shape shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            
            if (shape instanceof Rectangle) {
                out.writeUTF("Rectangle");
                Rectangle r = (Rectangle) shape;
                out.writeInt(r.x);
                out.writeInt(r.y);
                out.writeInt(r.width);
                out.writeInt(r.height);
            } else if (shape instanceof Ellipse2D.Float) {
                out.writeUTF("Ellipse");
                Ellipse2D.Float e = (Ellipse2D.Float) shape;
                out.writeFloat(e.x);
                out.writeFloat(e.y);
                out.writeFloat(e.width);
                out.writeFloat(e.height);
            } else if (shape instanceof Polygon) {
                out.writeUTF("Polygon");
                Polygon p = (Polygon) shape;
                out.writeObject(p.xpoints);
                out.writeObject(p.ypoints);
                out.writeInt(p.npoints);
            } else {
                out.writeUTF("None");
            }
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            String type = in.readUTF();
            
            switch (type) {
                case "Rectangle":
                    shape = new Rectangle(
                        in.readInt(), in.readInt(),
                        in.readInt(), in.readInt());
                    break;
                case "Ellipse":
                    shape = new Ellipse2D.Float(
                        in.readFloat(), in.readFloat(),
                        in.readFloat(), in.readFloat());
                    break;
                case "Polygon":
                    int[] xpoints = (int[]) in.readObject();
                    int[] ypoints = (int[]) in.readObject();
                    int npoints = in.readInt();
                    shape = new Polygon(xpoints, ypoints, npoints);
                    break;
                default:
                    shape = null;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphicEditorApp::new);
    }
}
