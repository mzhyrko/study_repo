#include "ArabRzym.h"
#include "ArabRzymException.h"
#include <algorithm>
#include <sstream>

using namespace std;

const vector<string> ArabRzym::romanNumerals = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };
const vector<int> ArabRzym::arabicValues = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
map<char, int> ArabRzym::romanMap = { {'I',1}, {'V',5}, {'X',10}, {'L',50}, {'C',100}, {'D',500}, {'M',1000} };

int ArabRzym::rzym2arab(const string& rzym) {
    if (rzym.empty()) {
        throw ArabRzymException("Empty input");
    }

    for (char c : rzym) {
        if (romanMap.find(c) == romanMap.end()) {
            throw ArabRzymException("Invalid character: " + string(1, c));
        }
    }

    int total = 0;
    int prevValue = 0;
    for (int i = rzym.size() - 1; i >= 0; --i) {
        int currentValue = romanMap[rzym[i]];
        if (currentValue < prevValue) {
            total -= currentValue;
        } else {
            total += currentValue;
        }
        prevValue = currentValue;
    }

    if (total < 1 || total > 3999) {
        throw ArabRzymException("Out of range: " + to_string(total));
    }

    string convertedBack = arab2rzym(total);
    if (rzym != convertedBack) {
        throw ArabRzymException("Invalid Roman numeral: " + rzym);
    }

    return total;
}

string ArabRzym::arab2rzym(int arab) {
    if (arab < 1 || arab > 3999) {
        throw ArabRzymException("Out of range (1-3999): " + to_string(arab));
    }

    string result;
    int remaining = arab;
    for (int i = arabicValues.size() - 1; i >= 0; --i) {
        while (remaining >= arabicValues[i]) {
            result += romanNumerals[i];
            remaining -= arabicValues[i];
        }
    }
    return result;
}
