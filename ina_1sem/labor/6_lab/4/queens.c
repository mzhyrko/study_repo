#include <stdio.h>
#include <stdbool.h>
#include <limits.h>
#define MAX_N 100000

int position[MAX_N];
bool bije_wiersz[MAX_N];
bool bije_przek1[2 * MAX_N];
bool bije_przek2[2 * MAX_N];

void drukuj_rozwiazanie(int n) {
    for (int i = 1; i <= n; i++) {
        printf("%d", position[i]);
        if (i < n) {
            printf(", ");
        }
    }
    printf("\n");
}


void ustaw(int i, int n, int *solutions) {
    for (int j = 1; j <= n; j++) {
        if (!bije_wiersz[j] && !bije_przek1[i + j] && !bije_przek2[i - j + n]) {
            position[i] = j;
            bije_wiersz[j] = true;
            bije_przek1[i + j] = true;
            bije_przek2[i - j + n] = true;

            if (i < n) {
                ustaw(i + 1, n, solutions);
            } else {
                drukuj_rozwiazanie(n);
                (*solutions)++;
            }

            position[i] = 0;
            bije_wiersz[j] = false;
            bije_przek1[i + j] = false;
            bije_przek2[i - j + n] = false;
        }
    }
}

void hetmani(int n, int *solutions) {
    for (int i = 0; i <= n; i++) {
        position[i] = 0;
        bije_wiersz[i] = false;
    }
    for (int i = 0; i < 2 * n; i++) {
        bije_przek1[i] = false;
        bije_przek2[i] = false;
    }

    ustaw(1, n, solutions);
}

int main() {
    int n;
    int solutions = 0;
    printf("Enter the number of queens (N): ");
    scanf("%d", &n);

    if (n > 0 && n <= MAX_N) {
        hetmani(n, &solutions);
        printf("Number of solutions: %d\n", solutions);
    } else {
        printf("Invalid input. Please enter a number between 1 and %d.\n", MAX_N);
    }

    return 0;
}

