#ifndef CZWOROKAT_H
#define CZWOROKAT_H

#include "Figura.h"
#include <stdexcept>

class Czworokat : public Figura {
protected:
    double bok1, bok2, bok3, bok4;
    double kat;

public:
    Czworokat(double b1, double b2, double b3, double b4, double k);
    virtual ~Czworokat() = default;
};

#endif
