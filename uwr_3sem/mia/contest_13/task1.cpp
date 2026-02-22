#include <bits/stdc++.h>
using namespace std;

const int N = 1000001;
bool is_composite[N];

void make_sieve() {
    is_composite[0] = is_composite[1] = true;
    
    for (int i = 4; i < N; i += 2) {
        is_composite[i] = true;
    }
    
    for (int i = 3; i * i < N; i += 2) {
        if (!is_composite[i]) {
            for (int j = i * i; j < N; j += i + i) {
                is_composite[j] = true;
            }
        }
    }
}

bool is_tprime(long long x) {
    if (x < 4) return false;
    
    long long sq = round(sqrt(x));
    if (sq * sq != x) return false;
    
    if (sq >= N) return false;          
    
    return !is_composite[sq];
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    make_sieve();
    
    int n;
    cin >> n;
    
    while (n--) {
        long long x;
        cin >> x;
        if (is_tprime(x)) {
            cout << "YES\n";
        } else {
            cout << "NO\n";
        }
    }
    
    return 0;
}