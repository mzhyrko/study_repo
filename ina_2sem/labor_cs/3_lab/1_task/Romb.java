public class Romb extends Czworokat {
    public Romb(double bok, double kat) {
        super(bok, bok, bok, bok, kat);
        if (kat == 90) throw new IllegalArgumentException("Romb nie może mieć kąta 90°.");
    }

    @Override
    public double obliczPole() {
        return bok1 * bok1 * Math.sin(Math.toRadians(kat));
    }

    @Override
    public double obliczObwod() {
        return 4 * bok1;
    }

    @Override
    public String podajNazwe() {
        return "Romb";
    }
}
