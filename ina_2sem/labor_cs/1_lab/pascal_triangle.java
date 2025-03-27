public class pascal_triangle {
    private int[] row;

    public pascal_triangle(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Nieprawidlowy numer wiersza");
        }
        row = new int[n + 1];
        row[0] = 1;
        for (int k = 1; k <= n; k++) {
            long value = (long) row[k - 1] * (n - k + 1) / k;
            if (value > Integer.MAX_VALUE) {
                throw new ArithmeticException("Przekroczono zakres int");
            }
            row[k] = (int) value;
        }
    }

    public int getElement(int m) {
        if (m < 0 || m >= row.length) {
            throw new IllegalArgumentException("Liczba spoza zakresu");
        }
        return row[m];
    }
}
