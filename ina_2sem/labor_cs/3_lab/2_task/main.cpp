#include <iostream>
#include <vector>
#include <memory>
#include <string>
#include <stdexcept>
#include "Kolo.h"
#include "Kwadrat.h"
#include "Prostokat.h"
#include "Romb.h"
#include "Pieciokat.h"
#include "Szesciokat.h"

using namespace std;

int main(int argc, char* argv[]) {
    vector<unique_ptr<Figura>> figury;
    int i = 1; // Pomijamy nazwę programu (argv[0])

    while (i < argc) {
        string typ = argv[i++];
        try {
            if (typ == "o") { // Koło
                double r = stod(argv[i++]);
                figury.push_back(make_unique<Kolo>(r));
            } else if (typ == "c") { // Czworokąt
                double b1 = stod(argv[i++]);
                double b2 = stod(argv[i++]);
                double b3 = stod(argv[i++]);
                double b4 = stod(argv[i++]);
                double kat = stod(argv[i++]);

                // Logika wyboru typu czworokąta
                if (b1 == b2 && b2 == b3 && b3 == b4) {
                    if (kat == 90) {
                        figury.push_back(make_unique<Kwadrat>(b1, kat));
                    } else {
                        figury.push_back(make_unique<Romb>(b1, kat));
                    }
                } else if (b1 == b3 && b2 == b4) {
                    if (kat == 90) {
                        figury.push_back(make_unique<Prostokat>(b1, b2, kat));
                    } else {
                        throw invalid_argument("Nieobsługiwany czworokąt.");
                    }
                } else {
                    throw invalid_argument("Nieobsługiwany czworokąt.");
                }
            } else if (typ == "p") { // Pięciokąt
                double bok = stod(argv[i++]);
                figury.push_back(make_unique<Pieciokat>(bok));
            } else if (typ == "s") { // Sześciokąt
                double bok = stod(argv[i++]);
                figury.push_back(make_unique<Szesciokat>(bok));
            } else {
                throw invalid_argument("Nieznany typ figury: " + typ);
            }
        } catch (const out_of_range& e) {
            cerr << "Błąd: Za mało parametrów dla figury typu " << typ << endl;
        } catch (const invalid_argument& e) {
            cerr << "Błąd: " << e.what() << endl;
        }
    }

    // Wypisywanie wyników
    for (const auto& figura : figury) {
        cout << figura->podajNazwe() << ": Pole=" << figura->obliczPole()
             << ", Obwód=" << figura->obliczObwod() << endl;
    }

    return 0;
}
