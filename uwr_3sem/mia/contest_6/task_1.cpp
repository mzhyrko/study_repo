#include <bits/stdc++.h>
using namespace std;

void solve(long long l, long long r){
    long long x = l;
    long long y = x+1;

    if(2*x <= r){
        cout << x << " " << 2*x << "\n";
    }else{
        cout << "-1 -1\n";
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int t;
    cin >> t;
    
    for (int i = 0; i < t; ++i) {
        long long l, r;
        cin >> l >> r;

        solve(l,r);       
    }
    return 0;
}