import java.util.ArrayList;
import java.util.List;

public class Figury {
    public static void main(String[] args) {
        List<Figura> figury = new ArrayList<>();
        int i = 0;

        while (i < args.length) {
            String typ = args[i++];
            try {
                switch (typ) {
                    case "o": // Kolo
                        double promien = Double.parseDouble(args[i++]);
                        figury.add(new Kolo(promien));
                        break;
                    case "c": // Czworokat
                        double b1 = Double.parseDouble(args[i++]);
                        double b2 = Double.parseDouble(args[i++]);
                        double b3 = Double.parseDouble(args[i++]);
                        double b4 = Double.parseDouble(args[i++]);
                        double kat = Double.parseDouble(args[i++]);
                        
                        if (b1 == b2 && b2 == b3 && b3 == b4) {
                            if (kat == 90) figury.add(new Kwadrat(b1, kat));
                            else figury.add(new Romb(b1, kat));
                        } else if (b1 == b3 && b2 == b4) {
                            if (kat == 90) figury.add(new Prostokat(b1, b2, kat));
                            else throw new IllegalArgumentException("Nieobsługiwany czworokąt.");
                        } else {
                            throw new IllegalArgumentException("Nieobsługiwany czworokąt.");
                        }
                        break;
                    case "p": // Pieciokat
                        double bokP = Double.parseDouble(args[i++]);
                        figury.add(new Pieciokat(bokP));
                        break;
                    case "s": // Szesciokat
                        double bokS = Double.parseDouble(args[i++]);
                        figury.add(new Szesciokat(bokS));
                        break;
                    default:
                        throw new IllegalArgumentException("Nieznany typ figury: " + typ);
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Za mało parametrów dla figury typu " + typ);
            } catch (NumberFormatException e) {
                System.err.println("Nieprawidłowy format liczby.");
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }

        // Wypisanie wynikow
        for (Figura f : figury) {
            System.out.printf("%s: Pole=%.2f, Obwod=%.2f%n",
                    f.podajNazwe(), f.obliczPole(), f.obliczObwod());
        }
    }
}
