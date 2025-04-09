public class Figury {
    // Interfejs dla figur z jednym parametrem
    public interface FiguraJednoParametrowa {
        double obliczPole(double parametr);
        double obliczObwod(double parametr);
        String podajNazwe();
    }

    // Interfejs dla figur z dwoma parametrami
    public interface FiguraDwuParametrowa {
        double obliczPole(double parametr1, double parametr2);
        double obliczObwod(double parametr1, double parametr2);
        String podajNazwe();
    }

    // Enum dla figur z jednym parametrem
    public enum JednoparametrowaFigura implements FiguraJednoParametrowa {
        KOLO {
            @Override
            public double obliczPole(double r) {
                if (r <= 0) throw new IllegalArgumentException("Promień musi być dodatni.");
                return Math.PI * r * r;
            }

            @Override
            public double obliczObwod(double r) {
                if (r <= 0) throw new IllegalArgumentException("Promień musi być dodatni.");
                return 2 * Math.PI * r;
            }

            @Override
            public String podajNazwe() {
                return "Koło";
            }
        },
        KWADRAT {
            @Override
            public double obliczPole(double a) {
                if (a <= 0) throw new IllegalArgumentException("Bok musi być dodatni.");
                return a * a;
            }

            @Override
            public double obliczObwod(double a) {
                if (a <= 0) throw new IllegalArgumentException("Bok musi być dodatni.");
                return 4 * a;
            }

            @Override
            public String podajNazwe() {
                return "Kwadrat";
            }
        },
        PIECIOKAT {
            @Override
            public double obliczPole(double a) {
                if (a <= 0) throw new IllegalArgumentException("Bok musi być dodatni.");
                return (5 * a * a) / (4 * Math.tan(Math.PI / 5));
            }

            @Override
            public double obliczObwod(double a) {
                if (a <= 0) throw new IllegalArgumentException("Bok musi być dodatni.");
                return 5 * a;
            }

            @Override
            public String podajNazwe() {
                return "Pięciokąt foremny";
            }
        },
        SZESCIOKAT {
            @Override
            public double obliczPole(double a) {
                if (a <= 0) throw new IllegalArgumentException("Bok musi być dodatni.");
                return (3 * Math.sqrt(3) * a * a) / 2;
            }

            @Override
            public double obliczObwod(double a) {
                if (a <= 0) throw new IllegalArgumentException("Bok musi być dodatni.");
                return 6 * a;
            }

            @Override
            public String podajNazwe() {
                return "Sześciokąt foremny";
            }
        };
    }

    // Enum dla figur z dwoma parametrami
    public enum DwuparametrowaFigura implements FiguraDwuParametrowa {
        PROSTOKAT {
            @Override
            public double obliczPole(double a, double b) {
                if (a <= 0 || b <= 0) throw new IllegalArgumentException("Boki muszą być dodatnie.");
                return a * b;
            }

            @Override
            public double obliczObwod(double a, double b) {
                if (a <= 0 || b <= 0) throw new IllegalArgumentException("Boki muszą być dodatnie.");
                return 2 * (a + b);
            }

            @Override
            public String podajNazwe() {
                return "Prostokąt";
            }
        },
        ROMB {
            @Override
            public double obliczPole(double a, double kat) {
                if (a <= 0) throw new IllegalArgumentException("Bok musi być dodatni.");
                if (kat <= 0 || kat >= 180) throw new IllegalArgumentException("Kąt musi być między 0 a 180 stopni.");
                return a * a * Math.sin(Math.toRadians(kat));
            }

            @Override
            public double obliczObwod(double a, double kat) {
                if (a <= 0) throw new IllegalArgumentException("Bok musi być dodatni.");
                return 4 * a;
            }

            @Override
            public String podajNazwe() {
                return "Romb";
            }
        };
    }
}
