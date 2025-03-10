//import java.lang.Math;

public class zadanie3 {
  public static int div(int n) {
        if (n <= 1) {
            return 1;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return n / i;
            }
        }
        return 1;
  }

  public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            try {
                int num = Integer.parseInt(args[i]);

                if (num <= 0) {
                    System.out.println(args[i] + " nie jest liczbą naturalną");
                    continue;
                }

                int gr_divisor = div(num);
                System.out.println(num + " " + gr_divisor);
            } catch (NumberFormatException ex) {
                System.out.println(args[i] + " nie jest liczbą całkowitą");
            }
        }
    }
}
