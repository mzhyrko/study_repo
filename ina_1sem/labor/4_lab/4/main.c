#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "totient.h"
//#include "totientv2.h"
#include <limits.h>
int main(int argc, char *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Usage: %s <number1> <number2> ...\n", argv[0]);
        return 1;
    }

    for (int i = 1; i < argc; i++) {
        unsigned n;
        char *endptr;
        long temp_n = strtol(argv[i], &endptr, 10);
        if (*endptr != '\0' || temp_n < 0 || temp_n > UINT_MAX) {
            fprintf(stderr, "Error: Invalid input '%s'.\n", argv[i]);
            return 1;
        }
        n = (unsigned)temp_n;
        printf("totient(%u) = %u\n", n, totient(n));
    }
    return 0;
}
