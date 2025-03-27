#include <iostream>
#include <algorithm>
#include "ArabRzym.h"
#include "ArabRzymException.h"

using namespace std;

int main(int argc, char* argv[]) {
    for (int i = 1; i < argc; ++i) {
        string arg = argv[i];
        try {
            if (isdigit(arg[0])) {
                int num = stoi(arg);
                string roman = ArabRzym::arab2rzym(num);
                cout << arg << " -> " << roman << endl;
            } else {
                string upperArg = arg;
                transform(upperArg.begin(), upperArg.end(), upperArg.begin(), ::toupper);
                int arabic = ArabRzym::rzym2arab(upperArg);
                cout << arg << " -> " << arabic << endl;
            }
        } catch (const invalid_argument& e) {
            cerr << "Invalid number: " << arg << endl;
        } catch (const ArabRzymException& e) {
            cerr << "Error: " << e.what() << endl;
        }
    }
    return 0;
}
