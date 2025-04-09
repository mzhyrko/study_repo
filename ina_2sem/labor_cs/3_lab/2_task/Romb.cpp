#include "Romb.h"

Romb::Romb(double bok, double kat) 
    : Czworokat(bok, bok, bok, bok, kat) {
    if (kat == 90) throw std::invalid_argument("Romb nie może mieć kąta 90°.");
}

double Romb::obliczPole() {
    return bok1 * bok1 * sin(kat * M_PI / 180.0);
}

double Romb::obliczObwod() {
    return 4 * bok1;
}

std::string Romb::podajNazwe() {
    return "Romb";
}
