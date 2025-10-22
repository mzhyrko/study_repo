#include <bits/stdc++.h>
using namespace std;

struct Interval {
    int L;
    int R;
    int w; 
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int t;
    if (!(cin >> t)) return 0;
    while (t--) {
        int n;
        cin >> n;
        vector<int> a(n);
        for (int i = 0; i < n; ++i) cin >> a[i];

        vector<vector<int>> pos(n + 1);
        for (int i = 0; i < n; ++i) {
            int v = a[i];
            if (1 <= v && v <= n) pos[v].push_back(i);
        }

        vector<Interval> intervals;
        intervals.reserve(n);
        for (int v = 1; v <= n; ++v) {
            const auto &p = pos[v];
            int m = (int)p.size();
            if (m < v) continue;
            for (int j = 0; j + v - 1 < m; ++j) {
                int L = p[j];
                int R = p[j + v - 1];
                intervals.push_back({L, R, v});
            }
        }

        if (intervals.empty()) {
            cout << 0 << '\n';
            continue;
        }

        sort(intervals.begin(), intervals.end(), [](const Interval &x, const Interval &y) {
            if (x.R != y.R) return x.R < y.R;
            if (x.L != y.L) return x.L < y.L;
            return x.w < y.w;
        });

        int m = (int)intervals.size();
        vector<int> ends(m);
        for (int i = 0; i < m; ++i) ends[i] = intervals[i].R;

        
        vector<int> dp(m + 1, 0);
        for (int i = 1; i <= m; ++i) {
            const auto &cur = intervals[i - 1];
            
            int p = upper_bound(ends.begin(), ends.end(), cur.L - 1) - ends.begin();
            
            dp[i] = max(dp[i - 1], dp[p] + cur.w);
        }

        cout << dp[m] << '\n';
    }

    return 0;
}
