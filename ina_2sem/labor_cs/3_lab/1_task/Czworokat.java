public abstract class Czworokat extends Figura {
    protected double bok1, bok2, bok3, bok4;
    protected double kat;

    public Czworokat(double b1, double b2, double b3, double b4, double kat) {
        if (b1 <= 0 || b2 <= 0 || b3 <= 0 || b4 <= 0) {
            throw new IllegalArgumentException("Boki muszą być dodatnie.");
        }
        if (kat <= 0 || kat >= 180) {
            throw new IllegalArgumentException("Kąt musi być między 0 a 180 stopni.");
        }
        this.bok1 = b1;
        this.bok2 = b2;
        this.bok3 = b3;
        this.bok4 = b4;
        this.kat = kat;
    }
}
