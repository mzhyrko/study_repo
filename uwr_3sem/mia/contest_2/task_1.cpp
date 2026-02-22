#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int t;
    cin >> t;
    
    while (t--) {
        int n;
        cin >> n;
        
        long long sum_sec = 0;
        long long min_sec = LLONG_MAX;           // самый маленький второй минимум
        
        vector<long long> mins(n);
        
        for (int i = 0; i < n; ++i) {
            int m;
            cin >> m;
            
            long long mn1 = LLONG_MAX;
            long long mn2 = LLONG_MAX;
            
            for (int j = 0; j < m; ++j) {
                long long x;
                cin >> x;
                if (x < mn1) {
                    mn2 = mn1;
                    mn1 = x;
                } else if (x < mn2) {
                    mn2 = x;
                }
            }
            
            mins[i] = mn1;
            sum_sec += mn2;
            min_sec = min(min_sec, mn2);
        }
        
        long long global_min = *min_element(mins.begin(), mins.end());
        
        if (n == 1) {
            cout << global_min << '\n';
        } else {
            cout << global_min + sum_sec - min_sec << '\n';
        }
    }
    
    return 0;
}