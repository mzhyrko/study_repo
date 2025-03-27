import java.util.HashMap;
import java.util.Map;

public class ArabRzym {
    private static String[] liczby = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };
    private static int[] values = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
    private static Map<Character, Integer> romanMap = new HashMap<>();

    static {
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
    }

    public static int rzym2arab(String rzym) throws ArabRzymException {
        if (rzym == null || rzym.isEmpty()) {
            throw new ArabRzymException("Empty input");
        }

        rzym = rzym.toUpperCase();
        for (char c : rzym.toCharArray()) {
            if (!romanMap.containsKey(c)) {
                throw new ArabRzymException("Invalid character: " + c);
            }
        }

        int total = 0;
        int prevValue = 0;
        for (int i = rzym.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(rzym.charAt(i));
            if (currentValue < prevValue) {
                total -= currentValue;
            } else {
                total += currentValue;
            }
            prevValue = currentValue;
        }

        if (total < 1 || total > 3999) {
            throw new ArabRzymException("Out of range: " + total);
        }

        String convertedBack = arab2rzym(total);
        if (!rzym.equals(convertedBack)) {
            throw new ArabRzymException("Invalid Roman numeral: " + rzym);
        }

        return total;
    }

    public static String arab2rzym(int arab) throws ArabRzymException {
        if (arab < 1 || arab > 3999) {
            throw new ArabRzymException("Out of range (1-3999): " + arab);
        }

        StringBuilder result = new StringBuilder();
        int remaining = arab;
        for (int i = values.length - 1; i >= 0; i--) {
            while (remaining >= values[i]) {
                result.append(liczby[i]);
                remaining -= values[i];
            }
        }
        return result.toString();
    }
}
