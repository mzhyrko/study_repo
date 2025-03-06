#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
void generate_permutations(int arr[], int n, int *solutions) {
    while (true) {
        bool is_valid = 1;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (abs(arr[i] - arr[j]) == abs(i - j)) {
                    is_valid = 0;
                    break;
                }
            }
            if (!is_valid) break;
        }

        if (is_valid) {
            for (int i = 0; i < n; ++i) {
                printf("%d ", arr[i] + 1);
            }
            printf("\n");
            (*solutions)++;
        }

        int i = n - 2;
        while (i >= 0 && arr[i] > arr[i + 1]) {
            --i;
        }
        if (i < 0) break;

        int j = n - 1;
        while (arr[j] <= arr[i]) {
            --j;
        }

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        //swap(arr[i], arr[j]);

        // reverse array from (i + 1) to (n-1)
        int left = i + 1, right = n - 1;

        while (left < right) {
            temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            ++left;
            --right;
        }
    }
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <n>\n", argv[0]);
        return 1;
    }

    int n = atoi(argv[1]);
    if (n < 1) {
        printf("Please enter a positive integer for n.\n");
        return 1;
    }

    int *arr = malloc(n * sizeof(int));
    if (arr == NULL) {
        printf("Memory allocation failed.\n");
        return 1;
    }

    // Init array
    for (int i = 0; i < n; ++i) {
        arr[i] = i;
    }

    int solutions = 0;
    generate_permutations(arr, n, &solutions);

    printf("Number of solutions: %d\n", solutions);

    free(arr);
    return 0;
}

