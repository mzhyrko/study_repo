#pragma once

#include <vector>
#include <stdexcept>

using namespace std;

class PascalTriangleRow {
private:
    vector<int> row;
public:
    PascalTriangleRow(int n);
    int getElement(int m) const;
    ~PascalTriangleRow() = default;
};

