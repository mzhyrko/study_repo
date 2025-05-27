import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * Main class to run the simulation.
 */
public class Simulation {
    public static void main(String[] args) {
        int n = 15; // Default grid rows
        int m = 20; // Default grid columns
        int haresCount = 5; // Default number of hares
        int k = 200; // Default delay parameter

        // Parse command line arguments
        if (args.length >= 4) {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            haresCount = Integer.parseInt(args[2]);
            k = Integer.parseInt(args[3]);
        }

        // Make variables effectively final
        final int finalN = n;
        final int finalM = m;
        final int finalHaresCount = haresCount;
        final int finalK = k;

        EventQueue.invokeLater(() -> {
            Board board = new Board(finalN, finalM);
            SimulationGUI gui = new SimulationGUI(board, finalK);
            board.setGui(gui);
            board.initializeAnimals(finalHaresCount);
            gui.setVisible(true);
        });
    }
}

/**
 * Represents the game board and manages animal positions.
 */
class Board {
    private final int rows;
    private final int cols;
    private final Map<Point, Animal> animals = new ConcurrentHashMap<>();
    private Wolf wolf;
    private SimulationGUI gui;
    private final Random random = new Random();

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public void setGui(SimulationGUI gui) {
        this.gui = gui;
    }

    public synchronized boolean moveAnimal(Animal animal, Point oldPos, Point newPos) {
        if (isValidPosition(newPos) && !animals.containsKey(newPos)) {
            animals.remove(oldPos);
            animals.put(newPos, animal);
            gui.updateCell(oldPos);
            gui.updateCell(newPos);
            return true;
        }
        return false;
    }

    public synchronized Point findNearestHare(Point wolfPos) {
        return animals.keySet().stream()
                .filter(p -> animals.get(p) instanceof Hare)
                .min(Comparator.comparingDouble(p -> distance(p, wolfPos)))
                .orElse(null);
    }

    private double distance(Point a, Point b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    public synchronized boolean isValidPosition(Point p) {
        return p.x >= 0 && p.x < cols && p.y >= 0 && p.y < rows;
    }

    public void initializeAnimals(int haresCount) {
        // Place wolf
        Point wolfPos = getRandomEmptyPosition();
        wolf = new Wolf(this, wolfPos, getRandom().nextInt(200) + 100);
        animals.put(wolfPos, wolf);
        new Thread(wolf).start();

        // Place hares
        for (int i = 0; i < haresCount; i++) {
            Point harePos = getRandomEmptyPosition();
            Hare hare = new Hare(this, harePos, getRandom().nextInt(200) + 100);
            animals.put(harePos, hare);
            new Thread(hare).start();
        }
    }

    private Point getRandomEmptyPosition() {
        Point pos;
        do {
            pos = new Point(random.nextInt(cols), random.nextInt(rows));
        } while (animals.containsKey(pos));
        return pos;
    }

    public synchronized void toggleAnimalPause(Point pos) {
        Animal animal = animals.get(pos);
        if (animal != null) {
            animal.togglePause();
        }
    }

    public synchronized void removeHare(Point pos) {
        animals.remove(pos);
        gui.updateCell(pos);
    }

    // Getters
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public Map<Point, Animal> getAnimals() { return animals; }
    public Random getRandom() { return random; }
}

/**
 * Abstract class representing an animal.
 */
abstract class Animal implements Runnable {
    protected final Board board;
    protected Point position;
    protected volatile boolean paused = false;
    protected final Random random;
    protected int delayParam;

    public Animal(Board board, Point initialPosition, int delayParam) {
        this.board = board;
        this.position = initialPosition;
        this.delayParam = delayParam;
        this.random = new Random();
    }

    public void togglePause() {
        paused = !paused;
    }

    protected void randomDelay() {
        try {
            int delay = (int) (delayParam * (0.5 + random.nextDouble()));
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected abstract Point calculateNextPosition();

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!paused) {
                Point newPos = calculateNextPosition();
                if (board.moveAnimal(this, position, newPos)) {
                    position = newPos;
                }
                randomDelay();
            }
        }
    }
}

/**
 * Represents a hare that flees from the wolf.
 */
class Hare extends Animal {
    public Hare(Board board, Point initialPosition, int delayParam) {
        super(board, initialPosition, delayParam);
    }

    @Override
    protected Point calculateNextPosition() {
        Point wolfPos = board.findNearestHare(position);
        if (wolfPos == null) return position;

        // Calculate direction away from wolf
        int dx = Integer.compare(position.x, wolfPos.x);
        int dy = Integer.compare(position.y, wolfPos.y);

        List<Point> possibleMoves = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;
                Point newPos = new Point(position.x + x, position.y + y);
                if (board.isValidPosition(newPos) && !board.moveAnimal(this, position, newPos)) {
                    possibleMoves.add(newPos);
                }
            }
        }

        if (possibleMoves.isEmpty()) return position;
        return possibleMoves.get(board.getRandom().nextInt(possibleMoves.size()));
    }
}

/**
 * Represents a wolf that hunts hares.
 */
class Wolf extends Animal {
    private int restCycles = 0;

    public Wolf(Board board, Point initialPosition, int delayParam) {
        super(board, initialPosition, delayParam);
    }

    @Override
    protected Point calculateNextPosition() {
        if (restCycles > 0) {
            restCycles--;
            return position;
        }

        Point target = board.findNearestHare(position);
        if (target == null) return position;

        int dx = Integer.compare(target.x, position.x);
        int dy = Integer.compare(target.y, position.y);

        List<Point> possibleMoves = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;
                Point newPos = new Point(position.x + x, position.y + y);
                if (board.isValidPosition(newPos)) {
                    if (board.moveAnimal(this, position, newPos)) {
                        if (newPos.equals(target)) {
                            board.removeHare(target);
                            restCycles = 5;
                        }
                        possibleMoves.clear();
                        possibleMoves.add(newPos);
                        break;
                    } else {
                        possibleMoves.add(newPos);
                    }
                }
            }
        }

        if (possibleMoves.isEmpty()) return position;
        return possibleMoves.get(board.getRandom().nextInt(possibleMoves.size()));
    }
}

/**
 * GUI for the simulation.
 */
class SimulationGUI extends JFrame {
    private final Board board;
    private final int cellSize = 30;
    private final JPanel gridPanel;

    public SimulationGUI(Board board, int k) {
        this.board = board;
        setTitle("Wolf and Hares Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gridPanel = new JPanel(new GridLayout(board.getRows(), board.getCols()));
        initializeGrid();
        add(gridPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeGrid() {
        gridPanel.removeAll();
        for (int y = 0; y < board.getRows(); y++) {
            for (int x = 0; x < board.getCols(); x++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(cellSize, cellSize));
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                final Point pos = new Point(x, y);
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.toggleAnimalPause(pos);
                        updateCell(pos);
                    }
                });
                gridPanel.add(cell);
            }
        }
    }

    public void updateCell(Point pos) {
        Component cell = gridPanel.getComponent(pos.y * board.getCols() + pos.x);
        Animal animal = board.getAnimals().get(pos);
        cell.setBackground(animal instanceof Wolf ? Color.RED : (animal != null ? Color.GRAY : Color.WHITE));
        cell.repaint();
    }
}
