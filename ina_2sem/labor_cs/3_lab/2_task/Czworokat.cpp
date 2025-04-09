#include "Czworokat.h"

Czworokat::Czworokat(double b1, double b2, double b3, double b4, double k) {
    if (b1 <= 0 || b2 <= 0 || b3 <= 0 || b4 <= 0) {
        throw std::invalid_argument("Boki muszą być dodatnie.");
    }
    if (k <= 0 || k >= 180) {
        throw std::invalid_argument("Kąt musi być między 0 a 180 stopni.");
    }
    bok1 = b1;
    bok2 = b2;
    bok3 = b3;
    bok4 = b4;
    kat = k;
}
