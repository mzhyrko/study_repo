#include "Prostokat.h"

Prostokat::Prostokat(double a, double b, double kat) 
    : Czworokat(a, b, a, b, kat) {
    if (kat != 90) throw std::invalid_argument("Kąt w prostokącie musi wynosić 90°.");
}

double Prostokat::obliczPole() {
    return bok1 * bok2;
}

double Prostokat::obliczObwod() {
    return 2 * (bok1 + bok2);
}

std::string Prostokat::podajNazwe() {
    return "Prostokąt";
}
