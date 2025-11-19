#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int t;
    cin >> t;

    for (int test = 0; test < t; ++test) {
        int n;
        cin >> n;

        vector<long long> min1(n, LLONG_MAX);
        vector<long long> min2(n, LLONG_MAX);

        long long sum_min2 = 0;                 
        long long smallest_min1 = LLONG_MAX;     
        int idx_smallest_min1 = -1;              

        for (int i = 0; i < n; ++i) {
            int m;
            cin >> m;
            for (int j = 0; j < m; ++j) {
                long long val;
                cin >> val;
                if (val < min1[i]) {
                    min2[i] = min1[i];
                    min1[i] = val;
                } else if (val < min2[i]) {
                    min2[i] = val;
                }
            }
            
            sum_min2 += min2[i];

            
            if (min1[i] < smallest_min1 || (min1[i] == smallest_min1 && 
                (idx_smallest_min1 == -1 || min2[i] < min2[idx_smallest_min1]))) {
                smallest_min1 = min1[i];
                idx_smallest_min1 = i;
            }
        }

        if (n == 1) {
            cout << min1[0] << '\n';
            continue;
        }

        long long answer = 0;
        
        for (int trash = 0; trash < n; ++trash) {
           
            long long trash_min = min1[trash];
            vector<long long> removed_elements;
            long long sum_others = 0;
            
            for (int i = 0; i < n; ++i) {
                if (i != trash) {

                    sum_others += min2[i];
                    removed_elements.push_back(min1[i]);
                }
            }
            
            for (auto elem : removed_elements) {
                trash_min = min(trash_min, elem);
            }
            
            long long candidate = trash_min + sum_others;
            answer = max(answer, candidate);
        }
        
        cout << answer << '\n';
    }
    
    return 0;
}