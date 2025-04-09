#ifndef OBLICZENIAFIGUR_H
#define OBLICZENIAFIGUR_H

#include <string>

class ObliczeniaFigur {
public:
    virtual double obliczPole() = 0;
    virtual double obliczObwod() = 0;
    virtual std::string podajNazwe() = 0;
    virtual ~ObliczeniaFigur() = default;
};

#endif
