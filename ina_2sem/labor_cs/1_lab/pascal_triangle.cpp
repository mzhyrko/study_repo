#include "pascal_triangle.h"

using namespace std;

PascalTriangleRow::PascalTriangleRow(int n) {
    if (n < 0) {
        throw invalid_argument("Nieprawidlowy numer wiersza");
    }
    row.resize(n + 1);
    row[0] = 1;
    for (int k = 0; k < n; ++k) {
        row[k + 1] = row[k] * (n - k) / (k + 1);
    }
}

int PascalTriangleRow::getElement(int m) const {
    if (m < 0 || m >= static_cast<int>(row.size())) {
        throw out_of_range("liczba spoza zakresu");
    }
    return row[m];
}
