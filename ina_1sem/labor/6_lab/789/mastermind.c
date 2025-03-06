#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#define CODE_LENGTH 4
#define MAX_DIGIT 6

// Generate all possible codes
void generate_codes(int **codes, int *code_count, int current_code[], int depth) {
    if (depth == CODE_LENGTH) {
        memcpy(codes[*code_count], current_code, CODE_LENGTH * sizeof(int));
        (*code_count)++;
        return;
    }

    for (int i = 1; i <= MAX_DIGIT; i++) {
        current_code[depth] = i;
        generate_codes(codes, code_count, current_code, depth + 1);
    }
}

// Check if a code matches the feedback
bool matches_feedback(int code[], int guess[], int correct_pos, int correct_digit) {
    bool used_code[CODE_LENGTH] = {false};
    bool used_guess[CODE_LENGTH] = {false};
    int pos_match = 0, digit_match = 0;

    // Check correct positions
    for (int i = 0; i < CODE_LENGTH; i++) {
        if (code[i] == guess[i]) {
            pos_match++;
            used_code[i] = true;
            used_guess[i] = true;
        }
    }

    // Check correct digits in wrong positions
    for (int i = 0; i < CODE_LENGTH; i++) {
        if (!used_guess[i]) {
            for (int j = 0; j < CODE_LENGTH; j++) {
                if (!used_code[j] && guess[i] == code[j]) {
                    digit_match++;
                    used_code[j] = true;
                    break;
                }
            }
        }
    }

    return pos_match == correct_pos && digit_match == correct_digit;
}

int main() {
    int total_codes = 1;
    for (int i = 0; i < CODE_LENGTH; i++) {
        total_codes *= MAX_DIGIT;
    }

    // Allocate memory for all possible codes
    int **codes = malloc(total_codes * sizeof(int *));
    for (int i = 0; i < total_codes; i++) {
        codes[i] = malloc(CODE_LENGTH * sizeof(int));
    }

    int code_count = 0;
    int current_code[CODE_LENGTH];

    generate_codes(codes, &code_count, current_code, 0);

    printf("Think of a 4-digit code using numbers 1 to %d.\n", MAX_DIGIT);
    printf("I will try to guess it.\n\n");

    bool *possible = malloc(total_codes * sizeof(bool));
    memset(possible, true, total_codes * sizeof(bool));

    for (int attempt = 1; attempt <= 8; attempt++) {
        // Find the first possible code
        int guess_index = -1;
        for (int i = 0; i < code_count; i++) {
            if (possible[i]) {
                guess_index = i;
                break;
            }
        }

        if (guess_index == -1) {
            printf("No possible codes left. Are you sure you gave correct feedback?\n");
            break;
        }

        int *guess = codes[guess_index];

        printf("%d: ", attempt);
        for (int i = 0; i < CODE_LENGTH; i++) {
            printf("%d ", guess[i]);
        }
        printf("?\n");

        int correct_pos, correct_digit;
        printf("Correct positions: ");
        scanf("%d", &correct_pos);
        printf("Correct digits in wrong positions: ");
        scanf("%d", &correct_digit);

        if (correct_pos + correct_digit > CODE_LENGTH || correct_pos < 0 || correct_digit < 0) {
            printf("Invalid feedback. Please check your input.\n");
            attempt--;
            continue;
        }

        if (correct_pos == CODE_LENGTH) {
            printf("I won! The code is correct.\n");
            break;
        }

        // Eliminate impossible codes
        bool any_possible = false;
        for (int i = 0; i < code_count; i++) {
            if (possible[i] && !matches_feedback(codes[i], guess, correct_pos, correct_digit)) {
                possible[i] = false;
            } else if (possible[i]) {
                any_possible = true;
            }
        }

        if (!any_possible) {
            printf("No possible codes left. Are you sure you gave correct feedback?\n");
            break;
        }
    }

    // Free allocated memory
    for (int i = 0; i < total_codes; i++) {
        free(codes[i]);
    }
    free(codes);
    free(possible);

    return 0;
}

