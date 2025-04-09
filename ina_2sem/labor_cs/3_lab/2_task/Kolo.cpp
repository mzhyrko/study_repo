#include "Kolo.h"

Kolo::Kolo(double r) : promien(r) {
    if (r <= 0) throw std::invalid_argument("Promień musi być dodatni.");
}

double Kolo::obliczPole() {
    return M_PI * promien * promien;
}

double Kolo::obliczObwod() {
    return 2 * M_PI * promien;
}

std::string Kolo::podajNazwe() {
    return "Koło";
}
