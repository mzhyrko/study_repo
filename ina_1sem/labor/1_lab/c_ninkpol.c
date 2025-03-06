#include <stdio.h>

int main(){
    int n = 0;
    int k = 0;
    int kop = 0;
    int rev = 0;
    
    printf("Wprowadz liczbe (n)  i jej podstawe (k): \n n = ");
    scanf("%d", &n);
    printf("k = ");
    scanf("%d", &k);

    kop = n;

    while(kop > 0){
        rev = (rev*k)+(kop%k);
        kop = kop/k;
    }
    
    

    if (rev == n)
            printf("Liczba jest polindromem\n");
    else 
            printf("Liczba nie jest polindromem\n");

    return 0;
}
