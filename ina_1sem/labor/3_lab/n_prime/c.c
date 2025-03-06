#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>
#include <limits.h>
#include <math.h>

bool is_positive_integer(const char *input) {
    if (input == NULL || *input == '\0') {
        return false;
    }
    for (int i = 0; input[i] != '\0'; i++) {
        if (!isdigit((unsigned char)input[i])) {
            return false;
        }
    }
    unsigned long value = strtoul(input, NULL, 10);
    if (value > UINT_MAX) {
        return false;
    }
    return true;
}

unsigned get_valid_input() {
    char input[100];
    while (true) {
        printf("Podaj dodatnia liczbe calkowita n, aby znalezc n-ta liczbe pierwsza: ");
        if (fgets(input, sizeof(input), stdin) == NULL) {
            printf("Blad wprowadzenia danych. Sprobuj ponownie.\n");
            continue;
        }
        input[strcspn(input, "\n")] = '\0';
        if (is_positive_integer(input)) {
            return (unsigned)strtoul(input, NULL, 10);
        } else {
            printf("Niepoprawne dane. Podaj dodatnia liczbe calkowita.\n");
        }
    }
}

unsigned estimate_limit(unsigned n) {
    if (n < 6) return 15;
    return (unsigned)(n * log(n) + n * log(log(n)));
}

void compute_sieve(bool *sieve, unsigned limit) {
    for (unsigned i = 2; i <= limit; i++) {
        sieve[i] = true;
    }
    for (unsigned i = 2; i * i <= limit; i++) {
        if (sieve[i]) {
            for (unsigned j = i * i; j <= limit; j += i) {
                sieve[j] = false;
            }
        }
    }
}

unsigned nth_prime(unsigned n) {
    unsigned limit = estimate_limit(n);
    bool *sieve = malloc((limit + 1) * sizeof(bool));
    if (!sieve) {
        printf("Blad alokacji pamieci.\n");
        exit(1);
    }
    compute_sieve(sieve, limit);

    unsigned count = 0;
    unsigned prime = 2;
    for (unsigned i = 2; i <= limit; i++) {
        if (sieve[i]) {
            count++;
            if (count == n) {
                prime = i;
                break;
            }
        }
    }

    free(sieve);
    return prime;
}

int main() {
    unsigned n = get_valid_input();
    unsigned prime = nth_prime(n);
    printf("%u-ta liczba pierwsza to: %u\n", n, prime);

    return 0;
}
