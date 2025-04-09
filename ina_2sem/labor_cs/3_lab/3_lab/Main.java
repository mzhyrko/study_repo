public class Main {
    public static void main(String[] args) {
        int i = 0;
        while (i < args.length) {
            String typ = args[i++];
            try {
                switch (typ) {
                    case "o": // Koło
                        double promien = Double.parseDouble(args[i++]);
                        Figury.JednoparametrowaFigura kolo = Figury.JednoparametrowaFigura.KOLO;
                        wypiszWynik(kolo, promien);
                        break;
                    case "k": // Kwadrat
                        double bokKwadrat = Double.parseDouble(args[i++]);
                        Figury.JednoparametrowaFigura kwadrat = Figury.JednoparametrowaFigura.KWADRAT;
                        wypiszWynik(kwadrat, bokKwadrat);
                        break;
                    case "p": // Pięciokąt
                        double bokPieciokat = Double.parseDouble(args[i++]);
                        Figury.JednoparametrowaFigura pieciokat = Figury.JednoparametrowaFigura.PIECIOKAT;
                        wypiszWynik(pieciokat, bokPieciokat);
                        break;
                    case "s": // Sześciokąt
                        double bokSzesciokat = Double.parseDouble(args[i++]);
                        Figury.JednoparametrowaFigura szesciokat = Figury.JednoparametrowaFigura.SZESCIOKAT;
                        wypiszWynik(szesciokat, bokSzesciokat);
                        break;
                    case "pr": // Prostokąt
                        double a = Double.parseDouble(args[i++]);
                        double b = Double.parseDouble(args[i++]);
                        Figury.DwuparametrowaFigura prostokat = Figury.DwuparametrowaFigura.PROSTOKAT;
                        wypiszWynik(prostokat, a, b);
                        break;
                    case "ro": // Romb
                        double bokRomb = Double.parseDouble(args[i++]);
                        double kat = Double.parseDouble(args[i++]);
                        Figury.DwuparametrowaFigura romb = Figury.DwuparametrowaFigura.ROMB;
                        wypiszWynik(romb, bokRomb, kat);
                        break;
                    default:
                        throw new IllegalArgumentException("Nieznany typ figury: " + typ);
                }
            } catch (Exception e) {
                System.err.println("Błąd: " + e.getMessage());
            }
        }
    }

    private static void wypiszWynik(Figury.FiguraJednoParametrowa figura, double parametr) {
        System.out.printf("%s: Pole=%.2f, Obwód=%.2f%n",
                figura.podajNazwe(), figura.obliczPole(parametr), figura.obliczObwod(parametr));
    }

    private static void wypiszWynik(Figury.FiguraDwuParametrowa figura, double p1, double p2) {
        System.out.printf("%s: Pole=%.2f, Obwód=%.2f%n",
                figura.podajNazwe(), figura.obliczPole(p1, p2), figura.obliczObwod(p1, p2));
    }
}
