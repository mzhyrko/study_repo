#include <vector>
#include <map>
#include <string>

using namespace std;

class ArabRzym {
private:
    static const vector<string> romanNumerals;
    static const vector<int> arabicValues;
    static map<char, int> romanMap;

public:
    static int rzym2arab(const string& rzym);
    static string arab2rzym(int arab);
};

