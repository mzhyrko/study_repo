#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>


int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Użycie: %s {pn|pr|ip} <liczba>\n", argv[0]);
        return 1;
    }

    char *command = argv[1];
    unsigned number;

    char *endptr;
    long temp_num = strtol(argv[2], &endptr, 10);
    if (*endptr != '\0' || temp_num < 0 || temp_num > UINT_MAX) {
        fprintf(stderr, "Błąd: Nieprawidłowa liczba.\n");
        return 1;
    }
    number = (unsigned)temp_num;

    if (strcmp(command, "pn") == 0) {
        printf("%u\n", primenumbers(number));
    } else if (strcmp(command, "pr") == 0) {
        printf("%u\n", nth_prime(number));
    } else if (strcmp(command, "ip") == 0) {
        printf("%s\n", is_prime(number) ? "true" : "false");
    } else {
        fprintf(stderr, "Błąd: Nieprawidłowa komenda.\n");
        return 1;
    }

    return 0;
}
