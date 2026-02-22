#include <bits/stdc++.h>

using namespace std;

void solve() {
    string s, t;
    cin >> s >> t;
    
    int n = s.length();
    int m = t.length();
    
    bool a = false;
    for (char c : t) {
        if (c == 'a') {
            a = true;
            break;
        }
    }
    
    if (m == 1 && t[0] == 'a') {
        cout << 1 << "\n";
        return;
    }
    
    if (a) {
        cout << -1 << "\n";
        return;
    }
    
    long long ans = 1LL << n;
    
    cout << ans << "\n";
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int q;
    cin >> q;
    
    while (q--) {
        solve();
    }
    
    return 0;
}