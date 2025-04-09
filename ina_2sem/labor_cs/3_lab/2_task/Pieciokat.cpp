#include "Pieciokat.h"

Pieciokat::Pieciokat(double b) : bok(b) {
    if (b <= 0) throw std::invalid_argument("Bok musi być dodatni.");
}

double Pieciokat::obliczPole() {
    return (5 * bok * bok) / (4 * tan(M_PI / 5));
}

double Pieciokat::obliczObwod() {
    return 5 * bok;
}

std::string Pieciokat::podajNazwe() {
    return "Pięciokąt foremny";
}
