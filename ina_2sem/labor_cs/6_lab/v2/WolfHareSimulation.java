import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Main class for the Wolf and Hares simulation.
 */
public class WolfHareSimulation {
    private static JFrame frame;

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java WolfHareSimulation <rows> <cols> <numHares> <delay>");
            return;
        }

        try {
            int rows = Integer.parseInt(args[0]);
            int cols = Integer.parseInt(args[1]);
            int numHares = Integer.parseInt(args[2]);
            int delay = Integer.parseInt(args[3]);

            if (rows <= 0 || cols <= 0 || numHares < 0 || delay < 0) {
                System.out.println("All values must be positive integers.");
                return;
            }

            if (numHares >= rows * cols - 1) {
                System.out.println("Number of hares must be less than rows × culumns - 1.");
                return;
            }

            startSimulation(rows, cols, numHares, delay);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter integers only.");
        }
    }

    public static void startSimulation(int rows, int cols, int numHares, int delay) {
        Board board = new Board(rows, cols);
        frame = new JFrame("Wolf & Hares Simulation");
        SimulationPanel panel = new SimulationPanel(board);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(panel);
        frame.setVisible(true);

        Random rand = new Random();
        Set<Point> occupied = new HashSet<>();

        // Place the wolf
        Point wolfPos;
        do {
            wolfPos = new Point(rand.nextInt(rows), rand.nextInt(cols));
        } while (!occupied.add(wolfPos));

        Wolf wolf = new Wolf(board, wolfPos.x, wolfPos.y, delay, frame);
        board.placeAnimal(wolf);
        new Thread(wolf).start();

        // Place the hares
        for (int i = 0; i < numHares; i++) {
            Point p;
            do {
                p = new Point(rand.nextInt(rows), rand.nextInt(cols));
            } while (!occupied.add(p));

            Hare hare = new Hare(board, p.x, p.y, delay);
            board.placeAnimal(hare);
            new Thread(hare).start();
        }

        // Mouse interaction for pause/resume
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellWidth = panel.getWidth() / cols;
                int cellHeight = panel.getHeight() / rows;
                int x = e.getY() / cellHeight;
                int y = e.getX() / cellWidth;
                Animal a = board.getAnimalAt(x, y);
                if (a != null) {
                    a.togglePause();
                }
            }
        });

        new javax.swing.Timer(100, e -> panel.repaint()).start();
    }
}

/**
 * Abstract class representing an animal.
 */
abstract class Animal implements Runnable {
    protected volatile boolean alive = true;
    protected int x, y, k;
    protected final Board board;
    protected static final Random random = new Random();
    protected final Object pauseLock = new Object();
    protected volatile boolean paused = false;

    public Animal(Board board, int x, int y, int k) {
        this.board = board;
        this.x = x;
        this.y = y;
        this.k = k;
    }

    public abstract int getX();
    public abstract int getY();

    public void togglePause() {
        if (!alive) return;
        paused = !paused;
        synchronized (pauseLock) {
            if (!paused) pauseLock.notifyAll();
        }
    }

    protected void sleepRandom() throws InterruptedException {
        Thread.sleep(k / 2 + random.nextInt(k));
    }

    protected void checkPaused() {
        if (!alive) return;
        synchronized (pauseLock) {
            while (paused) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException ignored) {}
            }
        }
    }
}

/**
 * Class representing a hare.
 */
class Hare extends Animal {
    public Hare(Board board, int x, int y, int k) {
        super(board, x, y, k);
    }

    public void die() {
        alive = false;
        board.removeAnimal(this);
    }

    @Override public int getX() { return x; }
    @Override public int getY() { return y; }

    public void run() {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        while (alive) {
            checkPaused();
            try {
                sleepRandom();
            } catch (InterruptedException e) {
                break;
            }

            Wolf wolf = board.getWolf();
            if (!alive || wolf == null) continue;

            int wx = wolf.getX(), wy = wolf.getY();
            List<Point> options = new ArrayList<>();
            double maxDist = -1;

            for (int i = 0; i < 8; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (board.isFree(nx, ny)) {
                    double d = dist(nx, ny, wx, wy);
                    if (d > maxDist) {
                        maxDist = d;
                        options.clear();
                        options.add(new Point(nx, ny));
                    } else if (d == maxDist) {
                        options.add(new Point(nx, ny));
                    }
                }
            }

            if (!options.isEmpty()) {
                Point move = options.get(random.nextInt(options.size()));
                board.moveAnimal(this, move.x, move.y);
                x = move.x;
                y = move.y;
            }
        }
    }

    private double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (long)(x1 - x2) + (y1 - y2) * (long)(y1 - y2)); 
    }
}

/**
 * Class representing the wolf.
 */
class Wolf extends Animal {
    private int rest = 0;
    private final JFrame frame;

    public Wolf(Board board, int x, int y, int k, JFrame frame) {
        super(board, x, y, k);
        this.frame = frame;
    }

    public void die() {
        alive = false;
        board.removeAnimal(this);
    }

    @Override public int getX() { return x; }
    @Override public int getY() { return y; }

    public void run() {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        while (alive) {
            checkPaused();
            try {
                sleepRandom();
            } catch (InterruptedException e) {
                break;
            }

            if (rest > 0) {
                rest--;
                continue;
            }

            Hare nearest = board.getNearestHare(x, y);
            if (nearest == null) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(frame, "All hares are caught!");
                    System.exit(0);
                });
                break;
            }

            List<Point> bestMoves = new ArrayList<>();
            double minDist = Double.MAX_VALUE;

            for (int i = 0; i < 8; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (!board.isValid(nx, ny)) continue;

                Animal a = board.getAnimalAt(nx, ny);
                if (a instanceof Hare && !((Hare) a).paused) {
                    bestMoves.add(new Point(nx, ny));
                    minDist = 0;
                } else if (board.isFree(nx, ny)) {
                    double dist = dist(nx, ny, nearest.getX(), nearest.getY());
                    if (dist < minDist) {
                        minDist = dist;
                        bestMoves.clear();
                        bestMoves.add(new Point(nx, ny));
                    } else if (dist == minDist) {
                        bestMoves.add(new Point(nx, ny));
                    }
                }
            }

            if (!bestMoves.isEmpty()) {
                Point move = bestMoves.get(random.nextInt(bestMoves.size()));
                Animal target = board.getAnimalAt(move.x, move.y);
                if (target instanceof Hare && !((Hare) target).paused) {
                    ((Hare) target).die();
                    board.moveAnimal(this, move.x, move.y);
                    x = move.x;
                    y = move.y;
                    rest = 5;
                } else if (board.isFree(move.x, move.y)) {
                    board.moveAnimal(this, move.x, move.y);
                    x = move.x;
                    y = move.y;
                }
            }
        }
    }

    private double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (long)(x1 - x2) + (y1 - y2) * (long)(y1 - y2));
    }
}

/**
 * Class representing the game board.
 */
class Board {
    private final Animal[][] grid;
    private final int rows, cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Animal[rows][cols];
    }

    public synchronized boolean isFree(int x, int y) {
        return isValid(x, y) && grid[x][y] == null;
    }

    public synchronized void moveAnimal(Animal a, int newX, int newY) {
        if (!isValid(newX, newY)) return;
        grid[a.getX()][a.getY()] = null;
        grid[newX][newY] = a;
    }

    public synchronized void placeAnimal(Animal a) {
        grid[a.getX()][a.getY()] = a;
    }

    public synchronized void removeAnimal(Animal a) {
        if (isValid(a.getX(), a.getY()) && grid[a.getX()][a.getY()] == a) {
            grid[a.getX()][a.getY()] = null;
        }
    }

    public synchronized Animal getAnimalAt(int x, int y) {
        return isValid(x, y) ? grid[x][y] : null;
    }

    public synchronized Hare getNearestHare(int x, int y) {
        Hare nearest = null;
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Animal a = grid[i][j];
                if (a instanceof Hare && !((Hare) a).paused) {
                    double d = Math.sqrt((x - i) * (long)(x - i) + (y - j) * (long)(y - j));
                    if (d < minDist) {
                        minDist = d;
                        nearest = (Hare) a;
                    }
                }
            }
        }
        return nearest;
    }

    public synchronized Wolf getWolf() {
        for (Animal[] row : grid) {
            for (Animal a : row) {
                if (a instanceof Wolf) return (Wolf) a;
            }
        }
        return null;
    }

    public synchronized boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < rows && y < cols;
    }

    public Animal[][] getGrid() {
        return grid;
    }
}

/**
 * Visualization panel for rendering the simulation.
 */
class SimulationPanel extends JPanel {
    private final Board board;

    public SimulationPanel(Board board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int rows = board.getGrid().length;
        int cols = board.getGrid()[0].length;
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Animal a = board.getAnimalAt(i, j);
                if (a instanceof Wolf) {
                    g.setColor(Color.RED);
                } else if (a instanceof Hare) {
                    g.setColor(a.paused ? Color.BLUE : Color.GREEN);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                g.setColor(Color.BLACK);
                g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}

