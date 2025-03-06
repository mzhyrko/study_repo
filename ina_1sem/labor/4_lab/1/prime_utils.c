#include <stdbool.h>
#include <math.h>
#include <limits.h>

#include "prime_utils.h"

unsigned primenumbers(unsigned n) {
    if (n < 2) return 0;
    bool sieve[n + 1];
    unsigned i, j, count = 0;
    for (i = 2; i <= n; i++) sieve[i] = true;
    unsigned limit = (unsigned)sqrt(n);
    for (i = 2; i <= limit; i++) {
        if (sieve[i]) {
            for (j = i * i; j <= n; j += i) sieve[j] = false;
        }
    }
    for (i = 2; i <= n; i++) if (sieve[i]) count++;
    return count;
}


bool is_prime(unsigned n) {
    if (n <= 1) return false;
    if (n <= 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;
    for (unsigned i = 5; i * i <= n; i += 6) {
        if (n % i == 0 || n % (i + 2) == 0) return false;
    }
    return true;
}

unsigned nth_prime(unsigned n) {
    if (n == 0) return 0;
    if (n == 1) return 2;
    unsigned count = 1;
    unsigned num = 3;
    while (count < n) {
        if (is_prime(num)) count++;
        num += 2;
    }
    return num - 2;
}
