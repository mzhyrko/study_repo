#include <bits/stdc++.h>
using namespace std;

void solve() {
    int n;
    cin >> n;
    vector<double> miners, dim;
    for (int i = 0; i < 2 * n; i++) {
        long long x, y;
        cin >> x >> y;
        if (x == 0) {
            miners.push_back(abs(y));
        } else {
            dim.push_back(abs(x));
        }
    }
    sort(miners.begin(), miners.end());
    sort(dim.begin(), dim.end());
    double res = 0.0;
    for (int i = 0; i < n; i++) {
        res += sqrt(miners[i] * miners[i] + dim[i] * dim[i]);
    }
    cout << fixed << setprecision(15) << res << "\n";
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