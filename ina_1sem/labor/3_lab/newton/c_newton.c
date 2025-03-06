#include <stdio.h>
#include <stdbool.h>
#include <ctype.h>

bool readnumV2(int *num) {
    char input[100];
    if (!fgets(input, sizeof(input), stdin)) {
        printf("Blad, wczytane dane nie sa intem.\n");
        return false;
    }

    for (int i = 0; input[i] != '\0' && input[i] != '\n'; i++) {
        if (!isdigit(input[i]) && !(i == 0 && input[i] == '-')) {
            printf("Blad, wczytane dane musza byc wieksze od 0\n");
            return false;
        }
    }

    if (sscanf(input, "%d", num) != 1) {
        printf("Blad\n");
        return false;
    }

    return true;
}

bool readnum(int *num) {
    if (scanf("%d", num) != 1) {
        printf("Blad\n");
        return false;
    }
    return true;
}

bool checkdata(int n, int k) {
    return (k >= 0 && k <= n);
}

int newton(int n, int k) {
    if (k < 0 || k > n) {
        return 0; 
    }
    if (k == 0 || k == n) {
        return 1;
    }

    int array[n + 1][k + 1];

    for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= k; j++) {
            if (j == 0 || j == i) {
                array[i][j] = 1;
            } else {
                array[i][j] = array[i - 1][j - 1] + array[i - 1][j];
            }
        }
    }
    return array[n][k];
}

int main() {
    int n, k;

    printf("Napisz wartosci n i k dla obliczenia dwumianu newtona\n");
    printf("n: ");
    if (!readnumV2(&n)) {
        return 1;
    }
    printf("k: ");
    if (!readnumV2(&k)) {
        return 1;
    }

    if (!checkdata(n, k)) {
        printf("niepoprawne dane dla n i k.\n");
        return 2;
    }

    int result = newton(n, k);
    printf("Dwumian newtona dla (%d, %d) to`: %d\n", n, k, result);

    return 0;
}

