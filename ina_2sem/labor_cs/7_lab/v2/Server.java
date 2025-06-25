import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final Map<String, BinaryTree<? extends Comparable>> trees = new HashMap<>();

    public static void main(String[] args) throws IOException {
        trees.put("Integer", new BinaryTree<Integer>());
        trees.put("Double", new BinaryTree<Double>());
        trees.put("String", new BinaryTree<String>());

        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("Server running on port 8888...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(";", 3);
                if (parts.length < 2){
                    out.println("Error: Invalid request format. Expected: type;operation;[value]");
                    continue;
                }

                String treeType = parts[0];
                String operation = parts[1];
                String value = parts.length == 3 ? parts[2] : null;

                String response = processOperation(treeType, operation, value);
                out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processOperation(String treeType, String operation, String value) {
        BinaryTree tree = trees.get(treeType);
        if (tree == null) {
            return "Unknown tree type: " + treeType;
        }

        if (operation.equals("draw")) {
            return tree.draw();
        }

        Comparable parsedValue;
        try {
            parsedValue = parseValue(treeType, value);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        switch (operation) {
            case "insert":
                tree.insert(parsedValue);
                return "Inserted: " + value + "\n" + tree.draw();
            case "delete":
                tree.delete(parsedValue);
                return "Deleted: " + value + "\n" + tree.draw();
            case "search":
                boolean found = tree.search(parsedValue);
                return found ? "Found: " + value : "Not found: " + value;
            default:
                return "Unknown operation: " + operation;
        }
    }

    private static Comparable parseValue(String treeType, String value) throws IllegalArgumentException {
        return switch (treeType) {
            case "Integer" -> {
                try { yield Integer.parseInt(value); }
                catch (NumberFormatException e) { throw new IllegalArgumentException("Value is not a valid Integer"); }
            }
            case "Double" -> {
                try { yield Double.parseDouble(value); }
                catch (NumberFormatException e) { throw new IllegalArgumentException("Value is not a valid Double"); }
            }
            case "String" -> value;
            default -> throw new IllegalArgumentException("Unknown tree type");
        };
    }
}

class Node<T extends Comparable<T>> {
    T data;
    Node<T> left, right;

    Node(T data) {
        this.data = data;
    }
}

class BinaryTree<T extends Comparable<T>> {
    private Node<T> root;

    public void insert(T value) {
        root = insertRecursive(root, value);
    }

    private Node<T> insertRecursive(Node<T> node, T value) {
        if (node == null) return new Node<>(value);
        if (value.compareTo(node.data) < 0) node.left = insertRecursive(node.left, value);
        else if (value.compareTo(node.data) > 0) node.right = insertRecursive(node.right, value);
        return node;
    }

    public boolean search(T value) {
        return searchRecursive(root, value);
    }

    private boolean searchRecursive(Node<T> node, T value) {
        if (node == null) return false;
        int cmp = value.compareTo(node.data);
        return cmp == 0 || (cmp < 0 ? searchRecursive(node.left, value) : searchRecursive(node.right, value));
    }

    public void delete(T value) {
        root = deleteRecursive(root, value);
    }

    private Node<T> deleteRecursive(Node<T> node, T value) {
        if (node == null) return null;
        int cmp = value.compareTo(node.data);
        if (cmp < 0) node.left = deleteRecursive(node.left, value);
        else if (cmp > 0) node.right = deleteRecursive(node.right, value);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            node.data = minValue(node.right);
            node.right = deleteRecursive(node.right, node.data);
        }
        return node;
    }

    private T minValue(Node<T> node) {
        while (node.left != null) node = node.left;
        return node.data;
    }

    public String draw() {
        StringBuilder sb = new StringBuilder();
        if (root != null) {
            sb.append(root.data).append("\n");
            drawRecursive(root.left, "", true, sb);
            drawRecursive(root.right, "", false, sb);
        } else {
            return "Empty tree";
        }
        return sb.toString();
    }

    private void drawRecursive(Node<T> node, String indent, boolean isLeft, StringBuilder sb) {
        if (node != null) {
            sb.append(indent);
            sb.append(isLeft ? "|-->" : "`-->");
            sb.append(node.data).append("\n");
            String newIndent = indent + (isLeft ? "|   " : "    ");
            drawRecursive(node.left, newIndent, true, sb);
            drawRecursive(node.right, newIndent, false, sb);
        }
    }
}

