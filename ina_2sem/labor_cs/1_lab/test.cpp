#include <iostream>
#include <string>
#include "pascal_triangle.h"

using namespace std;

int main(int argc, char* argv[]) {
    if (argc < 2) {
        cerr << argv[0] << " - Nieprawidlowy numer wiersza" << endl;
        return 1;
    }

    string nStr = argv[1];
    try {
        int n = stoi(nStr);
        try {
            PascalTriangleRow row(n);
            for (int i = 2; i < argc; ++i) {
                string currentArg = argv[i];
                try {
                    int m = stoi(currentArg);
                    try {
                        int value = row.getElement(m);
                        cout << m << " - " << value << endl;
                    } catch (const out_of_range&) {
                        cout << currentArg << " - liczba spoza zakresu" << endl;
                    }
                } catch (...) {
                    cout << currentArg << " - nieprawidlowa dana" << endl;
                }
            }
        } catch (const invalid_argument&) {
            cerr << nStr << " - Nieprawidlowy numer wiersza" << endl;
            return 1;
        }
    } catch (...) {
        cerr << nStr << " - Nieprawidlowy numer wiersza" << endl;
        return 1;
    }

    return 0;
}
