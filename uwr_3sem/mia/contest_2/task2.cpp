#include <bits/stdc++.h>
using namespace std;

void solve() {
    int n;
    long long k;
    cin >> n >> k;

    vector<int> exp(n);
    for (int i = 0; i < n; i++) {
        cin >> exp[i];
    }

    long long answer = 0;
    long long remain = k + 1;   

    for (int i = 0; i < n; i++) {
        long long cur_pow = 1;
        for (int j = 0; j < exp[i]; j++) {
            cur_pow *= 10;
        }

        long long max_take;
        if (i + 1 < n) {
            long long next_pow = 1;
            for (int j = 0; j < exp[i + 1]; j++) {
                next_pow *= 10;
            }
            max_take = (next_pow / cur_pow) - 1; 
        } else {
            max_take = remain + 10;  
        }

        long long take = min(max_take, remain);

        answer += take * cur_pow;
        remain -= take;

        if (remain <= 0) {
            break;
        }
    }

    if (remain > 0) {
        long long last_pow = 1;
        for (int j = 0; j < exp.back(); j++) {
            last_pow *= 10;
        }
        answer += last_pow;
    }

    cout << answer << '\n';
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int t;
    cin >> t;

    while (t--) {
        solve();
    }

    return 0;
}