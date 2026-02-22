#include <bits/stdc++.h>

using namespace std;

bool solve(int n, vector<pair<int, int>>& a) {
    int pos = 0, neg = 0;
    
    for (int i = 0; i < n; i++) {
        if (a[i].first > 0) {
            pos++;
        } else {
            neg++;
        }
    }
    
    if (pos == 0 || neg == 0 || pos == 1 || neg == 1) {
        return true;
    }
    
    return false;
}

int main() {   
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int n;
    cin >> n;
    vector<pair<int, int>> a(n);
 
    for (int i = 0; i < n; i++) {
        cin >> a[i].first >> a[i].second;
    }
    
    if (solve(n, a)) {
        cout << "Yes" << endl;
    } else {
        cout << "No" << endl;
    }

    return 0;
}