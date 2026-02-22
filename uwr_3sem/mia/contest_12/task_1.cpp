#include <bits/stdc++.h>

using namespace std;

int nbr_deg(int x, int y, int n, int m){
    int nbr = 4;
    if (x == 1 || x == n) nbr--;
    if (y == 1 || y == m) nbr--;

    return nbr;
}

void solve(){
    int n,m,x1,x2,y1,y2; 
    
    cin>>n>>m;
    cin>>x1>>y1>>x2>>y2;

    cout << min(nbr_deg(x1, y1, n, m), nbr_deg(x2, y2, n, m)) << "\n";
}

int main()
{   
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int t,n,m;
    cin >> t;
    
    for(int i = 0; i<t; i++){
        solve();
    }


    return 0;
}