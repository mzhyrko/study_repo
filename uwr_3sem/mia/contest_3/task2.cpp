#include <iostream>
#include <vector>
using namespace std;

// Количество чисел от l до r, делящихся на k
long long count_divisible(long long l, long long r, long long k) {
    return r / k - (l - 1) / k;
}

int main() {
    int t;
    cin >> t;
    
    while (t--) {
        long long l, r;
        cin >> l >> r;
        
        long long total = r - l + 1;
        vector<long long> primes = {2, 3, 5, 7};
        long long bad = 0;
        
        for (int mask = 1; mask < (1 << 4); mask++) {
            long long product = 1;
            int count_bits = 0;
            
            for (int i = 0; i < 4; i++) {
                if (mask & (1 << i)) {
                    product *= primes[i];
                    count_bits++;
                }
            }
            
            long long cnt = count_divisible(l, r, product);
            
            if (count_bits % 2 == 1) {
                bad += cnt;  
            } else {
                bad -= cnt;  
            }
        }
        
        cout << total - bad << endl;
    }
    
    return 0;
}