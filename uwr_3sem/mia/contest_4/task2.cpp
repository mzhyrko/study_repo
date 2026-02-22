#include <bits/stdc++.h>
using namespace std;

void solve() {
    string s, t;
    cin >> s >> t;
    
    int n = s.length();
    
    for (int i = 0; i < n; i++) {
        string path = s.substr(0, i + 1);
        string reversed = s.substr(0, i);
        reverse(reversed.begin(), reversed.end());
        path += reversed;
        
        if (path.find(t) != string::npos) {
            cout << "YES\n";
            return;
        }
    }
    
    cout << "NO\n";
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