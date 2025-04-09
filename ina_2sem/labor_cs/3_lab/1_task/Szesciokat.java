public class Szesciokat extends Figura {
    private final double bok;

    public Szesciokat(double bok) {
        if (bok <= 0) throw new IllegalArgumentException("Bok musi być dodatni.");
        this.bok = bok;
    }

    @Override
    public double obliczPole() {
        return (3 * Math.sqrt(3) * bok * bok) / 2;
    }

    @Override
    public double obliczObwod() {
        return 6 * bok;
    }

    @Override
    public String podajNazwe() {
        return "Sześciokąt foremny";
    }
}
