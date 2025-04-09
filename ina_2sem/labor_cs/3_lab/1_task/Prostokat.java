public class Prostokat extends Czworokat {
    public Prostokat(double a, double b, double kat) {
        super(a, b, a, b, kat);
        if (kat != 90) throw new IllegalArgumentException("Kąt w prostokącie musi wynosić 90°.");
    }

    @Override
    public double obliczPole() {
        return bok1 * bok2;
    }

    @Override
    public double obliczObwod() {
        return 2 * (bok1 + bok2);
    }

    @Override
    public String podajNazwe() {
        return "Prostokąt";
    }
}
