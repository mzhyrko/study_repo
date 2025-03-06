#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

int gcd(int a, int b) {
    int c;
    while (b != 0) {
        c = b;
        b = a % b;
        a = c;
    }
    return a;
}

void readnum(int *num) {
    if (scanf("%d", num) != 1) {
        printf("Blad\n");
        exit(1);
    }

    if (*num < INT_MIN || *num > INT_MAX) {
        printf("Blad\n");
        exit(2);
    }
}

int main() {
    int a, b;

    printf("Wpisz pierwsza liczbe: ");
    readnum(&a);

    printf("Wpisz deuuga liczbe: ");
    readnum(&b);

    printf("NWD to: %d\n", gcd(a, b));

    return 0;
}

