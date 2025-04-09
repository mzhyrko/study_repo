#ifndef PIECIOKAT_H
#define PIECIOKAT_H

#include "Figura.h"
#include <cmath>
#include <stdexcept>

class Pieciokat : public Figura {
    double bok;

public:
    Pieciokat(double b);
    double obliczPole() override;
    double obliczObwod() override;
    std::string podajNazwe() override;
};

#endif
