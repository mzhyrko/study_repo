public class Kolo extends Figura {
    private final double promien;

    public Kolo(double promien) {
        if (promien <= 0) throw new IllegalArgumentException("Promień musi być dodatni.");
        this.promien = promien;
    }

    @Override
    public double obliczPole() {
        return Math.PI * promien * promien;
    }

    @Override
    public double obliczObwod() {
        return 2 * Math.PI * promien;
    }

    @Override
    public String podajNazwe() {
        return "Koło";
    }
}
