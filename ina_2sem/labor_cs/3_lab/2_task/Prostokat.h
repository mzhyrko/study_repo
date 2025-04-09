#ifndef PROSTOKAT_H
#define PROSTOKAT_H

#include "Czworokat.h"

class Prostokat : public Czworokat {
public:
    Prostokat(double a, double b, double kat);
    double obliczPole() override;
    double obliczObwod() override;
    std::string podajNazwe() override;
};

#endif
