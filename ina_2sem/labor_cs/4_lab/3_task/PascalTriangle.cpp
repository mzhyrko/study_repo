#include <iostream>
#include <vector>
#include <stdexcept>

using namespace std;

long pascalValue(int row, int col) {
    if (row < 0 || col < 0 || col > row) {
        throw out_of_range("Invalid row or column index");
    }

    long result = 1;
    for (int i = 1; i <= col; ++i) {
        result = result * (row - i + 1) / i;
    }

    return result;
}

int main(int argc, char* argv[]) {
    if (argc != 3) {
        cerr << "Usage: " << argv[0] << " <row> <column>" << endl;
        return 1;
    }

    try {
        int row = stoi(argv[1]);
        int col = stoi(argv[2]);

        long value = pascalValue(row, col);
        cout << value << endl;
    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl;
        return 1;
    }

    return 0;
}

