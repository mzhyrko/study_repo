#include <bits/stdc++.h>
using namespace std;

int solve(vector<long long> &a, int n) {
    set<long long> st;
    for (int i = 0; i < n; i++) {
        if (a[i] % 2 == 0) {
            st.insert(a[i]);
        }
    }
    
    int moves = 0;
    while (!st.empty()) {
        long long max_even = *st.rbegin();
        st.erase(prev(st.end()));           
        
        moves++;
        long long halved = max_even / 2;
        if (halved % 2 == 0 && halved > 0) {
            st.insert(halved);
        }
    }
    return moves;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    int t;
    cin >> t;
    while (t--) {
        int n;
        cin >> n;
        vector<long long> a(n);
        for (int i = 0; i < n; i++) {
            cin >> a[i];
        }
        cout << solve(a, n) << "\n";
    }
    return 0;
}