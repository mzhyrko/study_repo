#include <bits/stdc++.h>

using namespace std;

int main()
{   
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    long long res=0,n,l=-1,r,d;
    
    cin>>n>>d;
    vector<int>p(n);
 
    r=n-1;
    for(int i=0;i<n;i++)
       cin>>p[i];
    
    sort(p.begin(),p.end());
    long long i=1;
    
    while(l<r){
        int m=p[r];
        if(m*(i) > d){
            r--;
            i=1;
            res++;

        }
         else{
            l++,i++; 
         }
        
    }
       
    cout<<res;

    return 0;
}