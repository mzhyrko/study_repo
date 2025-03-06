#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <limits.h>
#include <stdbool.h>

void readnum(int *a){ 
    printf("Napisz liczbe: ");
    if (scanf("%d", a) != 1 ) {
        printf("Blad danych\n");
        exit(1);
    }

    if (*a < 0 || *a > INT_MAX) {
        printf("Blad danych\n");
        exit(2);
    }
}

bool is_prime(int a){

    int m = 3;
    int k = sqrt(a);

    if(a == 2)
        return true;

    if(a < 2)
        return false;

    if(a % 2 == 0)
        return false;

    while((m <= k) && (a % m != 0)){
        m = m+2;

        if (m > k)
            return true;
    }

    return false; 
}

int main(){

    int a = 0;

    readnum(&a);

    if(is_prime(a) == true){
        printf("%d - jest liczba pierwsza\n", a);
    }else{
        printf("%d - nie jest liczba pierwsza\n", a);
    }

    return 0;
}
