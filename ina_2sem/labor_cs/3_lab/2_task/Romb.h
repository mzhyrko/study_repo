#ifndef ROMB_H
#define ROMB_H

#include "Czworokat.h"
#include <cmath>

class Romb : public Czworokat {
public:
    Romb(double bok, double kat);
    double obliczPole() override;
    double obliczObwod() override;
    std::string podajNazwe() override;
};

#endif
