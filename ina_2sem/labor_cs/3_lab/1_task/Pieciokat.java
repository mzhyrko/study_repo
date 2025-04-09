public class Pieciokat extends Figura {
    private final double bok;

    public Pieciokat(double bok) {
        if (bok <= 0) throw new IllegalArgumentException("Bok musi być dodatni.");
        this.bok = bok;
    }

    @Override
    public double obliczPole() {
        return (5 * bok * bok) / (4 * Math.tan(Math.PI / 5));
    }

    @Override
    public double obliczObwod() {
        return 5 * bok;
    }

    @Override
    public String podajNazwe() {
        return "Pięciokąt foremny";
    }
}
