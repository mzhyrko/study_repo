#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <ctype.h>

void print_solution(int *coins, int *count, int coinCount) {
    for (int i = 0; i < coinCount; i++) {
        if (count[i] > 0) {
            printf("%d x %d\n", count[i], coins[i]);
        }
    }
}

int coin_change(int *coins, int coinCount, int amount) {
    if (amount < 0) {
        printf("%d ==> Invalid amount (must be non-negative)!\n", amount);
        return -1;
    }

    int *dp = (int *)malloc((amount + 1) * sizeof(int));
    int *track = (int *)malloc((amount + 1) * sizeof(int));

    if (dp == NULL || track == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        free(dp);
        free(track);
        return -1;
    }

    dp[0] = 0;
    for (int i = 1; i <= amount; i++) {
        dp[i] = INT_MAX;
        track[i] = -1;
    }

    for (int i = 0; i < coinCount; i++) {
        if (coins[i] <= 0) {
            fprintf(stderr, "Error: Coin value %d is invalid (must be positive).\n", coins[i]);
            free(dp);
            free(track);
            return -1;
        }
        for (int j = coins[i]; j <= amount; j++) {
            if (dp[j - coins[i]] != INT_MAX && dp[j] > dp[j - coins[i]] + 1) {
                dp[j] = dp[j - coins[i]] + 1;
                track[j] = i;
            }
        }
    }

    if (dp[amount] == INT_MAX) {
        free(dp);
        free(track);
        return -1;
    }

    int *count = (int *)malloc(coinCount * sizeof(int));
    if (count == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        free(dp);
        free(track);
        return -1;
    }

    for (int i = 0; i < coinCount; i++) {
        count[i] = 0;
    }

    int current = amount;
    while (current > 0) {
        count[track[current]]++;
        current -= coins[track[current]];
    }

    printf("%d ==> %d\n", amount, dp[amount]);
    print_solution(coins, count, coinCount);

    int result = dp[amount];

    free(dp);
    free(track);
    free(count);

    return result;
}

int read_coins_from_file(const char *file_path, int **coins) {
    FILE *file = fopen(file_path, "r");
    if (!file) {
        perror("Failed to open file");
        return -1;
    }

    char line[256];
    if (!fgets(line, sizeof(line), file)) {
        fprintf(stderr, "Error: File is empty or cannot read the first line.\n");
        fclose(file);
        return -1;
    }

    int coinCount = atoi(line);
    if (coinCount <= 0) {
        fprintf(stderr, "Error: First line of the file must be a positive integer (number of coins).\n");
        fclose(file);
        return -1;
    }

    *coins = (int *)malloc(coinCount * sizeof(int));
    if (*coins == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        fclose(file);
        return -1;
    }

    for (int i = 0; i < coinCount; i++) {
        if (!fgets(line, sizeof(line), file)) {
            fprintf(stderr, "Error: File has fewer lines than expected.\n");
            free(*coins);
            fclose(file);
            return -1;
        }
        int coin = atoi(line);
        if (coin <= 0) {
            fprintf(stderr, "Error: Coin value %d is invalid (must be positive).\n", coin);
            free(*coins);
            fclose(file);
            return -1;
        }
        (*coins)[i] = coin;
    }

    fclose(file);
    return coinCount;
}

int main(int argc, char *argv[]) {
    if (argc < 3) {
        fprintf(stderr, "Usage: %s <coin_file> <amount1> <amount2> ...\n", argv[0]);
        return 1;
    }

    int *coins;
    int coinCount = read_coins_from_file(argv[1], &coins);
    if (coinCount == -1) {
        return 1;
    }

    for (int i = 2; i < argc; i++) {
        char *endptr;
        long amount = strtol(argv[i], &endptr, 10);
        if (*endptr != '\0' || amount < 0) {
            fprintf(stderr, "Error: Invalid amount '%s'. Amounts must be non-negative integers.\n", argv[i]);
            free(coins);
            return 1;
        }
        if (coin_change(coins, coinCount, (int)amount) == -1) {
            printf("%ld ==> No solution!\n", amount);
        }
    }

    free(coins);
    return 0;
}
