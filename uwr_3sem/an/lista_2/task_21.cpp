#include <bits/stdc++.h>

using namespace std;

int main() {
    vector<double> numbers;
    for (int e2 = 0; e2 <= 1; e2++) {
        for (int e3 = 0; e3 <= 1; e3++) {
            for (int e4 = 0; e4 <= 1; e4++) {
                for (int e5 = 0; e5 <= 1; e5++) {
                    for (int c = 0; c <= 1; c++) {
                        
                        double x = 0.1 + (e2 * 0.01) + (e3 * 0.001) + (e4 * 0.0001) + (e5 * 0.00001);
                        x = x * pow(2, c); 
                        
                        numbers.push_back(x);
                        numbers.push_back(-x);
                    }
                }
            }
        }
    }

    
    double minA = *min_element(numbers.begin(), numbers.end());
    double maxB = *max_element(numbers.begin(), numbers.end());

    
    cout << fixed << setprecision(6);
    cout << "Numbers: ";
    for (double num : numbers) {
        cout << num << " ";
    }
    return 0;
    
}
