#ifndef KWADRAT_H
#define KWADRAT_H

#include "Czworokat.h"

class Kwadrat : public Czworokat {
public:
    Kwadrat(double bok, double kat);
    double obliczPole() override;
    double obliczObwod() override;
    std::string podajNazwe() override;
};

#endif
