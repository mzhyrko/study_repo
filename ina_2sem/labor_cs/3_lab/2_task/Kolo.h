#ifndef KOLO_H
#define KOLO_H

#include "Figura.h"
#include <cmath>
#include <stdexcept>

class Kolo : public Figura {
    double promien;

public:
    Kolo(double r);
    double obliczPole() override;
    double obliczObwod() override;
    std::string podajNazwe() override;
};

#endif
