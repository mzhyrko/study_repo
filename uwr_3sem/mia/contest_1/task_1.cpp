#include <iostream>
using namespace std;

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    
    int data;
    int arr[] = {1, 5, 10, 20, 100};
    int n = 5;
    int cnt = 0;
    
    cin >> data;
    
    if (data < 0) {
        cout << 0 << '\n';
        return 0;
    }

    for (int i = n - 1; i >= 0; i--) {
        while (data >= arr[i]) {
            data -= arr[i];
            cnt++;
        }
        if (data == 0) 
            break;
    }
    
    cout << cnt << '\n';
    
    return 0;
}
