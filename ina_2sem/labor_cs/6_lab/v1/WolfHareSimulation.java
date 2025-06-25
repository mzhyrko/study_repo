import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class WolfHareSimulation {
    public static void main(String[] args) {
        int rows = 30, cols = 40, numHares = 3, k = 200;
        Board board = new Board(rows, cols);
        Map<Integer, Hare> haresMap = new HashMap<>();

        JFrame frame = new JFrame("Wolf & Hares Simulation");
        SimulationPanel panel = new SimulationPanel(board, haresMap);
        StatusPanel statusPanel = new StatusPanel(haresMap);
        
        frame.setLayout(new BorderLayout());
        frame.add(statusPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setVisible(true);

        Random rand = new Random();
        Set<Point> occupied = new HashSet<>();

        // Create wolf
        Point wolfPos;
        do {
            wolfPos = new Point(rand.nextInt(rows), rand.nextInt(cols));
        } while (!occupied.add(wolfPos));

        Wolf wolf = new Wolf(board, wolfPos.x, wolfPos.y, k);
        board.placeAnimal(wolf);
        new Thread(wolf).start();

        // Create hares
        for (int i = 0; i < numHares; i++) {
            Point p;
            do {
                p = new Point(rand.nextInt(rows), rand.nextInt(cols));
            } while (!occupied.add(p));

            Hare hare = new Hare(board, p.x, p.y, k);
            haresMap.put(hare.getId(), hare);
            board.placeAnimal(hare);
            new Thread(hare).start();
        }

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
        new javax.swing.Timer(500, e -> statusPanel.updateStatus()).start();
    }
}

class StatusPanel extends JPanel {
    private final Map<Integer, Hare> hares;
    private final Font ID_FONT = new Font("Arial", Font.BOLD, 14);

    public StatusPanel(Map<Integer, Hare> hares) {
        this.hares = hares;
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        setBackground(Color.LIGHT_GRAY);
    }

    public void updateStatus() {
        removeAll();
        for (Map.Entry<Integer, Hare> entry : hares.entrySet()) {
            JLabel label = new JLabel();
            label.setFont(ID_FONT);
            label.setOpaque(true);
            label.setBackground(entry.getValue().isAlive() ? Color.GREEN : Color.RED);
            label.setForeground(Color.BLACK);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label.setText(entry.getValue().isAlive() ? 
                String.valueOf(entry.getKey()) : 
                "<html><s>" + entry.getKey() + "</s></html>");
            add(label);
        }
        revalidate();
        repaint();
    }
}

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
    public boolean isAlive() { return alive; }

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

class Hare extends Animal {
    private static int nextId = 1;
    private final int id;

    public Hare(Board board, int x, int y, int k) {
        super(board, x, y, k);
        this.id = nextId++;
    }

    public int getId() { return id; }

    @Override
    public int getX() { return x; }
    @Override
    public int getY() { return y; }

    public void run() {
        while (alive) {
            checkPaused();
            try {
                sleepRandom();
            } catch (InterruptedException e) {
                break;
            }

            int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
            Wolf wolf = board.getWolf();
            if (wolf == null) continue;

            int wx = wolf.getX();
            int wy = wolf.getY();
            List<Point> options = new ArrayList<>();
            double maxDist = -1;

            for (int i = 0; i < 8; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (board.isFree(nx, ny)) {
                    double d = Math.sqrt(Math.pow(nx - wx, 2) + Math.pow(ny - wy, 2));
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
}

class Wolf extends Animal {
    private int rest = 0;

    public Wolf(Board board, int x, int y, int k) {
        super(board, x, y, k);
    }

    @Override
    public int getX() { return x; }
    @Override
    public int getY() { return y; }

    public void run() {
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
            if (nearest == null) continue;

            int dx = Integer.compare(nearest.getX(), x);
            int dy = Integer.compare(nearest.getY(), y);

            int nx = x + dx;
            int ny = y + dy;

            if (nx == nearest.getX() && ny == nearest.getY() && !nearest.paused) {
                nearest.alive = false;
                board.removeAnimal(nearest);
                rest = 5;
            }

            if (board.isFree(nx, ny)) {
                board.moveAnimal(this, nx, ny);
                x = nx;
                y = ny;
            }
        }
    }
}

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
        grid[a.getX()][a.getY()] = null;
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
                if (a instanceof Hare && ((Hare) a).isAlive() && !a.paused) {
                    double d = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
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

    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < rows && y < cols;
    }

    public Animal[][] getGrid() {
        return grid;
    }
}

class SimulationPanel extends JPanel {
    private final Board board;
    private final Map<Integer, Hare> hares;
    private final Font ID_FONT = new Font("Arial", Font.BOLD, 14);

    public SimulationPanel(Board board, Map<Integer, Hare> hares) {
        this.board = board;
        this.hares = hares;
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
                Color color = Color.WHITE;
                if (a instanceof Wolf) {
                    color = Color.RED;
                } else if (a instanceof Hare) {
                    color = a.paused ? Color.BLUE : Color.GREEN;
                }
                g.setColor(color);
                g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                
                if (a instanceof Hare) {
                    g.setColor(Color.BLACK);
                    g.setFont(ID_FONT);
                    String id = String.valueOf(((Hare) a).getId());
                    FontMetrics fm = g.getFontMetrics();
                    int x = j * cellWidth + (cellWidth - fm.stringWidth(id)) / 2;
                    int y = i * cellHeight + fm.getAscent() + (cellHeight - fm.getHeight()) / 2;
                    g.drawString(id, x, y);
                }
                
                g.setColor(Color.BLACK);
                g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}
