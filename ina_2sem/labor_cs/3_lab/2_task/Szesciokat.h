#ifndef SZESCIOKAT_H
#define SZESCIOKAT_H

#include "Figura.h"
#include <cmath>
#include <stdexcept>

class Szesciokat : public Figura {
    double bok;

public:
    Szesciokat(double b);
    double obliczPole() override;
    double obliczObwod() override;
    std::string podajNazwe() override;
};

#endif
