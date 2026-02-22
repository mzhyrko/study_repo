#include <iostream>
using namespace std;

long long gcd(long long a, long long b) {
    while (b) {
        a %= b;
        swap(a, b);
    }
    return a;
}

long long sumDigits(long long x) {
    long long sum = 0;
    while (x > 0) {
        sum += x % 10;
        x /= 10;
    }
    return sum;
}

int main() {
    int t;
    cin >> t;
    
    while (t--) {
        long long n;
        cin >> n;
        
        for (long long x = n; ; x++) {
            long long s = sumDigits(x);
            if (gcd(x, s) > 1) {
                cout << x << endl;
                break;
            }
        }
    }
    
    return 0;
}