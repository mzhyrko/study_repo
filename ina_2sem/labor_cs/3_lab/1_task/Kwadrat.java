public class Kwadrat extends Czworokat {
    public Kwadrat(double bok, double kat) {
        super(bok, bok, bok, bok, kat);
        if (kat != 90) throw new IllegalArgumentException("Kąt w kwadracie musi wynosić 90°.");
    }

    @Override
    public double obliczPole() {
        return bok1 * bok1;
    }

    @Override
    public double obliczObwod() {
        return 4 * bok1;
    }

    @Override
    public String podajNazwe() {
        return "Kwadrat";
    }
}
