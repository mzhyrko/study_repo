#include <stdio.h>
#include <limits.h>
#include <stdlib.h>
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

void primeFactorization(int n) {
    int count;

    count = 0;
    while (n % 2 == 0) {
        count++;
        n /= 2;
    }
    if (count > 0) {
        printf("2^%d", count);
        if (n > 1)
            printf("*");
    }

    for (int i = 3; i * i <= n; i += 2) {
        count = 0;
        while (n % i == 0) {
            count++;
            n /= i;
        }
        if (count > 0) {
            printf("%d^%d", i, count);
            if (n > 1)
                printf("*");
        }
    }

    if (n > 2) {
        printf("%d", n);
    }
}

int main() {
    int num;
    readnum(&num);  
    primeFactorization(num);
    return 0;
}

