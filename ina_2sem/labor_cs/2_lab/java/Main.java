public class Main {
    public static void main(String[] args) {
        for (String arg : args) {
            try {
                if (arg.matches("\\d+")) {
                    int num = Integer.parseInt(arg);
                    String roman = ArabRzym.arab2rzym(num);
                    System.out.println(arg + " -> " + roman);
                } else
                    int arabic = ArabRzym.rzym2arab(arg.toUpperCase());
                    System.out.println(arg + " -> " + arabic);

                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number: " + arg);
            } catch (ArabRzymException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
