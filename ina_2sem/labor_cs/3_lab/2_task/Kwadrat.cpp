#include "Kwadrat.h"

Kwadrat::Kwadrat(double bok, double kat) 
    : Czworokat(bok, bok, bok, bok, kat) {
    if (kat != 90) throw std::invalid_argument("Kąt w kwadracie musi wynosić 90°.");
}

double Kwadrat::obliczPole() {
    return bok1 * bok1;
}

double Kwadrat::obliczObwod() {
    return 4 * bok1;
}

std::string Kwadrat::podajNazwe() {
    return "Kwadrat";
}
