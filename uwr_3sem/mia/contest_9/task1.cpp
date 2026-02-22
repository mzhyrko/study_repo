#include <iostream>
#include <vector>
#include <set>
using namespace std;

int main() {
    int n, m;
    cin >> n >> m;
    
    vector<set<int>> adj(n + 1);
    
    for (int i = 0; i < m; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].insert(b);
        adj[b].insert(a);
    }
    
    int groups = 0;
    
    while (true) {
        vector<int> toRemove;
        
        for (int i = 1; i <= n; i++) {
            if (adj[i].size() == 1) {
                toRemove.push_back(i);
            }
        }
        
        if (toRemove.empty()) {
            break;
        }
        
        for (int student : toRemove) {
            for (int neighbor : adj[student]) {
                adj[neighbor].erase(student);
            }
            adj[student].clear();
        }
        
        groups++;
    }
    
    cout << groups << endl;
    
    return 0;
}