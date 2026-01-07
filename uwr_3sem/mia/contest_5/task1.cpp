#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    int t;
    cin >> t;
    while (t--) {
        string s;
        cin >> s;
        int cnt_N = 0;
        
        for (auto c: s) {
            if(c == 'N')cnt_N++;
        }
        if(cnt_N == 1) 
            cout << "NO\n";
        else
            cout << "YES\n";
    }
}