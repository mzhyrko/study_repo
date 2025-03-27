public class test {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak argumentow");
            return;
        }

        int n;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println(args[0] + " - Nieprawidlowy numer wiersza");
            return;
        }

        if (n < 0) {
            System.out.println(args[0] + " - Nieprawidlowy numer wiersza");
            return;
        }

        pascal_triangle wiersz;
        try {
            wiersz = new pascal_triangle(n);
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println(args[0] + " - Nieprawidlowy numer wiersza");
            return;
        }

        for (int i = 1; i < args.length; i++) {
            String arg = args[i];
            try {
                int m = Integer.parseInt(arg);
                try {
                    int value = wiersz.getElement(m);
                    System.out.println(m + " - " + value);
                } catch (IllegalArgumentException e) {
                    System.out.println(arg + " - liczba spoza zakresu");
                }
            } catch (NumberFormatException e) {
                System.out.println(arg + " - nieprawidlowa dana");
            }
        }
    }
}


