#include <stdio.h>
#include <stdbool.h>
#include <math.h>

void compute_sieve(bool s[], unsigned n) {
    unsigned i, j;
    for (i = 2; i <= n; i++) {
        s[i] = true;
    }

    unsigned limit = n;
    for (i = 2; i <= limit; i++) {
        if (s[i]) {
            for (j = i * i; j <= n; j += i) {
                s[j] = false;
            }
        }
    }
}

unsigned count_primes(bool s[], unsigned n) {
    unsigned i, c = 0;
    for (i = 2; i <= n; i++) {
        if (s[i]) {
            c++;
        }
    }
    return c;
}

unsigned primenumbers(unsigned n) {
    bool sieve[n + 1];
    compute_sieve(sieve, n);
    return count_primes(sieve, n);
}

int main() {
    unsigned n;

    printf("Podaj liczbę całkowitą n (n >= 2), aby policzyć liczby pierwsze do n: ");
    if (scanf("%u", &n) != 1 || n < 2) {
        printf("Nieprawidłowe dane. Proszę podać liczbę całkowitą większą lub równą 2.\n");
        return 1;
    }

    printf("%u", n);

    unsigned prime_count = primenumbers(n);
    printf("Istnieje %u liczb pierwszych do %u.\n", prime_count, n);

    return 0;
}

