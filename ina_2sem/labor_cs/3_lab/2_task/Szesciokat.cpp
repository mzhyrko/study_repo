#include "Szesciokat.h"

Szesciokat::Szesciokat(double b) : bok(b) {
    if (b <= 0) throw std::invalid_argument("Bok musi być dodatni.");
}

double Szesciokat::obliczPole() {
    return (3 * sqrt(3) * bok * bok) / 2;
}

double Szesciokat::obliczObwod() {
    return 6 * bok;
}

std::string Szesciokat::podajNazwe() {
    return "Sześciokąt foremny";
}
