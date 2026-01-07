#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int n;
    cin >> n;
    
    vector<int> t(n);
    for (int i = 0; i < n; ++i) {
        cin >> t[i];
    }
    
    vector<int> gr;
    int cur_len = 1;
    
    for(int i = 1; i<n; i++){
        if(t[i] == t[i-1]){
            cur_len++;
        }else{
            gr.push_back(cur_len);
            cur_len = 1;
        }
    }
    gr.push_back(cur_len);
    int ans = 0;

    for(int i = 1; i<gr.size(); i++){
        int l1 = gr[i];
        int l2 = gr[i-1];

        int pre_res = 2*min(l1, l2);

        if(pre_res > ans){
            ans = pre_res;
        }
    }

    cout << ans << "\n"; 

    return 0;
}